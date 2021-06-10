package main;

import main.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * simple SQL SELECT<br>
 * connection configuration<br>
 * connection creation<br>
 * try / catch / finally pattern<br>
 * interface vs implementation usage<br>
 * DriverManager => Connection => Statement => ResultSet<br>
 * always native SQL<br>
 * no java objects database row automatic mapping
 */
public class Main3Select {

    public static void main(String[] args) {

        // ensure database state by executing these queries
        // drop table person;
        // create table person(id int primary key, name varchar(20));
        // insert into person(id,name) values (1,'ion');
        // insert into person(id,name) values (2,'gheorghe');
        // insert into person(id,name) values (3,'vasile');
        // select * from person;

        // credentials and connectivity configuration
        String machine = "localhost";// machine ip or localhost if the database is locally installed
        String port = "3306";
        String databaseName = "mydatabase";// or schema name
        String url = "jdbc:mysql://" + machine + ":" + port + "/" + databaseName;
        String username = "cornel";
        String password = "sefusefu";

        // SQL string to execute
        String sql = "SELECT id, name FROM person";

        // expected data
        List<Person> people = new ArrayList<>();

        // connection to use
        Connection connection = null;

        try {

            // create database connection
            connection = DriverManager.getConnection(url, username, password);

            // create SQL statement
            Statement statement = connection.createStatement();

            // execute SQL statement and obtain the result
            ResultSet resultSet = statement.executeQuery(sql);

            // loop through the result set
            while (resultSet.next()) {

                // get data and process it row by row
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                // build object
                Person person = new Person(id, name);

                // and add it to result
                people.add(person);

            }

            // optional
            resultSet.close();

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

        // verify result by checking database table data and console output
        System.out.println(people);
    }

}
