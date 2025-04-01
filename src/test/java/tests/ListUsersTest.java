package tests;

import base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ListUsersTest extends BaseTest {

    @Test
    public void getUsersByPageNumber() {
        System.out.println("Fetching users on page 2");

        given()
                .spec(requestSpec)
                .when()
                .get("/api/users?page=2")
                .then()
                .spec(responseSpec)
                .body("page", equalTo(2))
                .body("data.size()", equalTo(6))
                .log().all(); // Explicit, controlled logging
    }
}
