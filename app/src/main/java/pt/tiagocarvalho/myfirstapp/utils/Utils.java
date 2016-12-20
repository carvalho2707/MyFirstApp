package pt.tiagocarvalho.myfirstapp.utils;

import android.text.TextUtils;

import java.util.ArrayList;

import pt.tiagocarvalho.myfirstapp.model.User;

/**
 * Created by tiago.carvalho on 12/20/16.
 */

public class Utils {

    public static ArrayList<User> filterData(ArrayList<User> userList, String personName, int minAge, int maxAge, String techList, String state) {
        ArrayList<User> userFiltered = new ArrayList<>();
        for (User user : userList) {
            boolean nameOk = true;
            if (!TextUtils.isEmpty(personName)) {
                if (!personName.equals(user.getName())) {
                    nameOk = false;
                }
            }
            boolean techOk = true;
            if (!TextUtils.isEmpty(techList)) {
                String[] techs = techList.split(",");
                for (String tech : techs) {
                    if (!user.getTechList().contains(tech)) {
                        techOk = false;
                    }
                }
            }

            boolean ageOk = true;
            if (user.getAge() < minAge || user.getAge() > maxAge) {
                ageOk = false;
            }
            if (nameOk && techOk && ageOk) {
                userFiltered.add(user);
            }
        }
        return userFiltered;
    }
}
