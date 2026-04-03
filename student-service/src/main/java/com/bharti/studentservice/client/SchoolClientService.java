package com.bharti.studentservice.service;

import com.bharti.studentservice.client.SchoolFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolClientService {

    private final SchoolFeignClient studentFeignClient;


}
