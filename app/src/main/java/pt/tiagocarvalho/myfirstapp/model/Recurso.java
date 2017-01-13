package pt.tiagocarvalho.myfirstapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tiago.carvalho on 12/19/16.
 */

public class Recurso implements Serializable {
    private int id;
    private String name;
    private int age;
    private String email;
    private byte[] image;

    public Recurso(int id, String name, String email, int age, byte[] image) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.image = image;
        this.email = email;
    }

    public Recurso(String name, String email, int age, byte[] image) {
        this.name = name;
        this.age = age;
        this.image = image;
        this.email = email;
    }

    public Recurso() {
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImageId(byte[] imageId) {
        this.image = image;
    }
}
