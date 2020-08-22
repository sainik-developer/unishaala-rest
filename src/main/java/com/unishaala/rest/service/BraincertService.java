package com.unishaala.rest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unishaala.rest.model.SessionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class BraincertService {
//    @Autowired
//    private SessionRepository sessionRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserBraincertRepository userBraincertRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${braincert.api.schedule.url}")
    private String BRAINCERT_SCHEDULE_URL;
    @Value("${braincert.api.getclasslaunch.url}")
    private String BRAINCERT_CLASSLAUNCH_URL;
    @Value("${braincert.api.key}")
    private String BRAINCERT_API_KEY;

    public void scheduleBraincertClass(final SessionDO session) {
//        final ResponseEntity<String> braincertResponseEntity =
//                restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(BRAINCERT_SCHEDULE_URL)
//                                .queryParam("apikey", BRAINCERT_API_KEY)
//                                .queryParam("title", session.getCourseDO().getName())
//                                .queryParam("timezone", 33)
//                                .queryParam("date", session.get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
//                                .queryParam("start_time", session.getStarts_at().format(DateTimeFormatter.ofPattern("hh:mma")))
//                                .queryParam("end_time", session.getStarts_at().plusMinutes(30).format(DateTimeFormatter.ofPattern("hh:mma"))).build().toUriString(),
//                        HttpMethod.POST, createRequest(), String.class);
//        if (braincertResponseEntity.getStatusCode() == HttpStatus.OK && Objects.nonNull(braincertResponseEntity.getBody())) {
//            // student url are created
//            try {
//                CreateBraincertClassResponseDTO createBraincertDTO = objectMapper.readValue(braincertResponseEntity.getBody(), CreateBraincertClassResponseDTO.class);
//                List<User> users = userRepository.findBySchoolClass(session.getSchoolClass());
//                for (User user : users) {
//                    if (user.getSchoolClass() != null) {
//                        final String launchUrl = createBraincertLaunchUrl(user, session.getCourse().getName(), false, String.valueOf(session.getSession_id()), createBraincertDTO.getClassId());
//                        userBraincertRepository.save(UserBraincert.builder().session(session).user(user).braincertUrl(launchUrl).build());
//                    }
//                }
//                // teacher url is created
//                final String launchUrl = createBraincertLaunchUrl(teacherUser, session.getCourse().getName(), true, String.valueOf(session.getSession_id()), createBraincertDTO.getClassId());
//                session.setBraincertClassId(createBraincertDTO.getClassId());
//                session.setBraincertTeacherUrl(launchUrl);
//                sessionRepository.save(session);
//            } catch (Exception e) {
//
//            }
//        }
    }

//    private String createBraincertLaunchUrl(final User user, final String courseName, final boolean isTeacher, final String lessonName, final String classId) {
//        final ResponseEntity<String> braincertResponseEntity =
//                restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(BRAINCERT_CLASSLAUNCH_URL)
//                                .queryParam("apikey", BRAINCERT_API_KEY)
//                                .queryParam("class_id", classId)
//                                .queryParam("userId", (int) user.getId())
//                                .queryParam("isTeacher", isTeacher ? 1 : 0)
//                                .queryParam("userName", user.getUsername())
//                                .queryParam("courseName", courseName)
//                                .queryParam("lessonName", lessonName).build().toUriString(),
//                        HttpMethod.POST, createRequest(), String.class);
//        if (braincertResponseEntity.getStatusCode() == HttpStatus.OK && Objects.nonNull(braincertResponseEntity.getBody())) {
//            try {
//                CreateClassLaunchResponseDTO createClassLaunchResponseDTO = objectMapper.readValue(braincertResponseEntity.getBody(), CreateClassLaunchResponseDTO.class);
//                return createClassLaunchResponseDTO.getLaunchurl();
//            } catch (Exception e) {
//                return "No Url";
//            }
//        } else {
//            return "No Url";
//        }
//    }


    // ?apikey=WeNiXPQy0wnFR8v2hquD&class_id=505671&userId=1&isTeacher=0&userName=sainik&courseName=some&lessonName=some1
    private HttpEntity<?> createRequest() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(null, headers);
    }
}
