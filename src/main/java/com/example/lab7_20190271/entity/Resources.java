package com.example.lab7_20190271.entity;

import jakarta.persistence.*;

@Entity
@Table(name="resources")
public class Resources {
    @Id
    @Column(name="resourceId", nullable=false)
    private Integer id;

    @Column(name = "name")
    private String name;
}
