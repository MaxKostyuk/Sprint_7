package com.kotan4ik.requests;

import com.kotan4ik.models.Courier;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CourierApiMethods extends BaseApiConfig {

    private static final String BASE_COURIER_URL = "courier/";
    private static final String LOGIN_URL = "login";

    @Step("Sending request to create courier with login {login}, password {password} and name {name}")
    public static Response createCourier(String login, String password, String name) {
        Courier courier = new Courier(login, password, name);
        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .body(courier)
                .post(BASE_COURIER_URL);
        return response;
    }

    @Step("Sending login request with login {login} and password {password}")
    public static Response loginCourier(String login, String password) {
        Courier courier = new Courier(login, password, null);
        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .body(courier)
                .post(BASE_COURIER_URL + LOGIN_URL);
        return response;
    }

    @Step("Sending request to delete courier with id {id}")
    public static Response deleteCourier(int id) {
        Response response = RestAssured.given()
                .delete(BASE_COURIER_URL + id);
        return response;
    }

    @Step("Sending request to delete courier without id")
    public static Response deleteCourier() {
        Response response = RestAssured.given()
                .delete(BASE_COURIER_URL);
        return response;
    }

    @Step("Deleting courier with login {login} and password {password}")
    public static void deleteCourierByLoginAndPassword(String login, String password) {
        int id = getCourierIdByLoginAndPassword(login, password);
        deleteCourier(id);
    }

    @Step("Getting courier id by login {login} and password {password}")
    public static int getCourierIdByLoginAndPassword(String login, String password) {
        Response response = loginCourier(login, password);
        return parseLoginResponseToGetCourierId(response);
    }

    @Step("Parsing login courier body to get courier id")
    public static int parseLoginResponseToGetCourierId(Response response) {
        return Integer.parseInt(response.path("id").toString());
    }
}
