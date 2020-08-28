package com.unishaala.rest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unishaala.rest.dto.CreateBraincertClassResponseDTO;
import com.unishaala.rest.dto.CreateClassLaunchResponseDTO;
import com.unishaala.rest.model.BraincertDO;
import com.unishaala.rest.model.SessionDO;
import com.unishaala.rest.model.UserDO;
import com.unishaala.rest.repository.BraincertRepository;
import com.unishaala.rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class BraincertService {
    private final BraincertRepository braincertRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${braincert.api.schedule.url}")
    private String BRAINCERT_SCHEDULE_URL;
    @Value("${braincert.api.getclasslaunch.url}")
    private String BRAINCERT_CLASSLAUNCH_URL;
    @Value("${braincert.api.key}")
    private String BRAINCERT_API_KEY;

    public void scheduleBraincertClass(final SessionDO sessionDO) {
        final ResponseEntity<String> braincertResponseEntity =
                restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(BRAINCERT_SCHEDULE_URL)
                                .queryParam("apikey", BRAINCERT_API_KEY)
                                .queryParam("title", sessionDO.getCourse().getName())
                                .queryParam("timezone", 33)
                                .queryParam("date", sessionDO.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                .queryParam("start_time", sessionDO.getStartTime().format(DateTimeFormatter.ofPattern("hh:mma")))
                                .queryParam("end_time", sessionDO.getStartTime().plusMinutes(sessionDO.getDurationInMin()).format(DateTimeFormatter.ofPattern("hh:mma"))).build().toUriString(),
                        HttpMethod.POST, createRequest(), String.class);
        if (braincertResponseEntity.getStatusCode() == HttpStatus.OK && Objects.nonNull(braincertResponseEntity.getBody())) {
            // student url are created
            try {
                CreateBraincertClassResponseDTO createBraincertDTO = objectMapper.readValue(braincertResponseEntity.getBody(), CreateBraincertClassResponseDTO.class);
                long totalCount = userRepository.countByRelatedClass(sessionDO.getAClass());
                int pageCount = (int) Math.ceil(totalCount / 10);
                int count = 0;
                while (count > pageCount) {
                    final List<UserDO> users = userRepository
                            .findByRelatedClass(sessionDO.getAClass(), PageRequest.of(count, 10));
                    for (UserDO user : users) {
                        final String launchUrl = createBraincertLaunchUrl(user, sessionDO.getCourse().getName(),
                                false, String.valueOf(sessionDO.getId()), createBraincertDTO.getClassId());
                        braincertRepository.save(BraincertDO.builder()
                                .isTeacher(false).session(sessionDO).user(user).url(launchUrl).build());
                    }
                    count++;
                }
                // teacher url is created
                final String launchUrl = createBraincertLaunchUrl(sessionDO.getCourse().getTeacher(),
                        sessionDO.getCourse().getName(), true, String.valueOf(sessionDO.getId()),
                        createBraincertDTO.getClassId());
                braincertRepository.save(BraincertDO.builder()
                        .isTeacher(true).session(sessionDO).user(sessionDO.getCourse().getTeacher()).url(launchUrl)
                        .build());
            } catch (Exception e) {

            }
        }
    }

    private String createBraincertLaunchUrl(final UserDO user, final String courseName, final boolean isTeacher, final String lessonName, final String classId) {
        final ResponseEntity<String> braincertResponseEntity =
                restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(BRAINCERT_CLASSLAUNCH_URL)
                                .queryParam("apikey", BRAINCERT_API_KEY)
                                .queryParam("class_id", classId)
                                .queryParam("userId", user.getId())
                                .queryParam("isTeacher", isTeacher ? 1 : 0)
                                .queryParam("userName", user.getUserName())
                                .queryParam("courseName", courseName)
                                .queryParam("lessonName", lessonName).build().toUriString(),
                        HttpMethod.POST, createRequest(), String.class);
        if (braincertResponseEntity.getStatusCode() == HttpStatus.OK && Objects.nonNull(braincertResponseEntity.getBody())) {
            try {
                CreateClassLaunchResponseDTO createClassLaunchResponseDTO = objectMapper.readValue(braincertResponseEntity.getBody(), CreateClassLaunchResponseDTO.class);
                return createClassLaunchResponseDTO.getLaunchurl();
            } catch (Exception e) {
                return "No Url";
            }
        } else {
            return "No Url";
        }
    }


    // ?apikey=WeNiXPQy0wnFR8v2hquD&class_id=505671&userId=1&isTeacher=0&userName=sainik&courseName=some&lessonName=some1
    private HttpEntity<?> createRequest() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(null, headers);
    }
}
