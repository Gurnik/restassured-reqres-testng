package tests;

import base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetUserTest extends BaseTest {

    @Test
    public void getUserById() {
        given()
                .spec(requestSpec)
                .when()
                .get("/api/users/2")
                .then()
                .spec(responseSpec)
                .body("data.id", equalTo(2))
                .body("data.first_name", equalTo("Janet"));
    }
}
