package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import payloads.UpdateUserResponse;
import payloads.UserPayload;

import static io.restassured.RestAssured.given;

public class UpdateUserTest extends BaseTest {

    @Test
    public void updateUserWithPut() {
        System.out.println("Step 1: Preparing PUT request payload");

        UserPayload updatedUser = new UserPayload("neo", "the one");

        System.out.println("Step 2: Sending PUT request to update user ID = 2");

        Response response = given()
                .spec(requestSpec)
                .body(updatedUser)
                .when()
                .put("/api/users/2")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        UpdateUserResponse updatedResponse = response.as(UpdateUserResponse.class);

        System.out.println("Step 3: Validating updated user data");

        Assert.assertEquals(updatedResponse.getName(), "neo");
        Assert.assertEquals(updatedResponse.getJob(), "the one");
        Assert.assertNotNull(updatedResponse.getUpdatedAt());
    }
}
