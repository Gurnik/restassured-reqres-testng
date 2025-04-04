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
        System.out.println("Step 1: Verify user exists");

        given()
                .spec(requestSpec)
                .when()
                .get("/api/users/2")
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Step 2: Updating user with PUT");

        String name = "neo";
        String job = "the one";

        UserPayload userUpdate = new UserPayload(name, job);

        Response response = given()
                .spec(requestSpec)
                .body(userUpdate)
                .when()
                .put("/api/users/2")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        UpdateUserResponse resData = response.as(UpdateUserResponse.class);

        Assert.assertEquals(resData.getName(), name);
        Assert.assertEquals(resData.getJob(), job);
        Assert.assertNotNull(resData.getUpdatedAt());

        System.out.println("✅ User updated at: " + resData.getUpdatedAt());
    }
}
