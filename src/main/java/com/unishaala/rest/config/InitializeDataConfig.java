package com.unishaala.rest.config;

import com.unishaala.rest.dto.*;
import com.unishaala.rest.enums.UserType;
import com.unishaala.rest.mapper.ClassMapper;
import com.unishaala.rest.mapper.CourseMapper;
import com.unishaala.rest.mapper.SchoolMapper;
import com.unishaala.rest.mapper.UserMapper;
import com.unishaala.rest.model.ClassDO;
import com.unishaala.rest.model.CourseDO;
import com.unishaala.rest.model.SchoolDO;
import com.unishaala.rest.model.UserDO;
import com.unishaala.rest.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Log4j2
@Configuration
public class InitializeDataConfig {

    private static final String DETAILS = "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
            " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took " +
            "a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
            "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with " +
            "the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software " +
            "like Aldus PageMaker including versions of Lorem Ipsum.";

    @Bean
    @Profile(value = {"stage", "local"})
    public ApplicationRunner initializer(final UserRepository userRepository,
                                         final SchoolRepository schoolRepository,
                                         final ClassRepository classRepository,
                                         final CourseRepository courseRepository,
                                         final SessionRepository sessionRepository) {
        return arg -> {
            // add three admin
            final UserDO admin1DO = saveIfNotPresent(userRepository, UserMapper.INSTANCE.fromDTO(AdminDTO.builder()
                    .userType(UserType.ADMIN).mobileNumber("+911111111111").userName("Vikshak").build()));
            final UserDO admin2DO = saveIfNotPresent(userRepository, UserMapper.INSTANCE.fromDTO(AdminDTO.builder()
                    .userType(UserType.ADMIN).mobileNumber("+912222222222").userName("Sainik").build()));
            final UserDO admin3DO = saveIfNotPresent(userRepository, UserMapper.INSTANCE.fromDTO(AdminDTO.builder()
                    .userType(UserType.ADMIN).mobileNumber("+913333333333").userName("Edition").build()));
            // add five schools
            final SchoolDO school1DO = saveIfNotPresent(schoolRepository, SchoolMapper.INSTANCE.fromDTO(SchoolDTO.builder()
                    .name("school1").address("school1 address").createdDate(LocalDateTime.now()).build()));
            final SchoolDO school2DO = saveIfNotPresent(schoolRepository, SchoolMapper.INSTANCE.fromDTO(SchoolDTO.builder()
                    .name("school2").address("school2 address").createdDate(LocalDateTime.now()).build()));
            final SchoolDO school3DO = saveIfNotPresent(schoolRepository, SchoolMapper.INSTANCE.fromDTO(SchoolDTO.builder()
                    .name("school3").address("school3 address").createdDate(LocalDateTime.now()).build()));
            final SchoolDO school4DO = saveIfNotPresent(schoolRepository, SchoolMapper.INSTANCE.fromDTO(SchoolDTO.builder()
                    .name("school4").address("school4 address").createdDate(LocalDateTime.now()).build()));
            final SchoolDO school5DO = saveIfNotPresent(schoolRepository, SchoolMapper.INSTANCE.fromDTO(SchoolDTO.builder()
                    .name("school5").address("school5 address").createdDate(LocalDateTime.now()).build()));
            // add 2,3,4,5,6 classes for each school
            final ClassDO class1DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("8-A").schoolId(school1DO.getId()).build()), school1DO);
            final ClassDO class2DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("9-A").schoolId(school1DO.getId()).build()), school1DO);

            final ClassDO class3DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("6-A").schoolId(school2DO.getId()).build()), school2DO);
            final ClassDO class4DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("7-A").schoolId(school2DO.getId()).build()), school2DO);
            final ClassDO class5DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("8-A").schoolId(school2DO.getId()).build()), school2DO);

            final ClassDO class6DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("5-A").schoolId(school3DO.getId()).build()), school3DO);
            final ClassDO class7DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("6-A").schoolId(school3DO.getId()).build()), school3DO);
            final ClassDO class8DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("7-A").schoolId(school3DO.getId()).build()), school3DO);
            final ClassDO class9DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("8-A").schoolId(school3DO.getId()).build()), school3DO);

            final ClassDO class10DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("2-A").schoolId(school4DO.getId()).build()), school4DO);
            final ClassDO class11DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("3-A").schoolId(school4DO.getId()).build()), school4DO);
            final ClassDO class12DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("4-A").schoolId(school4DO.getId()).build()), school4DO);
            final ClassDO class13DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("5-A").schoolId(school4DO.getId()).build()), school4DO);
            final ClassDO class14DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("6-A").schoolId(school4DO.getId()).build()), school4DO);

            final ClassDO class15DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("2-A").schoolId(school5DO.getId()).build()), school5DO);
            final ClassDO class16DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("3-A").schoolId(school5DO.getId()).build()), school5DO);
            final ClassDO class17DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("4-A").schoolId(school5DO.getId()).build()), school5DO);
            final ClassDO class18DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("5-A").schoolId(school5DO.getId()).build()), school5DO);
            final ClassDO class19DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("6-A").schoolId(school5DO.getId()).build()), school5DO);
            final ClassDO class20DO = saveIfNotPresent(classRepository, ClassMapper.INSTANCE.fromDTO(ClassDTO.builder().name("7-A").schoolId(school5DO.getId()).build()), school5DO);
