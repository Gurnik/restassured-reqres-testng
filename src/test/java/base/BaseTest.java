package base;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BaseTest {

    protected RequestSpecification requestSpec;
    protected ResponseSpecification responseSpec;

    @BeforeClass
    public void setUpBase() {
        RestAssured.baseURI = "https://reqres.in";

        requestSpec = given()
                .log().all()  // ✅ Still log full request details
                .header("Content-Type", "application/json");

        responseSpec = RestAssured.expect()
                .statusCode(200)  // ✅ Expect a successful response
                .contentType("application/json"); // ✅ Ensure JSON is returned
        // ❌ Removed log().all() from here
    }
}
