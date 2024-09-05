package com.kotan4ik.tests.courier;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.kotan4ik.requests.CourierApiMethods.*;
import static com.kotan4ik.utils.ApiErrorMessages.COURIER_ID_NOT_FOUND;
import static com.kotan4ik.utils.ApiErrorMessages.DELETE_NOT_ENOUGH_DATA;
import static com.kotan4ik.utils.Assertions.*;


public class DeleteCourierTest {

    private static final String CORRECT_LOGIN = "myOriginalLogin";
    private static final String CORRECT_PASSWORD = "myPassword";
    private static final String CORRECT_NAME = "originalName";
    private int courierId;

    @BeforeEach
    public void setUp() {
        createCourier(CORRECT_LOGIN, CORRECT_PASSWORD, CORRECT_NAME);
        courierId = getCourierIdByLoginAndPassword(CORRECT_LOGIN, CORRECT_PASSWORD);
    }

    @AfterEach
    public void tearDown() {
        deleteCourier(courierId);
    }

    @Test
    @Description("Позитивный тест удаления курьера. Должен вернуть код 200 и тело \"true:ok\"")
    public void positiveTestShouldReturn200Ok() {
        Response response = deleteCourier(courierId);
        compareResponseCode(response, HttpStatus.SC_OK);
        isSuccessfulResponseBody(response);
    }

    @Test
    @Description("Негативный тест удаления курьера. Передается запрос без id удаляемого курьера. Должен вернуть код 400 и тело с сообщением об ошибке")
    public void negativeTestWithoutIdShouldReturn400BadRequest() {
        Response response = deleteCourier();
        compareResponseCode(response, 400);
        isErrorMessageCorrect(response, DELETE_NOT_ENOUGH_DATA);
    }

    @Test
    @Description("Негативный тест удаления курьера. Передается запрос на удаление курьера с несуществующим id. Должен вернуть код 404 и тело с сообщением об ошибке")
    public void negativeTestShouldReturn404NotFound() {
        deleteCourier(courierId);
        Response response = deleteCourier(courierId);
        compareResponseCode(response, HttpStatus.SC_NOT_FOUND);
        isErrorMessageCorrect(response, COURIER_ID_NOT_FOUND);
    }
}
