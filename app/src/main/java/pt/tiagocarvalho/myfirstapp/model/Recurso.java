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
    private int imageId;
    private String email;

    public Recurso(int id, String name, String email, int age, int imageId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.imageId = imageId;
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
