package com.kotan4ik.tests.courier;


import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.kotan4ik.requests.CourierApiMethods.createCourier;
import static com.kotan4ik.requests.CourierApiMethods.deleteCourierByLoginAndPassword;
import static com.kotan4ik.utils.ApiErrorMessages.CREATE_NOT_ENOUGH_DATA;
import static com.kotan4ik.utils.ApiErrorMessages.LOGIN_ALREADY_EXISTS;
import static com.kotan4ik.utils.Assertions.*;

public class CreateCourierTest {

    private static final String CORRECT_LOGIN = "myOriginalLogin";
    private static final String CORRECT_PASSWORD = "myPassword";
    private static final String CORRECT_NAME = "originalName";

    @AfterEach
    public void deleteCourier() {
        deleteCourierByLoginAndPassword(CORRECT_LOGIN, CORRECT_PASSWORD);
    }

    @Test
    @Description("Позитивный тест создания курьера. Должен вернуть код 201 и тело \"true:ok\"")
    public void positiveTestShouldCreateCourier() {
        Response response = createCourier(CORRECT_LOGIN, CORRECT_PASSWORD, CORRECT_NAME);
        compareResponseCode(response, HttpStatus.SC_CREATED);
        isSuccessfulResponseBody(response);
    }

    @Test
    @Description("Негативный тест создания курьера. Создается курьер с существующим логином. Должен вернуть код 409 и тело с сообщением об ошибке")
    public void createTwoSameCouriersShouldReturn409Conflict() {
        createCourier(CORRECT_LOGIN, CORRECT_PASSWORD, CORRECT_NAME);
        Response response = createCourier(CORRECT_LOGIN, CORRECT_PASSWORD, CORRECT_NAME);
        compareResponseCode(response, HttpStatus.SC_CONFLICT);
        isErrorMessageCorrect(response, LOGIN_ALREADY_EXISTS);

    }

    @Test
    @Description("Негативный тест создания курьера. Передается запрос без логина. Должен вернуть код 400 и тело с сообщением об ошибке")
    public void createCourierWithoutLoginShouldReturn400BadRequest() {
        Response response = createCourier(null, CORRECT_PASSWORD, CORRECT_NAME);
        compareResponseCode(response, HttpStatus.SC_BAD_REQUEST);
        isErrorMessageCorrect(response, CREATE_NOT_ENOUGH_DATA);
    }

    @Test
    @Description("Негативный тест создания курьера. Передается запрос без пароля. Должен вернуть код 400 и тело с сообщением об ошибке")
    public void createCourierWithoutPasswordShouldReturn400BadRequest() {
        Response response = createCourier(CORRECT_LOGIN, null, CORRECT_NAME);
        compareResponseCode(response, HttpStatus.SC_BAD_REQUEST);
        isErrorMessageCorrect(response, CREATE_NOT_ENOUGH_DATA);
    }
}
