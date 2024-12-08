package tests;

import api.OrderApi;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class AllOrderListTest {

    @Test
    public void listOfAllOrdersIsNotNull(){
        OrderApi orderApi = new OrderApi();
        ValidatableResponse response = orderApi.getAllOrderList();
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(notNullValue());
    }
}
