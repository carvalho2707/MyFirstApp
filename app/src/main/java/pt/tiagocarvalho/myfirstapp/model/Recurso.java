package pt.tiagocarvalho.myfirstapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tiago.carvalho on 12/19/16.
 */

public class Recurso implements Serializable {
    private long id;
    private String name;
    private int age;
    private String email;
    private String image;

    public Recurso(long id, String name, String email, int age, String image) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.image = image;
        this.email = email;
    }

    public Recurso(String name, String email, int age, String image) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
