package org.openjfx.lpi.data;

import java.text.SimpleDateFormat;
import java.sql.Date;

public class Person extends Record {

    String name;
    String gender;
    Date birth;

    public Person (
        String name,
        String gender,
        Date birth
    ) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
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