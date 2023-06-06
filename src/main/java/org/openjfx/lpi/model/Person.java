package org.openjfx.lpi.model;

import java.text.SimpleDateFormat;

import org.openjfx.lpi.controller.utils.HasName;

import java.sql.Date;

public class Person extends Record implements HasName {

    private String name;
    private String gender;
    private Date birth;

    public Person (
        Integer id,
        String name,
        String gender,
        Date birth
    ) {
        super(id);
        this.name = name;
        this.gender = gender;
        this.birth = birth;
    }

    public Person (String name, String gender, Date birth) {
        this(null, name, gender, birth);
    }

    public Person (int id, String name, String gender, Date birth) {
        this((Integer)id, name, gender, birth);
    }

    public String getName() { return name; }
    public String getGender() { return gender; }
    public Date getBirth() { return birth; }
    public String getBirthString() {
        return new SimpleDateFormat("dd-MM-yyyy").format(birth);
    }

    public void setName(String nome) { this.name = nome; }
    public void setGender(String genero) { this.gender = genero; }
    public void setBirth(Date nascimento) { this.birth = nascimento; }

}