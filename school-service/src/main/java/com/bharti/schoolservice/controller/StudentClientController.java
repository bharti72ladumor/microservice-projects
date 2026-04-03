package com.bharti.schoolservice.controller;

import com.bharti.schoolservice.payload.StudentDTO;
import com.bharti.schoolservice.payload.StudentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "student-service", path = "/student")
public interface StudentClientController {
    @PostMapping("/save")
    ResponseEntity<StudentDTO> save(@RequestBody StudentRequest request);
}
