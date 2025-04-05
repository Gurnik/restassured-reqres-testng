package utils;

import io.restassured.specification.RequestSpecification;

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

    public static void assertUserNotFound(RequestSpecification requestSpec, int userId) {
        System.out.println("Verifying that user with ID " + userId + " does not exist...");

        given()
                .spec(requestSpec)
                .when()
                .get("/api/users/" + userId)
                .then()
                .statusCode(404)
                .log().all();
    }
}
