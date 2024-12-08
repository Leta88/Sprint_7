package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.CourierData;

import static io.restassured.RestAssured.given;

public class CourierApi extends RestApi{

    public static final String CREATE_COURIER_URI = "/api/v1/courier";
    public static final String LOGIN_COURIER_URI = "/api/v1/courier/login";
    public static final String DELETE_COURIER_URI = "/api/v1/courier";

    @Step("Create courier")
    public ValidatableResponse createCourier(CourierData courier){
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(CREATE_COURIER_URI)
                .then();
    }

    @Step("Delete courier")
    public ValidatableResponse deleteCourier(int courierId){
        String body = "{ \"id\": " + courierId + " }";
        return given()
                .spec(requestSpecification())
                .and()
                .body(body)
                .when()
                .delete(DELETE_COURIER_URI + "/" + courierId)
                .then();
    }

    @Step("Login")
    public ValidatableResponse loginCourier(CourierData courier){
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER_URI)
                .then();
    }
}
