package main;

import main.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * prepared statement usage<br>
 * SQL injection<br>
 * query optimisation<br>
 */
public class Main3SelectWithPreparedStatement {

    public static void main(String[] args) {

        // ensure database state by executing these queries
//        create table person(id int primary key, name varchar(20));
//        insert into person(id,name) values (1,'ion');
//        insert into person(id,name) values (2,'gheorghe');
//        insert into person(id,name) values (3,'vasile');
//        select * from person;

        // credentials and connectivity configuration
        String machine = "localhost";// machine ip or localhost if the database is locally installed
        String port = "3306";
        String databaseName = "mydatabase";// or schema name
        String url = "jdbc:mysql://" + machine + ":" + port + "/" + databaseName;
        String username = "cornel";
        String password = "sefusefu";

        // SQL string to execute
        int parameter = 2;// this is a parameter
        String sql = "SELECT id, name FROM person WHERE id = ?";

        // expected data
        List<Person> people = new ArrayList<>();

        // connection to use
        Connection connection = null;

        try {

            // create database connection
            connection = DriverManager.getConnection(url, username, password);

            // create SQL statement
            PreparedStatement statement = connection.prepareStatement(sql);

            // set value for each parameter
            statement.setInt(1, parameter);

            // execute SQL statement and obtain the result
            ResultSet resultSet = statement.executeQuery();

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

        // verify result by checking console output
        System.out.println(people);
    }

}
