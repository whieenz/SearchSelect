package com.whieenz.searchselect;

/**
 * Created by heziwen on 2018/4/13.
 * 作用：
 */

public class PersonBean {
    private String name;
    private String sex;
    private int age;

    public PersonBean(String name, String sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
