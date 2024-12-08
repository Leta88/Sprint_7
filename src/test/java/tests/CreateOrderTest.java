package tests;

import api.CourierApi;
import api.OrderApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CourierData;
import model.OrderData;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final List<String> color;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    protected OrderApi orderApi;
    protected OrderData orderData;

    public CreateOrderTest(List<String> color, String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment) {
        this.color = color;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {Arrays.asList("BLACK", "GREY"), "Иван", "Петров", "пр. Ленина д.1", "Сокольники", "+79998888888", 5, "2025-06-06", "Как дела?"},
                {Arrays.asList("GREY"), "Алексей", "Иванов", "пр. Речной д.1", "Динамо", "+79998888877", 4, "2025-01-01", "Не звоните"},
                {Arrays.asList("BLACK"), "Иван", "Петров", "пр. Ленина д.1", "Сокольники", "+79998888866", 3, "2025-02-10", ""},
        };
    }

    @Before
    public void setUp() {
        orderApi = new OrderApi();
        orderData = new OrderData(color, firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
    }

    @DisplayName("Check order can be created")
    @Test
    public void orderCanBeCreatedTest(){
        ValidatableResponse response = orderApi.createOrder(orderData);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", notNullValue());
    }
}
