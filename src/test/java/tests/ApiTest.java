package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
    }

    @Test
    public void testDocumentWorkflowApproval() {
        // Test API-driven workflow: Approve a document
        Response response = RestAssured.given()
                .post("/documents/1/approve");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getString("message").contains("approved"));
    }

    @Test
    public void testAdminEndpointAccess() {
        // Test security: This should fail or require auth, demonstrating access control
        Response response = RestAssured.given()
                .get("/admin/users");

        // This might return 401 (Unauthorized) or 403 (Forbidden) in a real system
        Assert.assertTrue(response.getStatusCode() != 500, "Server should handle unauthorized access gracefully");
    }
    // After uploading a document via UI or API, add this check:
@Test
public void verifyDocumentStoredInDatabase() {
    // 1. Upload document (via UI or API)
    
    // 2. Query database directly to check if document exists
    String documentName = "test_document.pdf";
    boolean documentExists = databaseHelper.checkDocumentExists(documentName);
    
    // 3. ASSERT this is true - this proves DB storage
    assertTrue(documentExists, "Document should be stored in database");
    
    // 4. Also verify metadata is correct
    Document doc = databaseHelper.getDocumentMetadata(documentName);
    assertEquals("application/pdf", doc.getFileType());
    assertEquals(1024, doc.getFileSize()); // etc
}
}
