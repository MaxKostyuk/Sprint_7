package com.kotan4ik.tests;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.kotan4ik.requests.CourierApiRequests.*;
import static com.kotan4ik.utils.ApiErrorMessages.ACCOUNT_NOT_FOUND;
import static com.kotan4ik.utils.ApiErrorMessages.LOGIN_NOT_ENOUGH_DATA;
import static com.kotan4ik.utils.Assertions.*;

public class LoginCourierTest {
    private static final String CORRECT_LOGIN = "myOriginalLogin";
    private static final String CORRECT_PASSWORD = "myPassword";
    private static final String CORRECT_NAME = "originalName";

    @BeforeEach
    public void setUp() {
        createCourier(CORRECT_LOGIN, CORRECT_PASSWORD, CORRECT_NAME);
    }

    @AfterEach
    public void tearDown() {
        deleteCourierByLoginAndPassword(CORRECT_LOGIN, CORRECT_PASSWORD);
    }

    @Test
    public void positiveTestLoginCourierShouldReturn200AndCourierLogin() {
        Response response = loginCourier(CORRECT_LOGIN, CORRECT_PASSWORD);
        compareResponseCode(response, HttpStatus.SC_OK);
        isSuccessfulLoginBody(response);
    }

    @Test
    public void negativeTestWithoutLoginShouldReturn400BadRequest() {
        Response response = loginCourier(null, CORRECT_PASSWORD);
        compareResponseCode(response, HttpStatus.SC_BAD_REQUEST);
        isErrorMessageCorrect(response, LOGIN_NOT_ENOUGH_DATA);
    }

    @Test
    public void negativeTestWithoutPasswordShouldReturn400BadRequest() {
        Response response = loginCourier(CORRECT_LOGIN, null);
        compareResponseCode(response, HttpStatus.SC_BAD_REQUEST);
        isErrorMessageCorrect(response, LOGIN_NOT_ENOUGH_DATA);
    }

    @Test
    public void negativeTestWithNotExistingLoginShouldReturn404NotFound() {
        deleteCourierByLoginAndPassword(CORRECT_LOGIN, CORRECT_PASSWORD);
        Response response = loginCourier(CORRECT_LOGIN, CORRECT_PASSWORD);
        compareResponseCode(response, HttpStatus.SC_NOT_FOUND);
        isErrorMessageCorrect(response, ACCOUNT_NOT_FOUND);
    }

    @Test
    public void negativeTestWithIncorrectPasswordShouldReturn() {
        Response response = loginCourier(CORRECT_LOGIN, CORRECT_PASSWORD + 1);
        compareResponseCode(response, HttpStatus.SC_NOT_FOUND);
        isErrorMessageCorrect(response, ACCOUNT_NOT_FOUND);
    }
}
