package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import payloads.CreateUserResponse;
import payloads.UpdateUserResponse;
import payloads.UserPayload;
import utils.UserHelper;

import java.util.HashMap;
import java.util.Map;

public class UserLifecycleTest extends BaseTest {

    @Test
    public void fullUserLifecycle() {
        // Step 1: Create user
        UserPayload newUser = new UserPayload("morpheus", "leader");
        CreateUserResponse createdUser = UserHelper.createUser(requestSpec, newUser);

        String userId = createdUser.getId();
        Assert.assertNotNull(userId);
        System.out.println("✅ Created user with ID: " + userId);

        // Step 2: Patch user
        Map<String, String> patchPayload = new HashMap<>();
        patchPayload.put("job", "zion general");

        UpdateUserResponse updatedUser = UserHelper.patchUser(requestSpec, userId, patchPayload);

        Assert.assertEquals(updatedUser.getJob(), "zion general");
        Assert.assertNotNull(updatedUser.getUpdatedAt());
        System.out.println("✅ Job updated at: " + updatedUser.getUpdatedAt());

        // Step 3: Delete user
        UserHelper.deleteUser(requestSpec, userId);
        System.out.println("✅ User deleted");
    }
}
