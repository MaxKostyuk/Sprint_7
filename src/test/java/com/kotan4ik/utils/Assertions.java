package com.kotan4ik.utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Assertions {

    @Step("Checking response code equals {code}")
    public static void compareResponseCode(Response response, int code) {
        response.then().statusCode(code);
    }

    @Step("Checking response body sends success")
    public static void isSuccessfulResponseBody(Response response) {
        response.then()
                .body("$", not(empty()))
                .body("$", aMapWithSize(1))
                .body("", equalTo(Map.of("ok", true)));
    }

    @Step("Checking response body for login contains id and id is positive")
    public static void isSuccessfulLoginBody(Response response) {
        response.then()
                .body("$", not(empty()))
                .body("$", aMapWithSize(1))
                .body("id", notNullValue());
        int id = response.then().extract().path("id");
        assertTrue(id > 0);
    }

    @Step("Checking response body for create order contains track and track is positive")
    public static void isSuccessfulCreateOrderBody(Response response) {
        response.then()
                .body("$", not(empty()))
                .body("$", aMapWithSize(1))
                .body("track", notNullValue());
        int id = response.then().extract().path("track");
        assertTrue(id > 0);
    }

    @Step("Checking error message equals {expectedMessage}")
    public static void isErrorMessageCorrect(Response response, String expectedMessage) {
        response.then()
                .body("$", not(empty()))
                .body("$", aMapWithSize(1))
                .body("message", notNullValue())
                .body("message", equalTo(expectedMessage));
    }

    @Step("Checking response has order in its body")
    public static void hasOrderBody(Response response) {
        response.then()
                .body("$", not(empty()))
                .body("$", aMapWithSize(1))
                .body("order", notNullValue());
    }

    @Step("Checking response has list of orders in its body")
    public static void hasListOfOrdersBody(Response response) {
        response.then()
                .body("$", not(empty()))
                .body("$", aMapWithSize(3))
                .body("orders", notNullValue())
                .body("pageInfo", notNullValue())
                .body("availableStations", notNullValue());
    }
}
