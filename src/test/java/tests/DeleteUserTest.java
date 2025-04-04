package tests;

import base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteUserTest extends BaseTest {

    @Test
    public void deleteUser() {
        System.out.println("Step 1: Checking user exists before deletion");

        given()
                .spec(requestSpec)
                .when()
                .get("/api/users/2")
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Step 2: Sending DELETE request");

        given()
                .spec(requestSpec)
                .when()
                .delete("/api/users/2")
                .then()
                .statusCode(204)
                .log().all();

        System.out.println("✅ User deletion request sent successfully (204 No Content)");

        // Step 3: In a real API, you would now re-check GET /users/2 to ensure 404
        // But in Reqres, it will still return 200 because it doesn’t persist deletions
    }
}
