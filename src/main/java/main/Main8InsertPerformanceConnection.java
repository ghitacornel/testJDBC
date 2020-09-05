package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 * insert 10000 records<br>
 * use different strategies for managing database connection
 */

// first attempt => see method "main1"
// create the database connection once before all inserts
// reuse the database connection for every insert
// close the database connection at the end of all the inserts
// results :
//start program at Sat Sep 05 09:15:25 EEST 2020
//end program at Sat Sep 05 09:16:08 EEST 2020

// first attempt => see method "main2"
// create the database connection for every insert
// close the database connection at the end each insert
// results :
//start program at Sat Sep 05 09:21:58 EEST 2020
//end program at Sat Sep 05 09:24:57 EEST 2020
public class Main8InsertPerformanceConnection {

    public static void main1(String[] args) {

        System.out.println("start program at " + new Date());

        // ensure table person_performance is created and empty
        // create table person_performance(id int primary key, name varchar(20));

        // credentials and connectivity configuration
        String machine = "localhost";// machine ip or localhost if the database is locally installed
        String port = "3306";
        String databaseName = "mydatabase";// or schema name
        String url = "jdbc:mysql://" + machine + ":" + port + "/" + databaseName;
        String username = "cornel";
        String password = "sefusefu";

        // SQL string to execute
        String sql = "INSERT INTO person_performance(id,name) values (?,?);";

        // connection to use
        Connection connection = null;

        try {

            // create database connection
            connection = DriverManager.getConnection(url, username, password);

            for (int i = 1; i <= 10000; i++) {

                // create SQL statement
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, "nume persoana " + i);
                preparedStatement.execute();
                preparedStatement.close();

            }

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

        System.out.println("end program at " + new Date());

    }

    public static void main2(String[] args) {

        System.out.println("start program at " + new Date());

        for (int i = 1; i <= 10000; i++) {

            // ensure table person_performance is created and empty
            // create table person_performance(id int primary key, name varchar(20));

            // credentials and connectivity configuration
            String machine = "localhost";// machine ip or localhost if the database is locally installed
            String port = "3306";
            String databaseName = "mydatabase";// or schema name
            String url = "jdbc:mysql://" + machine + ":" + port + "/" + databaseName;
            String username = "cornel";
            String password = "sefusefu";

            // SQL string to execute
            String sql = "INSERT INTO person_performance(id,name) values (?,?);";

            // connection to use
            Connection connection = null;

            try {

                // create database connection
                connection = DriverManager.getConnection(url, username, password);

                // create SQL statement
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, "nume persoana " + i);
                preparedStatement.execute();
                preparedStatement.close();

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

        }// end for loop

        System.out.println("end program at " + new Date());

    }

}
