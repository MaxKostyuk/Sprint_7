package com.kotan4ik.tests.courier;


import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.kotan4ik.requests.CourierApiMethods.createCourier;
import static com.kotan4ik.requests.CourierApiMethods.deleteCourierByLoginAndPassword;
import static com.kotan4ik.utils.ApiErrorMessages.*;
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
    public void positiveTestShouldCreateCourier() {
        Response response = createCourier(CORRECT_LOGIN, CORRECT_PASSWORD, CORRECT_NAME);
        compareResponseCode(response, HttpStatus.SC_CREATED);
        isSuccessfulResponseBody(response);
    }

    @Test
    public void createTwoSameCouriersShouldReturn409Conflict() {
        createCourier(CORRECT_LOGIN, CORRECT_PASSWORD, CORRECT_NAME);
        Response response = createCourier(CORRECT_LOGIN, CORRECT_PASSWORD, CORRECT_NAME);
        compareResponseCode(response, HttpStatus.SC_CONFLICT);
        isErrorMessageCorrect(response, LOGIN_ALREADY_EXISTS);

    }

    @Test
    public void createCourierWithoutLoginShouldReturn400BadRequest() {
        Response response = createCourier(null, CORRECT_PASSWORD, CORRECT_NAME);
        compareResponseCode(response, HttpStatus.SC_BAD_REQUEST);
        isErrorMessageCorrect(response, CREATE_NOT_ENOUGH_DATA);
    }

    @Test
    public void createCourierWithoutPasswordShouldReturn400BadRequest() {
        Response response = createCourier(CORRECT_LOGIN, null, CORRECT_NAME);
        compareResponseCode(response, HttpStatus.SC_BAD_REQUEST);
        isErrorMessageCorrect(response, CREATE_NOT_ENOUGH_DATA);
    }
}
