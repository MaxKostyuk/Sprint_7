package com.kotan4ik.requests;

import com.kotan4ik.models.Order;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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

    @Step("Sending request to accept order {orderId} by courier {courierId}")
    public static Response acceptOrder(int orderId, int courierId) {
        Response response = RestAssured.given()
                .queryParam("courierId", courierId)
                .put("orders/accept/" + orderId);
        return response;
    }

    @Step("Sending request to accept order {orderId} without courier")
    public static Response acceptOrder(int orderId) {
        Response response = RestAssured.given()
                .put("orders/accept/" + orderId);
        return response;
    }

    @Step("Sending request to accept order without order id")
    public static Response acceptOrderWithoutOrderId(int courierId) {
        Response response = RestAssured.given()
                .queryParam("courierId", courierId)
                .put("orders/accept/");
        return response;
    }

    @Step("Getting order by track number {track}")
    public static Response getOrderByTrack(int track) {
        Response response = RestAssured.given()
                .queryParam("t", track)
                .get("orders/" + "track");
        return response;
    }

    @Step("Getting order without track number")
    public static Response getOrderWithoutTrack() {
        Response response = RestAssured.given()
                .get("orders/" + "track");
        return response;
    }

    @Step("Getting orderId by track number {track}")
    public static int getOrderIdByTrack(int track) {
        Response response = getOrderByTrack(track);
        return parseOrderIdFromGetOrderResponse(response);
    }

    @Step("Parsing order response to get order id")
    private static int parseOrderIdFromGetOrderResponse(Response response) {
        return Integer.parseInt(response.path("order.id").toString());
    }

    @Step("Parsing create order body to get track")
    public static int parseTrack(Response response) {
        return Integer.parseInt(response.path("track").toString());
    }

    @Step("Getting order list with courier id {courierId}, nearestStation {")
    public static Response getOrderList(Integer courierId, int[] nearestStation, Integer limit, Integer page) {
        RequestSpecification request = RestAssured.given();
        if (courierId != null)
            request.queryParam("courierId", courierId);
        if (nearestStation != null)
            request.queryParam("nearestStation", nearestStation);
        if (limit != null)
            request.queryParam("limit", limit);
        if (page != null)
            request.queryParam("page", page);
        Response response = request.get("orders");
        return response;
    }
}
