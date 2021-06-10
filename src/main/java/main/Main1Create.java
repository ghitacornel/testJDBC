package main;

import java.sql.*;

/**
 * simple SQL DDL example
 */
public class Main1Create {

    public static void main(String[] args) {

        // credentials and connectivity configuration
        String machine = "localhost";// machine ip or localhost if the database is locally installed
        String port = "3306";
        String databaseName = "mydatabase";// or schema name
        String url = "jdbc:mysql://" + machine + ":" + port + "/" + databaseName;
        String username = "cornel";
        String password = "sefusefu";

        // SQL string to execute
        String sql = "create table person(id int primary key, name varchar(20))";

        // connection to use
        Connection connection = null;

        try {

            // create database connection
            connection = DriverManager.getConnection(url, username, password);

            // create SQL statement
            Statement statement = connection.createStatement();

            // execute SQL statement and obtain the result
            statement.execute(sql);

            // optional
            statement.close();

        } catch (SQLException e) {

            // process or not
            throw new RuntimeException(e);

        } finally {

            // always close the connection
            try {

                // safety check always
                if (connection != null) {
                    connection.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // verify result by checking database tables
        // run twice this method and check exception is raised when running the second time if the table already exists

    }

}
