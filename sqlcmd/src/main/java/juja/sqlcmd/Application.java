package juja.sqlcmd;

import java.sql.*;

public class Application {

    private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
    private static final String DB_CONNECTION_PATH = "jdbc:postgresql://127.0.0.1:5432/sqlcmd";
    private static final String DB_USER = "sqlcmd";
    private static final String DB_PASSWORD = "sqlcmd";
    private static final String TABLE_NAME = "user";
    private Connection connection;


    public static void main(String[] arg) throws SQLException {
        new Application().simpleSQL();
    }

    public void simpleSQL() throws SQLException {
        checkJDBCDriver();
        createDbConnection();
        dropTableWithName(TABLE_NAME);
        showExistingTables();
        createTable(TABLE_NAME);
        connection.close();
    }

    private void dropTableWithName(String user) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE IF EXISTS \"" + user + "\" CASCADE ;";
            statement.executeUpdate(sql);
        }
    }

    private void createTable(String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE public.\"" + tableName + "\"" + "\n"
                    + "(" + "\n"
                    + "id SERIAL PRIMARY KEY ," + "\n"
                    + "name TEXT  NOT NULL," + "\n"
                    + "password TEXT  NOT NULL" + "\n"
                    + ")";
            statement.executeUpdate(sql);
            showExistingTables();
        }
    }

    private void showExistingTables() {
        try (Statement statement = connection.createStatement()) {
            String quary = "SELECT" + "\n"
                    + "*" + "\n"
                    + "FROM" + "\n"
                    + "pg_catalog.pg_tables" + "\n"
                    + "WHERE" + "\n"
                    + "schemaname != 'pg_catalog'" + "\n"
                    + "AND schemaname != 'information_schema';";
            ResultSet rs = statement.executeQuery(quary);
            System.out.println("Existing Tables:");
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    System.out.println("-" + rs.getString("tablename"));
                }
            } else {
                System.out.println("db is empty" + "\n");
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
