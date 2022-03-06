package com.example.email2.DTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmailDTO {
    private String From;
    private String To;
    private String Subject;
    private String Content;
}