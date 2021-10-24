package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * simple SQL insert
 */
public class Main2InsertTransaction {

    public static void main(String[] args) {

        // ensure database state by executing these queries
//        create table person(id int primary key, name varchar(20));

        // credentials and connectivity configuration
        String machine = "localhost";// machine ip or localhost if the database is locally installed
        String port = "3306";
        String databaseName = "mydatabase";// or schema name
        String url = "jdbc:mysql://" + machine + ":" + port + "/" + databaseName;
        String username = "cornel";
        String password = "sefusefu";

        // SQL string to execute
        String sql1 = "insert into person(id,name) values (1,'ion')";
        String sql2 = "insert into person(id,name) values (2,'gheorghe')";
        String sql3 = "insert into person(id,name) values (3,'vasile')";

        // connection to use
        Connection connection = null;

        try {

            // create database connection
            connection = DriverManager.getConnection(url, username, password);

            // begin transaction
            connection.setAutoCommit(false);

            // create SQL statement
            Statement statement = connection.createStatement();

            // execute SQL statement and obtain the result
            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);

            // throw an exception here and observe nothing is saved in the database
            if (true) throw new RuntimeException("dummy exception");

            statement.executeUpdate(sql3);

            // optional
            statement.close();

            // commit transaction
            connection.commit();

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

        // verify result by checking database table data
        // run twice this method and check exception is raised when running the second time if the table row already exists

    }

}
