package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import payloads.CreateUserResponse;
import payloads.UserPayload;
import utils.ExtentTestListener;
import utils.TestDataProvider;

import static com.aventstack.extentreports.Status.INFO;
import static com.aventstack.extentreports.Status.PASS;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateUserTest extends BaseTest {

    @Test
    public void createUser() {
        System.out.println("Creating a new user using POJO payload");

        UserPayload user = new UserPayload("morpheus", "leader");

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"))
                .body("id", notNullValue())
                .log().all();
    }

    @Test(dataProvider = "userData", dataProviderClass = TestDataProvider.class)
    public void createUserWithMultipleData(String name, String job) {
        ExtentTestListener.extentTest.get().log(INFO, "Creating user: " + name);

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

        CreateUserResponse res = response.as(CreateUserResponse.class);

        Assert.assertEquals(res.getName(), name);
        Assert.assertEquals(res.getJob(), job);
        Assert.assertNotNull(res.getId());

        ExtentTestListener.extentTest.get().log(PASS, "User created successfully with ID: " + res.getId());
    }
}
