package com.kotan4ik.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private static final String CORRECT_FIRST_NAME = "Ivan";
    private static final String CORRECT_LAST_NAME = "Ivanov";
    private static final String CORRECT_ADDRESS = "Manezhnaya st, 2-10";
    private static final String CORRECT_SUBWAY_STATION = "Ploshad Revolutsii";
    private static final String CORRECT_PHONE = "+78007009329";
    private static final String CORRECT_RENT_TIME = "100";
    private static final String CORRECT_DELIVERY_DATE = "2024-10-30";
    private static final String CORRECT_COMMENT = "Abiens, abi!";

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public static Order createDefaultCorrectOrder() {
        return new Order(CORRECT_FIRST_NAME,
                CORRECT_LAST_NAME,
                CORRECT_ADDRESS,
                CORRECT_SUBWAY_STATION,
                CORRECT_PHONE,
                CORRECT_RENT_TIME,
                CORRECT_DELIVERY_DATE,
                CORRECT_COMMENT,
                null);
    }
}