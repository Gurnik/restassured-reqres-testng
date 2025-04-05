package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import payloads.CreateUserResponse;
import payloads.UpdateUserResponse;
import payloads.UserPayload;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserLifecycleTest extends BaseTest {

    @Test
    public void fullUserLifecycle() {
        // Step 1: Create user
        System.out.println("🔁 Step 1: Creating user");

        UserPayload newUser = new UserPayload("morpheus", "leader");

        Response postResponse = given()
                .spec(requestSpec)
                .body(newUser)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .log().all()
                .extract().response();

        CreateUserResponse createdUser = postResponse.as(CreateUserResponse.class);
        String userId = createdUser.getId();

        Assert.assertNotNull(userId);
        System.out.println("✅ Created user with ID: " + userId);

        // Step 2: Patch user
        System.out.println("🛠️ Step 2: Updating job title with PATCH");

        Map<String, String> patchPayload = new HashMap<>();
        patchPayload.put("job", "zion general");

        Response patchResponse = given()
                .spec(requestSpec)
                .body(patchPayload)
                .when()
                .patch("/api/users/" + userId)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        UpdateUserResponse updatedUser = patchResponse.as(UpdateUserResponse.class);

        Assert.assertEquals(updatedUser.getJob(), "zion general");
        Assert.assertNotNull(updatedUser.getUpdatedAt());

        System.out.println("✅ User job updated: " + updatedUser.getUpdatedAt());

        // Step 3: Delete user
        System.out.println("🗑️ Step 3: Deleting user");

        given()
                .spec(requestSpec)
                .when()
                .delete("/api/users/" + userId)
                .then()
                .statusCode(204)
                .log().all();

        System.out.println("✅ User deletion successful");
    }
}
