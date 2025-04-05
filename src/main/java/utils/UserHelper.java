package utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.CreateUserResponse;
import payloads.UpdateUserResponse;
import payloads.UserPayload;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserHelper {

    public static void assertUserExists(RequestSpecification requestSpec, int userId) {
        System.out.println("Checking if user with ID " + userId + " exists...");

        given()
                .spec(requestSpec)
                .when()
                .get("/api/users/" + userId)
                .then()
                .statusCode(200)
                .log().all();
    }

    public static CreateUserResponse createUser(RequestSpecification requestSpec, UserPayload payload) {
        System.out.println("Creating user: " + payload.getName());

        Response response = given()
                .spec(requestSpec)
                .body(payload)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .log().all()
                .extract().response();

        return response.as(CreateUserResponse.class);
    }

    public static UpdateUserResponse patchUser(RequestSpecification requestSpec, String userId, Map<String, String> patchPayload) {
        System.out.println("Updating user with PATCH: " + userId);

        Response response = given()
                .spec(requestSpec)
                .body(patchPayload)
                .when()
                .patch("/api/users/" + userId)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        return response.as(UpdateUserResponse.class);
    }

    public static void deleteUser(RequestSpecification requestSpec, String userId) {
        System.out.println("Deleting user ID: " + userId);

        given()
                .spec(requestSpec)
                .when()
                .delete("/api/users/" + userId)
                .then()
                .statusCode(204)
                .log().all();
    }
}
