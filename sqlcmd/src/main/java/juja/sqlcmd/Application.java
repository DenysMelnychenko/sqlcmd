package juja.sqlcmd;

import java.sql.*;

public class Application {

    private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
    private static final String DB_CONNECTION_PATH = "jdbc:postgresql://127.0.0.1:5432/sqlcmd";
    private static final String DB_USER = "sqlcmd";
    private static final String DB_PASSWORD = "sqlcmd";
    private Connection connection;


    public static void main(String[] arg) throws SQLException {
        new Application().simpleSQL();
    }

    public void simpleSQL() throws SQLException {
        checkJDBCDriver();
        createDbConnection();
        showExistingTables();

        connection.close();
    }

    private void showExistingTables() {
        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables("sqlcmd", "public", "%", null);
            System.out.println("Existing Tables:");
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    System.out.println("-" + rs.getString(3));
                }
            } else {
                System.out.println("db is empty");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void createDbConnection() {
        try {
            connection = DriverManager.getConnection(DB_CONNECTION_PATH, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        if (connection != null) {
            System.out.println("Connection created" + "\n");
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
