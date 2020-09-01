package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * transaction control
 */
public class Main7InsertTransactional {

    public static void main(String[] args) {

        // ensure database state by executing these queries
//        create table person(id int primary key, name varchar(20));
//        insert into person(id,name) values (1,'ion');
//        insert into person(id,name) values (2,'gheorghe');
//        select * from person;

        // credentials and connectivity configuration
        String machine = "localhost";// machine ip or localhost if the database is locally installed
        String port = "3306";
        String databaseName = "mydatabase";// or schema name
        String url = "jdbc:mysql://" + machine + ":" + port + "/" + databaseName;
        String username = "cornel";
        String password = "sefusefu";

        // SQL string to execute
        String sql1 = "insert into person(id,name) values (13,'dumitru')";
        String sql2 = "insert into person(id,name) values (1,'existing id')";

        // connection to use
        Connection connection = null;

        try {

            // create database connection
            connection = DriverManager.getConnection(url, username, password);

            // we control manually the transaction
            connection.setAutoCommit(false);

            // create SQL statement
            Statement statement = connection.createStatement();

            // execute SQL statement and obtain the result
            statement.executeUpdate(sql1);

            // TODO check this line
            connection.commit(); // add this and the first query is persisted

            statement.executeUpdate(sql2);

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

        // ensure expected result is obtained by validating the database state
    }

}
