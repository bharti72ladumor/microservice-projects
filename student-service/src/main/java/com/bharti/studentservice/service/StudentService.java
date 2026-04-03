package com.bharti.studentservice.service;

import com.bharti.studentservice.controller.SchoolFeignClient;
import com.bharti.studentservice.dto.SchoolDTO;
import com.bharti.studentservice.dto.StudentResponse;
import com.bharti.studentservice.entity.Student;
import com.bharti.studentservice.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final SchoolFeignClient schoolFeignClient;
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public ResponseEntity<?> fetchStudentById(Integer id){
        Optional<Student> student =  studentRepository.findById(id);
        if(student.isPresent()){

            SchoolDTO school = schoolFeignClient.getStudentById(student.get().getSchoolId());
            StudentResponse studentResponse = new StudentResponse(
                    student.get().getId(),
                    student.get().getFirstName(),
                    student.get().getLastName(),
                    student.get().getGender(),
                    school
            );
            return new ResponseEntity<>(studentResponse, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No Student Found",HttpStatus.NOT_FOUND);
        }
    }

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }
}
