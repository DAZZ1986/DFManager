package com.DFM.DFM_transfer_module.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "field_position")
    private String position;

    @Column(name = "skill_lvl")
    private int skill_lvl;

    @Column(name = "price")
    private int price;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;


    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public int getSkill_lvl() {
        return skill_lvl;
    }
    public void setSkill_lvl(int skill_lvl) {
        this.skill_lvl = skill_lvl;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public Club getClub() {
        return club;
    }
    public void setClub(Club club) {
        this.club = club;
    }

}
