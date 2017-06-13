package com.example.wedad.design.welcomscreens.beanspkg;

import java.io.Serializable;

/**
 * Created by wedad on 5/1/2017.
 */

public class User implements Serializable {
    String firstName;
    String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
