package com.bharti.schoolservice.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequest {
    private String firstName;
    private String lastName;
    private String gender;
    private Integer schoolId;
}
