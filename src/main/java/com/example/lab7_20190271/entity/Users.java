package com.example.lab7_20190271.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="users")
public class Users {
    @Id
    @Column(name = "userId", nullable = false)
    private Integer Id;
    @ManyToOne
    @Column(name = "authorizedResource")
    private Resources resources;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "type", nullable = false, length = 100)
    private String type;
    @Column(name = "active", nullable = false)
    private String active;
}
