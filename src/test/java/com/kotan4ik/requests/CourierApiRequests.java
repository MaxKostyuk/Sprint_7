package com.kotan4ik.requests;

import com.kotan4ik.models.Courier;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CourierApiRequests {
    static {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/api/v1/";
    }

    @Step("Sending request to create courier with login {login}, password {password} and name {name}")
    public static Response createCourier(String login, String password, String name) {
        Courier courier = new Courier(login, password, name);
        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("courier/");
        return response;
    }

    @Step("Sending request to delete courier with id {id}")
    public static Response deleteCourier(int id) {
        Response response = RestAssured.given()
                .delete("courier/" + id);
        return response;
    }

    @Step("Deleting courier with login {login} and password {password}")
    public static void deleteCourierByLoginAndPassword(String login, String password) {
        Response response = loginCourier(login, password);
        if (response.statusCode() == 200) {
            int id = Integer.parseInt(response.path("id").toString());
            deleteCourier(id);
        }
    }

    @Step("Sending login request")
    public static Response loginCourier(String login, String password) {
        Courier courier = new Courier(login, password, null);
        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("courier/" + "login");
        return response;
    }
}
