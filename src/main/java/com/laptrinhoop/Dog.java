package com.laptrinhoop;

import javax.validation.constraints.NotEmpty;

public class Dog implements Animal {
    @NotEmpty
    private String name;
    public void make() {
        System.out.println("Hello");
    }
}
