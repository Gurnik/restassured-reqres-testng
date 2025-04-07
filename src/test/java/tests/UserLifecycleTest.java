package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import payloads.CreateUserResponse;
import payloads.UpdateUserResponse;
import payloads.UserPayload;
import utils.ExtentTestListener;
import utils.UserHelper;

import java.util.HashMap;
import java.util.Map;

@Listeners(ExtentTestListener.class)
public class UserLifecycleTest extends BaseTest {

    @Test
    public void fullUserLifecycle() {
        ExtentTestListener.extentTest.get().log(Status.INFO, "Creating user");

        UserPayload newUser = new UserPayload("morpheus", "leader");
        CreateUserResponse createdUser = UserHelper.createUser(requestSpec, newUser);

        String userId = createdUser.getId();
        Assert.assertNotNull(userId);
        ExtentTestListener.extentTest.get().pass("User created with ID: " + userId);

        Map<String, String> patchPayload = new HashMap<>();
        patchPayload.put("job", "zion general");

        ExtentTestListener.extentTest.get().log(Status.INFO, "Updating user with PATCH");

        UpdateUserResponse updatedUser = UserHelper.patchUser(requestSpec, userId, patchPayload);
        ExtentTestListener.extentTest.get().pass("Job updated to: " + updatedUser.getJob());

        ExtentTestListener.extentTest.get().log(Status.INFO, "Deleting user");
        UserHelper.deleteUser(requestSpec, userId);
        ExtentTestListener.extentTest.get().pass("User deleted successfully");
    }
}
