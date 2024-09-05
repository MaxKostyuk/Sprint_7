package com.kotan4ik.requests;

import io.restassured.RestAssured;

public class BaseApiConfig {

    static {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/api/v1/";
    }
}
