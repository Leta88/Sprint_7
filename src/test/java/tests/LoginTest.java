package tests;

import api.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CourierData;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTest {

    protected int courierId;
    protected CourierData courierData;
    protected CourierApi courierApi;
    private final String CourierNamePrefix = "CourierLogin";

    @Before
    public void setUp() {
        courierApi = new CourierApi();
        String loginParam = CourierNamePrefix + RandomStringUtils.randomAlphabetic(4);
        String passwordParam = RandomStringUtils.randomAlphabetic(6);
        courierData = new CourierData(loginParam, passwordParam, "John");
        ValidatableResponse response = courierApi.createCourier(courierData);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }

    @After
    public void cleanUp() {
        ValidatableResponse response = courierApi.deleteCourier(courierId);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @DisplayName("Check courier can Log in")
    @Test
    public void courierCanLoginTest(){
        courierData.setFirstName(null);
        ValidatableResponse response = courierApi.loginCourier(courierData);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue());

        courierId = response.extract().path("id");
    }
}
