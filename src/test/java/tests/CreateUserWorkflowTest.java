package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import payloads.CreateUserResponse;
import payloads.UserPayload;

import static io.restassured.RestAssured.given;

public class CreateUserWorkflowTest extends BaseTest {

    @Test
    public void createUserAndVerify() {
        String name = "morpheus";
        String job = "zion leader";

        System.out.println("Step 1: Attempting to fetch user before creation (expected 404)");

        // Optional: Simulate checking if user exists first (fake ID)
        given()
                .spec(requestSpec)
                .when()
                .get("/api/users/9999")
                .then()
                .statusCode(404)
                .log().all();

        System.out.println("Step 2: Creating user via POST");

        UserPayload user = new UserPayload(name, job);

        Response response = given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .log().all()
                .extract().response();

        // Deserialize response to POJO
        CreateUserResponse resData = response.as(CreateUserResponse.class);

        System.out.println("Step 3: Validating response data");

        Assert.assertEquals(resData.getName(), name);
        Assert.assertEquals(resData.getJob(), job);
        Assert.assertNotNull(resData.getId());
        Assert.assertNotNull(resData.getCreatedAt());

        System.out.println("✅ User created with ID: " + resData.getId());

        // Step 4: Try fetching user by ID (WON'T work with Reqres, but works in real APIs)
        System.out.println("Step 4: (Simulated) GET created user by ID");

        given()
                .spec(requestSpec)
                .when()
                .get("/api/users/" + resData.getId()) // Reqres won't return it
                .then()
                .statusCode(404) // Change to 200 in real API
                .log().all();
    }
}
