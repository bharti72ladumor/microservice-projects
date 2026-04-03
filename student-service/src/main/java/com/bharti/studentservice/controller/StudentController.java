package com.bharti.studentservice.controller;

import com.bharti.studentservice.entity.Student;
import com.bharti.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    @GetMapping("/hello")
    public String hello() {
        return  "This is hello from student service";
    }

    @GetMapping("/authenticate")
    public String hello2() {
        return  "This is hello from student service Authenticated";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchStudentById(@PathVariable int id){
        return studentService.fetchStudentById(id);
    }

    @GetMapping("/getAll")
    public List<Student> getall() {
        return studentService.getAllStudent();
    }
    @PostMapping("/save")
    public Student createStudent(@RequestBody Student student){
        return studentService.saveStudent(student);
    }
}
