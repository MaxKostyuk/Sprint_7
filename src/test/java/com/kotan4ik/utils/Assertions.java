package com.kotan4ik.utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static org.hamcrest.Matchers.*;

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
        assert id > 0;
    }

    @Step("Checking error message equals {expectedMessage}")
    public static void isErrorMessageCorrect(Response response, String expectedMessage) {
        response.then()
                .body("$", not(empty()))
                .body("$", aMapWithSize(1))
                .body("message", notNullValue())
                .body("message", equalTo(expectedMessage));
    }
}
