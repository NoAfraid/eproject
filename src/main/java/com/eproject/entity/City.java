package com.eproject.entity;

import lombok.Data;

import javax.persistence.*;

//市
@Data
@Entity
@Table(name = "t_address_city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;
    @Column(name = "code" ,length=6)
    private int Code;
    @Column(name = "name")
    private String Name;
    @Column(name = "provinceCode" ,length=6)
    private int ProvinceCode;
}
