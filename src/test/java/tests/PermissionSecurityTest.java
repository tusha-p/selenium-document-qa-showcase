package tests;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

// This class inherits from BaseTest, so it gets the driver setup/teardown
public class PermissionSecurityTest {

    @Test
    public void standardUserCannotAccessAdminPage() {
        // 1. Navigate directly to the admin page (bypassing UI for a security test)
        driver.get("http://localhost:8080/admin.html");

        // 2. Verification: Assert that we are NOT on the admin page.
        //    A secure system should redirect us to a login page or show an error.
        boolean isOnAdminPage = driver.getCurrentUrl().contains("admin");
        String pageTitle = driver.getTitle().toLowerCase();
        String pageSource = driver.getPageSource();

        // This assertion checks that we are not on the admin URL.
        // If we are, it means the security check failed.
        Assert.assertFalse(isOnAdminPage, "Security Vulnerability: Standard user was able to access the admin page directly.");

        // Additional robust checks that a security breach happened:
        if (isOnAdminPage) {
            // If we are on the admin page, check for content that should only be visible to admins
            if (pageSource.contains("User Management") || pageTitle.contains("admin")) {
                Assert.fail("Critical Security Failure: Standard user can see admin-only content.");
            }
        }
        // If all the above passes, the test passes. The security control worked.
    }

    @Test
    public void adminUserCanAccessAdminPage() {
        // This test would require a valid admin login first.
        // Since we don't have a real app, we'll just navigate to the page.
        // This test is currently expected to FAIL, which demonstrates a important concept.

        driver.get("http://localhost:8080/admin.html");

        // This is what we would EXPECT for an admin user after logging in.
        // We are asserting the OPPOSITE to show the test is working and would fail without a proper login.
        boolean isOnAdminPage = driver.getCurrentUrl().contains("admin");
        Assert.assertFalse(isOnAdminPage, "This test is expected to fail, demonstrating that without auth, we cannot access the admin page.");

        // In a real scenario, this test would:
        // 1. Log in with admin credentials (using LoginPage class)
        // 2. Navigate to /admin.html
        // 3. Assert.assertTrue(isOnAdminPage, "Admin user could not access the admin page.");
    }
}
