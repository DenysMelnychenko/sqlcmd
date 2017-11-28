package juja.sqlcmd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {

    private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
    private static final String DB_CONNECTION_PATH = "jdbc:postgresql://127.0.0.1:5432/sqlcmd";
    private static final String DB_USER = "sqlcmd";
    private static final String DB_PASSWORD = "sqlcmd";

    public static void main(String[] arg) {
        new Application().simpleSQL();
    }

    public void simpleSQL() {
        checkJDBCDriver();
        getConnection();
    }

    private void getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(DB_CONNECTION_PATH, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

    private void checkJDBCDriver() {
        System.out.println("-------- PostgreSQL "
                + "JDBC Connection  ------------");

        try {
            Class.forName(POSTGRESQL_DRIVER);
        } catch (ClassNotFoundException e) {

            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
        }

        System.out.println("PostgreSQL JDBC Driver Registered!");

    }

}
