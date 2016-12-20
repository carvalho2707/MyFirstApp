package pt.tiagocarvalho.myfirstapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tiago.carvalho on 12/19/16.
 */

public class User implements Serializable {
    private String name;
    private int age;
    private ArrayList<String> techList;

    public String getName() {
        return name;
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

    public ArrayList<String> getTechList() {
        return techList;
    }

    public void setTechList(ArrayList<String> techList) {
        this.techList = techList;
    }
}
