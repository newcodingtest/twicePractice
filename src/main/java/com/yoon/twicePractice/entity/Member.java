package com.yoon.twicePractice.entity;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseEntity {
    @Id
    private String email;

    private String password;

    private String name;
}
