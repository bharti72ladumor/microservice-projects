package com.bharti.studentservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private SchoolDTO school;
}
