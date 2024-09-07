package com.kotan4ik.tests.order;

import com.kotan4ik.models.Order;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.kotan4ik.requests.CourierApiMethods.*;
import static com.kotan4ik.requests.OrderApiMethods.*;
import static com.kotan4ik.utils.ApiErrorMessages.*;
import static com.kotan4ik.utils.Assertions.*;

public class AcceptOrderTest {

    private static final String CORRECT_LOGIN = "myOriginalLogin";
    private static final String CORRECT_PASSWORD = "myPassword";
    private static final String CORRECT_NAME = "originalName";

    private int courierId;
    private int orderId;

    @BeforeEach
    public void setUp() {
        createCourier(CORRECT_LOGIN, CORRECT_PASSWORD, CORRECT_NAME);
        courierId = getCourierIdByLoginAndPassword(CORRECT_LOGIN, CORRECT_PASSWORD);
        Response response = createOrder(Order.createDefaultCorrectOrder());
        int trackId = parseTrack(response);
        orderId = getOrderIdByTrack(trackId);
    }

    @Test
    @Description("Позитивный тест принятия заказа курьером. Должен вернуть код 200 и тело \"true:ok\"")
    public void positiveTestShouldReturn200Ok() {
        Response response = acceptOrder(orderId, courierId);
        compareResponseCode(response, HttpStatus.SC_OK);
        isSuccessfulResponseBody(response);
    }

    @Test
    @Description("Негативный тест принятия заказа курьером. Передается запрос без id курьера. Должен вернуть код 400 и тело с сообщением об ошибке")
    public void negativeTestWithoutCourierIdShouldReturn400BadRequest() {
        Response response = acceptOrder(orderId);
        compareResponseCode(response, HttpStatus.SC_BAD_REQUEST);
        isErrorMessageCorrect(response, SEARCH_NOT_ENOUGH_DATA);
    }

    @Test
    @Description("Негативный тест принятия заказа курьером. Передается запрос без id заказа. Должен вернуть код 400 и тело с сообщением об ошибке")
    public void negativeTestWithoutOrderIdShouldReturn400BadRequest() {
        Response response = acceptOrderWithoutOrderId(courierId);
        compareResponseCode(response, HttpStatus.SC_BAD_REQUEST);
        isErrorMessageCorrect(response, SEARCH_NOT_ENOUGH_DATA);
    }

    @Test
    @Description("Негативный тест принятия заказа курьером. Передается запрос с несуществующим id курьера. Должен вернуть код 404 и тело с сообщением об ошибке")
    public void negativeTestWithIncorrectCourierIdShouldReturn404NotFound() {
        deleteCourier(courierId);
        Response response = acceptOrder(orderId, courierId);
        compareResponseCode(response, HttpStatus.SC_NOT_FOUND);
        isErrorMessageCorrect(response, COURIER_NOT_EXISTS);
    }

    @Test
    @Description("Негативный тест принятия заказа курьером. Передается запрос с несуществующим id заказа. Должен вернуть код 404 и тело с сообщением об ошибке")
    public void negativeTestWithIncorrectOrderIdShouldReturn404NotFound() {
        Response response = acceptOrder(0, courierId);
        compareResponseCode(response, HttpStatus.SC_NOT_FOUND);
        isErrorMessageCorrect(response, ORDER_NOT_EXISTS);
    }

    @AfterEach
    public void tearDown() {
        deleteCourier(courierId);
    }
}
