package tests;

import base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserNotFoundTest extends BaseTest {

    @Test
    public void getNonExistingUser() {
        System.out.println("Fetching a non-existing user (ID = 23)");

        given()
                .spec(requestSpec)
                .when()
                .get("/api/users/23")
                .then()
                .statusCode(404)
                .log().all();
    }
}
