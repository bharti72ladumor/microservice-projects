package com.bharti.studentservice.controller;

import com.bharti.studentservice.dto.SchoolDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "school-service", path = "/school/")
public interface SchoolFeignClient {

    @GetMapping("/by/{id}")
    SchoolDTO getStudentById(@PathVariable int id);
}
