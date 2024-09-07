package com.kotan4ik.tests.order;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static com.kotan4ik.requests.OrderApiMethods.getOrderList;
import static com.kotan4ik.utils.Assertions.compareResponseCode;
import static com.kotan4ik.utils.Assertions.hasListOfOrdersBody;

public class GetListOfOrdersTest {

    @Test
    @Description("Позитивный тест получения списка заказов. Ответ должен содержать код 200 и json объект со списком заказов, секциями pageInfo и availableStations")
    public void positiveTestShouldReturn200Ok() {
        Response response = getOrderList(null, null, 1, null);
        compareResponseCode(response, HttpStatus.SC_OK);
        hasListOfOrdersBody(response);
    }
}
