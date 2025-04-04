package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import payloads.UpdateUserResponse;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PatchUserTest extends BaseTest {

    @Test
    public void patchUserJobTitleOnly() {
        System.out.println("Step 1: Checking user existence");

        given()
                .spec(requestSpec)
                .when()
                .get("/api/users/2")
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Step 2: Partially updating user with PATCH");

        // We only update the 'job' field
        Map<String, String> updateFields = new HashMap<>();
        updateFields.put("job", "zion general");

        Response response = given()
                .spec(requestSpec)
                .body(updateFields)
                .when()
                .patch("/api/users/2")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        UpdateUserResponse res = response.as(UpdateUserResponse.class);

        Assert.assertEquals(res.getJob(), "zion general");
        Assert.assertNotNull(res.getUpdatedAt());

        System.out.println("✅ User job title updated: " + res.getUpdatedAt());
    }
}
