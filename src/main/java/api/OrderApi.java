package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.OrderData;

import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi{

    public static final String CREATE_ORDER_URI = "/api/v1/orders";
    public static final String GET_ORDER_LIST_URI = "/api/v1/orders";


    @Step("Create order")
    public ValidatableResponse createOrder(OrderData order){
        return given()
                .spec(requestSpecification())
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER_URI)
                .then();
    }

    @Step("Get list of all orders")
    public ValidatableResponse getAllOrderList(){
        return given()
                .spec(requestSpecification())
                .and()
                .body("")
                .when()
                .get(GET_ORDER_LIST_URI)
                .then();
    }
}
