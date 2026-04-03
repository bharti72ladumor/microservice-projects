package com.bharti.studentservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDTO {
    private int id;
    private String schoolName;
    private String location;
    private String principalName;
}
