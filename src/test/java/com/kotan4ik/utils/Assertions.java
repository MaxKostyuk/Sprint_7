package com.kotan4ik.utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

public class Assertions {

    @Step("Checking response code equals {code}")
    public static void compareResponseCode(Response response, int code) {
        response.then().statusCode(code);
    }

    @Step("Checking response body sends success")
    public static void isSuccessfulResponseBody(Response response) {
        response.then().body("", equalTo(Map.of("ok", true)));
    }

    @Step("Checking error message equals {expectedMessage}")
    public static void isErrorMessageCorrect(Response response, String expectedMessage) {
        response.then().body("message", equalTo(expectedMessage));
    }
}
