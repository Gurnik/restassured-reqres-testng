package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import utils.UserHelper;

import static io.restassured.RestAssured.given;

public class DeleteUserTest extends BaseTest {

    @Test
    public void deleteUser() {
        int userId = 2;

        System.out.println("Step 1: Check if user exists before deletion");
        UserHelper.assertUserExists(requestSpec, userId);

        System.out.println("Step 2: Sending DELETE request");

        given()
                .spec(requestSpec)
                .when()
                .delete("/api/users/" + userId)
                .then()
                .statusCode(204)
                .log().all();

        System.out.println("✅ User deletion request sent (204 No Content)");

        // Optional in real APIs:
        // UserHelper.assertUserNotFound(requestSpec, userId);
    }
}
