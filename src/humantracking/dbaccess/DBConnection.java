package humantracking.dbaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
 
    private static Connection connection;
    
    private DBConnection(){
        
    }

    public synchronized static Connection getConnectionToDB() throws SQLException, ClassNotFoundException {
        
        if (connection == null) {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/survellience_system","root", "");
        }
        return connection;
    }
}
