package com.example.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "candidate")
@Getter
@Setter
@Builder
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String gender;
    private Double salaryExpected;
    private String status;

    public Candidate() {
    }

    public Candidate(Long id, String name, String email, String gender, Double salaryExpected, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.salaryExpected = salaryExpected;
        this.status = status;
    }
}
