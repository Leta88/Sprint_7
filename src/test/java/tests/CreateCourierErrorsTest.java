package tests;

import api.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CourierData;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class CreateCourierErrorsTest {

    private final String CourierNamePrefix = "CourierError";

    @DisplayName("Check that it is impossible to create two identical couriers")
    @Test
    public void impossibleToCreateTwoIdenticalCouriersTest(){

        String loginParam = CourierNamePrefix + RandomStringUtils.randomAlphabetic(4);
        CourierData courierData = new CourierData(loginParam, "passwordV1234534", "Petr");
        CourierApi courierApi = new CourierApi();

        ValidatableResponse response = courierApi.createCourier(courierData);
        response = courierApi.createCourier(courierData);
        response.log().all()
                .statusCode(HttpStatus.SC_CONFLICT)
                .and()
                .assertThat()
                .body("message", is("Этот логин уже используется"));
    }


    @DisplayName("Check courier can not be created without First Name")
    @Test
    public void courierCanBeCreatedWithoutFirstNameTest() {

        CourierData courierData = new CourierData(null, "passwordVlad1234534", "Petr");
        CourierApi courierApi = new CourierApi();
        ValidatableResponse response = courierApi.createCourier(courierData);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @DisplayName("Check courier can not be created without Password")
    @Test
    public void courierCanBeCreatedWithEmptyFirstNameTest() {
        String loginParam = CourierNamePrefix + RandomStringUtils.randomAlphabetic(4);
        CourierData courierData = new CourierData(loginParam, null, "Alex");

        CourierApi courierApi = new CourierApi();
        ValidatableResponse response = courierApi.createCourier(courierData);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }
}
