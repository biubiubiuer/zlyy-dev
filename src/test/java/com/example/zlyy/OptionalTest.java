package com.example.zlyy;

import lombok.Data;

import java.util.Optional;

@Data
class A {
    private String a;
    private String b;
    private String c;
}

public class OptionalTest {

    public static void main(String[] args) {
        A a = new A();
        a.setA("aa");
        a.setB("bb");
        
        Optional<String> strOptional_a = Optional.ofNullable(a.getA());
        Optional<String> strOptional_b = Optional.ofNullable(a.getB());
        Optional<String> strOptional_c = Optional.ofNullable(a.getC());

        System.out.println(strOptional_a.get());
        System.out.println(strOptional_b.get());
        if (strOptional_c.isPresent()) {
            System.out.println(strOptional_c.get());
        }
    }
}
