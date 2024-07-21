package com.example.jpa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by Wadi
 * Date 7/21/2024
 **/

@Setter
@Getter
@Entity
public class Address {

    @Id
    @GeneratedValue
    private Integer id;

    private String streetName;

    private String houseNumber;

    private String zipCode;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
