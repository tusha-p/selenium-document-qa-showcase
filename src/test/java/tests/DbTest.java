package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.*;

public class DbTest {

    @Test
    public void testDocumentStatusInDatabase() throws SQLException {
        // This would connect to a real test database in a real project
        // String url = "jdbc:mysql://localhost/test";
        // Connection conn = DriverManager.getConnection(url, "user", "pass");

        // For demo: Using H2 in-memory DB to simulate the concept
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE documents (id INT, title VARCHAR(255), status VARCHAR(50))");
        stmt.execute("INSERT INTO documents VALUES (1, 'Test Doc', 'APPROVED')");

        ResultSet rs = stmt.executeQuery("SELECT status FROM documents WHERE id = 1");
        rs.next();
        String status = rs.getString("status");

        Assert.assertEquals(status, "APPROVED", "Document status should be APPROVED in the database");

        conn.close();
    }
}