//            // add 20 students distributed among all class
            final UserDO student1DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student1@gmail.com").mobileNumber("+914333333333").fullName("student1").classId(class1DO.getId()).build()));
            final UserDO student2DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student2@gmail.com").mobileNumber("+915333333333").fullName("student2").classId(class2DO.getId()).build()));
            final UserDO student3DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student3@gmail.com").mobileNumber("+916333333333").fullName("student3").classId(class3DO.getId()).build()));
            final UserDO student4DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student4@gmail.com").mobileNumber("+917333333333").fullName("student4").classId(class4DO.getId()).build()));
            final UserDO student5DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student5@gmail.com").mobileNumber("+918333333333").fullName("student5").classId(class5DO.getId()).build()));
            final UserDO student6DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student6@gmail.com").mobileNumber("+919333333333").fullName("student6").classId(class6DO.getId()).build()));
            final UserDO student7DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student7@gmail.com").mobileNumber("+911033333333").fullName("student7").classId(class7DO.getId()).build()));
            final UserDO student8DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student8@gmail.com").mobileNumber("+911133333333").fullName("student8").classId(class8DO.getId()).build()));
            final UserDO student9DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student9@gmail.com").mobileNumber("+911233333333").fullName("student9").classId(class9DO.getId()).build()));
            final UserDO student10DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student10@gmail.com").mobileNumber("+911333333333").fullName("student10").classId(class10DO.getId()).build()));
            final UserDO student11DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student11@gmail.com").mobileNumber("+911433333333").fullName("student11").classId(class11DO.getId()).build()));
            final UserDO student12DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student12@gmail.com").mobileNumber("+911533333333").fullName("student12").classId(class12DO.getId()).build()));
            final UserDO student13DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student13@gmail.com").mobileNumber("+911633333333").fullName("student13").classId(class13DO.getId()).build()));
            final UserDO student14DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student14@gmail.com").mobileNumber("+911733333333").fullName("student14").classId(class14DO.getId()).build()));
            final UserDO student15DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student15@gmail.com").mobileNumber("+911833333333").fullName("student15").classId(class15DO.getId()).build()));
            final UserDO student16DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student16@gmail.com").mobileNumber("+911933333333").fullName("student16").classId(class16DO.getId()).build()));
            final UserDO student17DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student17@gmail.com").mobileNumber("+912033333333").fullName("student17").classId(class17DO.getId()).build()));
            final UserDO student18DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student18@gmail.com").mobileNumber("+912133333333").fullName("student18").classId(class18DO.getId()).build()));
            final UserDO student19DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student19@gmail.com").mobileNumber("+912233333333").fullName("student19").classId(class19DO.getId()).build()));
            final UserDO student20DO = saveStudentIfNotPresents(userRepository, UserMapper.INSTANCE.fromStudentDTO(StudentDTO.builder()
                    .userType(UserType.STUDENT).dob(LocalDate.now()).email("student20@gmail.com").mobileNumber("+912333333333").fullName("student20").classId(class20DO.getId()).build()));
