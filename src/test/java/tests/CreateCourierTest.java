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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;

@RunWith(Parameterized.class)
public class CreateCourierTest {

    protected int courierId;
    protected CourierData courierData;
    protected CourierApi courierApi;

    private final String login;
    private final String password;
    private final String firstName;

    public CreateCourierTest (String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {"CourierFull" + RandomStringUtils.randomAlphabetic(4), "passwordqwery", "Vlad"},
                {"CourierWithoutFN" + RandomStringUtils.randomAlphabetic(4), "passwordqwery", null}, //create courier without First Name
                {"CourierWithEmptyFN" + RandomStringUtils.randomAlphabetic(4), "passwordqwery", ""}, //create courier with empty First Name
        };
    }

    @Before
    public void setUp() {
        courierApi = new CourierApi();
        courierData = new CourierData(login, password, firstName);
    }

    @After
    public void cleanUp() {
        courierData.setFirstName(null);
        ValidatableResponse response = courierApi.loginCourier(courierData);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

        courierId = response.extract().path("id");
        response = courierApi.deleteCourier(courierId);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @DisplayName("Check courier can be created")
    @Test
    public void courierCanBeCreatedTest() {
        ValidatableResponse response = courierApi.createCourier(courierData);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }
}
