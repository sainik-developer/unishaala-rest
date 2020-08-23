package com.unishaala.rest.service;

import com.unishaala.rest.dto.SessionDTO;
import com.unishaala.rest.exception.SessionException;
import com.unishaala.rest.mapper.SessionMapper;
import com.unishaala.rest.model.ClassDO;
import com.unishaala.rest.model.CourseDO;
import com.unishaala.rest.model.SessionDO;
import com.unishaala.rest.repository.ClassRepository;
import com.unishaala.rest.repository.CourseRepository;
import com.unishaala.rest.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class SessionService {
    private final BraincertService braincertService;
    private final SessionRepository sessionRepository;
    private final CourseRepository courseRepository;
    private final ClassRepository classRepository;

    public List<SessionDTO> createSession(final UUID teacherId, final SessionDTO sessionDTO) {
        final CourseDO courseDo = courseRepository.findById(sessionDTO.getCourseId()).orElse(null);
        if (courseDo != null) {
            if (courseDo.getTeacher().getId().equals(teacherId)) {
                final ClassDO classDo = classRepository.findById(sessionDTO.getClassId()).orElse(null);
                if (classDo != null) {
                    final Iterable<SessionDO> sessionDos = sessionRepository.saveAll(createConsideringRepetition(sessionDTO, courseDo, classDo));
                    sessionDos.forEach(this::handleBraincert);
                    return SessionMapper.INSTANCE.toDTOs(sessionDos);
                }
                throw new SessionException("Invalid class is associated!");
            }
            throw new SessionException("Only Teacher can create a session of a course!");
        }
        throw new SessionException("Course is not valid!");
    }

    private List<SessionDO> createConsideringRepetition(final SessionDTO sessionDTO, final CourseDO courseDO, final ClassDO classDO) {
        final List<SessionDO> sessions = new LinkedList<>();
        if (sessionDTO.isRepeat()) {
            int count = 0;
            for (LocalDate date = sessionDTO.getStartTime().toLocalDate(); date.isBefore(sessionDTO.getEndDate()); date = date.plusDays(1)) {
                if (!sessionDTO.isAllDaysOrWorkingDays() && date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                    continue;
                }
                final SessionDO sessionDo = SessionMapper.INSTANCE.fromDTO(sessionDTO);
                sessionDo.setCourse(courseDO);
                sessionDo.setAClass(classDO);
                sessionDo.setStartTime(sessionDTO.getStartTime().plusDays(count));
                sessions.add(sessionDo);
                count++;
            }
        } else {
            final SessionDO sessionDo = SessionMapper.INSTANCE.fromDTO(sessionDTO);
            sessionDo.setCourse(courseDO);
            sessionDo.setAClass(classDO);
            sessions.add(sessionDo);
        }
        return sessions;
    }

    private void handleBraincert(final SessionDO sessionDO) {
        braincertService.scheduleBraincertClass(sessionDO);
    }
}