//            // add five teacher
            final UserDO teacher1DO = saveIfNotPresent(userRepository, UserMapper.INSTANCE.fromTeacherDTO(TeacherDTO.builder()
                    .userType(UserType.TEACHER).mobileNumber("+912411111111").firstName("Teacher1").lastName("surname").build()));
            final UserDO teacher2DO = saveIfNotPresent(userRepository, UserMapper.INSTANCE.fromTeacherDTO(TeacherDTO.builder()
                    .userType(UserType.TEACHER).mobileNumber("+912522222222").firstName("Teacher2").lastName("surname").build()));
            final UserDO teacher3DO = saveIfNotPresent(userRepository, UserMapper.INSTANCE.fromTeacherDTO(TeacherDTO.builder()
                    .userType(UserType.TEACHER).mobileNumber("+912633333333").firstName("Teacher3").lastName("surname").build()));
//            // add two course
            final CourseDO course1DO = saveCourseIfNotPresents(courseRepository, userRepository,
                    CourseMapper.INSTANCE.fromDTO(CourseDTO.builder().details(DETAILS).name("Physics").build()),teacher1DO.getId());
            final CourseDO course2DO = saveCourseIfNotPresents(courseRepository, userRepository,
                    CourseMapper.INSTANCE.fromDTO(CourseDTO.builder().details(DETAILS).name("Chemistry").build()),teacher2DO.getId());
            final CourseDO course3DO = saveCourseIfNotPresents(courseRepository, userRepository,
                    CourseMapper.INSTANCE.fromDTO(CourseDTO.builder().details(DETAILS).name("Mathematics").build()),teacher3DO.getId());
        };
    }

    private CourseDO saveCourseIfNotPresents(final CourseRepository courseRepository, final UserRepository userRepository, final CourseDO courseDO, final UUID teacherId) {
        final CourseDO courseDO1 = courseRepository.findByName(courseDO.getName());
        if (courseDO1 == null) {
            final UserDO userDO = userRepository.findById(teacherId).get();
            courseDO.setTeacher(userDO);
            return save(courseRepository, courseDO);
        }
        return courseDO1;
    }

    private UserDO saveStudentIfNotPresents(final UserRepository userRepository, final UserDO studentDTO) {
        final UserDO userDO = userRepository.findByMobileNumberAndUserType(studentDTO.getMobileNumber(), UserType.STUDENT);
        if (userDO == null) {
            return save(userRepository, studentDTO);
        }
        return userDO;
    }

    private ClassDO saveIfNotPresent(final ClassRepository classRepository, final ClassDO classDO, final SchoolDO schoolDO) {
        final ClassDO classDOFromDb = classRepository.findByNameAndSchool(classDO.getName(), schoolDO);
        if (classDOFromDb == null) {
            classDO.setSchool(schoolDO);
            return save(classRepository, classDO);
        }
        return classDOFromDb;
    }

    private SchoolDO saveIfNotPresent(final SchoolRepository schoolRepository, final SchoolDO schoolDO) {
        final SchoolDO schoolDOFromDb = schoolRepository.findByName(schoolDO.getName());
        if (schoolDOFromDb == null) {
            return save(schoolRepository, schoolDO);
        }
        return schoolDOFromDb;
    }


    private UserDO saveIfNotPresent(final UserRepository userRepository, final UserDO userDO) {
        final UserDO userDOFromDb = userRepository.findByMobileNumberAndUserType(userDO.getMobileNumber(), UserType.ADMIN);
        if (userDOFromDb == null) {
            return save(userRepository, userDO);
        }
        return userDOFromDb;
    }

    private <T> T save(final CrudRepository crudRepository, final T t) {
        try {
            return (T) crudRepository.save(t);
        } catch (final Exception e) {
            log.error("Data already exits ", e);
            throw new RuntimeException(e);
        }
    }
}
