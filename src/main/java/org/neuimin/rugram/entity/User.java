package org.neuimin.rugram.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "users")
@SequenceGenerator(name = "userIdSeq", sequenceName = "user_id_seq", allocationSize = 1)
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userIdSeq")
    @Column(name = "id", nullable = false, columnDefinition = "serial")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "gender")
    private String gender;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "hardskills")
    private String hardskills;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "isDeleted")
    private boolean isDeleted;

    public User() {

    }

    public User(String name, String surname, String middleName, Date birthday, String gender, City city, String imageUrl, String description, String nickname, String hardskills, String email, String phone, boolean isDeleted) {
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.birthday = birthday;
        this.gender = gender;
        this.city = city;
        this.imageUrl = imageUrl;
        this.description = description;
        this.nickname = nickname;
        this.hardskills = hardskills;
        this.email = email;
        this.phone = phone;
        this.isDeleted = isDeleted;
    }

    public User(Long id, String name, String surname, String middleName, Date birthday, String gender, City city, String imageUrl, String description, String nickname, String hardskills, String email, String phone, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.birthday = birthday;
        this.gender = gender;
        this.city = city;
        this.imageUrl = imageUrl;
        this.description = description;
        this.nickname = nickname;
        this.hardskills = hardskills;
        this.email = email;
        this.phone = phone;
        this.isDeleted = isDeleted;
    }
}
