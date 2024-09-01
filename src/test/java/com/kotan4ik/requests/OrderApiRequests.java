package com.kotan4ik.requests;

import com.kotan4ik.models.Order;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class OrderApiRequests {
    static {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/api/v1/";
    }

    @Step("Sending request to create order {order}")
    public static Response createOrder(Order order) {
        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .body(order)
                .post("orders/");
        return response;
    }
}
