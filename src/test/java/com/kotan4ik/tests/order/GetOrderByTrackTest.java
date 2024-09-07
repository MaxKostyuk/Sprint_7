package com.kotan4ik.tests.order;

import com.kotan4ik.models.Order;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static com.kotan4ik.requests.OrderApiMethods.*;
import static com.kotan4ik.utils.ApiErrorMessages.ORDER_NOT_FOUND;
import static com.kotan4ik.utils.ApiErrorMessages.SEARCH_NOT_ENOUGH_DATA;
import static com.kotan4ik.utils.Assertions.*;

public class GetOrderByTrackTest {

    @Test
    @Description("Позитивный тест получения заказа по track номеру. Должен вернуть код 200 и тело с json объектом заказа")
    public void positiveTestShouldReturn200Ok() {
        Response response = createOrder(Order.createDefaultCorrectOrder());
        int track = parseTrack(response);
        response = getOrderByTrack(track);
        compareResponseCode(response, HttpStatus.SC_OK);
        hasOrderBody(response);
    }

    @Test
    @Description("Негативный тест получения заказа по track номеру. Передается запрос с несуществующим track номером. Должен вернуть код 404 и тело с сообщением об ошибке")
    public void negativeTestWithIncorrectTrackShouldReturn404NotFound() {
        Response response = getOrderByTrack(0);
        compareResponseCode(response, HttpStatus.SC_NOT_FOUND);
        isErrorMessageCorrect(response, ORDER_NOT_FOUND);
    }

    @Test
    @Description("Негативный тест получения заказа по track номеру. Передается запрос без track номера. Должен вернуть код 400 и тело с сообщением об ошибке")
    public void negativeTestWithoutTrackShouldReturn400BadRequest() {
        Response response = getOrderWithoutTrack();
        compareResponseCode(response, HttpStatus.SC_BAD_REQUEST);
        isErrorMessageCorrect(response, SEARCH_NOT_ENOUGH_DATA);
    }
}
