package com.kotan4ik.tests.order;


import com.kotan4ik.models.Order;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.kotan4ik.requests.OrderApiMethods.createOrder;
import static com.kotan4ik.utils.Assertions.compareResponseCode;
import static com.kotan4ik.utils.Assertions.isSuccessfulCreateOrderBody;

public class CreateOrderTest {

    @ParameterizedTest
    @MethodSource("colorProvider")
    @Description("Позитивный тест создания заказа. Должен вернуть код 201 и тело с трек номером созданного заказа. " +
            "Проверяется создание заказа с указанием каждого цвета, обоих цветов одновременно и без указания цвета")
    public void positiveTestShouldReturn200OkAndTrackNumber(List<String> colors) {
        Order order = Order.createDefaultCorrectOrder();
        order.setColor(colors);
        Response response = createOrder(order);
        compareResponseCode(response, HttpStatus.SC_CREATED);
        isSuccessfulCreateOrderBody(response);
    }

    private static Stream<List<String>> colorProvider() {
        return Stream.of(
                List.of("GREY"),
                List.of("BLACK"),
                List.of("BLACK", "GREY"),
                List.of()
        );
    }
}
