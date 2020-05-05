package com.eproject.entity;

import lombok.Data;

import javax.persistence.*;

//省份
@Data
@Table(name = "t_address_province")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;
    @Column(name = "code" ,length=6)
    private int Code;
    @Column(name = "name")
    private String Name;
}
