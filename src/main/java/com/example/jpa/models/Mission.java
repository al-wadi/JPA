package com.example.jpa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Wadi
 * Date 7/21/2024
 **/

@Setter
@Getter
@Entity
public class Mission {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String duration;

    @ManyToMany(mappedBy = "missions")
    private List<Employee> employees;

}
