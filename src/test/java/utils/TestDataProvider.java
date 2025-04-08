package utils;

import org.testng.annotations.DataProvider;

public class TestDataProvider {
    @DataProvider(name = "userData")
    public Object[][] getUserData() {
        return new Object[][] {
                {"morpheus", "leader"},
                {"neo", "the one"},
                {"trinity", "pilot"},
        };
    }
}
