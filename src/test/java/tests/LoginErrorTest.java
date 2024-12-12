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

public class LoginErrorTest {

    protected int courierId;
    protected CourierData courierData;
    protected CourierApi courierApi;
    private final String CourierNamePrefix = "CourierLoginError";
    String loginParam;
    String passwordParam;

    @Before
    public void setUp() {
        courierApi = new CourierApi();
        loginParam = CourierNamePrefix + RandomStringUtils.randomAlphabetic(4);
        passwordParam = RandomStringUtils.randomAlphabetic(6);
        courierData = new CourierData(loginParam, passwordParam);
        ValidatableResponse response = courierApi.createCourier(courierData);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }

    @After
    public void cleanUp() {
        courierData.setLogin(loginParam);
        courierData.setPassword(passwordParam);

        ValidatableResponse response = courierApi.loginCourier(courierData);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue());

        courierId = response.extract().path("id");

        response = courierApi.deleteCourier(courierId);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @DisplayName("Check courier can not Log in without Login")
    @Test
    public void courierCannotLoginWithoutLoginTest(){
        courierData.setLogin(null);
        ValidatableResponse response = courierApi.loginCourier(courierData);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @DisplayName("Check courier can not Log in without Password")
    @Test
    public void courierCannotLoginWithoutPasswordTest(){
        courierData.setPassword(null);
        ValidatableResponse response = courierApi.loginCourier(courierData);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @DisplayName("Check courier can not Log in with incorrect Data")
    @Test
    public void courierCannotWithIncorrectDataTest(){
        courierData.setLogin(courierData.getLogin() + "1");
        courierData.setPassword(courierData.getPassword() + "1");
        ValidatableResponse response = courierApi.loginCourier(courierData);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }
}
