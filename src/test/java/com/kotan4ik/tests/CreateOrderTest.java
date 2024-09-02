package com.kotan4ik.tests;


import com.kotan4ik.models.Order;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.kotan4ik.requests.OrderApiRequests.createOrder;
import static com.kotan4ik.utils.Assertions.compareResponseCode;
import static com.kotan4ik.utils.Assertions.isSuccessfulCreateOrderBody;

public class CreateOrderTest {

    public static final String CORRECT_FIRST_NAME = "Ivan";
    public static final String CORRECT_LAST_NAME = "Ivanov";
    public static final String CORRECT_ADDRESS = "Manezhnaya st, 2-10";
    public static final String CORRECT_SUBWAY_STATION = "Ploshad Revolutsii";
    public static final String CORRECT_PHONE = "+78007009329";
    public static final String CORRECT_RENT_TIME = "100";
    public static final String CORRECT_DELIVERY_DATE = "2024-10-30";
    public static final String CORRECT_COMMENT = "Abiens, abi!";

    @ParameterizedTest
    @MethodSource("colorProvider")
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
