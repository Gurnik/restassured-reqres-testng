package tests;

import base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateUserTest extends BaseTest {

    @Test
    public void createUser() {
        System.out.println("Creating a new user: morpheus");

        String requestBody = """
            {
                "name": "morpheus",
                "job": "leader"
            }
            """;

        given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"))
                .body("id", notNullValue())
                .log().all();
    }
}
