package transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class TestTransactional {

    Connection connection;

    Connection createConnection() throws SQLException {
        String machine = "localhost";// machine ip or localhost if the database is locally installed
        String port = "3306";
        String databaseName = "mydatabase";// or schema name
        String url = "jdbc:mysql://" + machine + ":" + port + "/" + databaseName;
        String username = "cornel";
        String password = "sefusefu";
        return DriverManager.getConnection(url, username, password);
    }

    @BeforeEach
    public void setUp() throws SQLException {
        connection = createConnection();
        connection.createStatement().execute("drop table if exists person_transactional");
        connection.createStatement().execute("create table if not exists person_transactional(id int primary key, name varchar(20))");
        connection.createStatement().execute("insert into person_transactional(id,name) values (1,'existing')");
    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.createStatement().execute("drop table if exists person_transactional");
        connection.close();
    }

    @Test
    public void testTransactionalPartialRollback() throws SQLException {

        String sql1 = "insert into person_transactional(id,name) values (2,'new value')";
        String sql2 = "insert into person_transactional(id,name) values (1,'try to write over existing id')";

        try {

            // we control manually the transaction
            connection.setAutoCommit(false);

            // create SQL statement
            Statement statement = connection.createStatement();

            // execute SQL statement and obtain the result
            statement.executeUpdate(sql1);

            // check this line
            connection.commit(); // add this and the first query is persisted

            // try to execute an insert query that will raise a database error
            statement.executeUpdate(sql2);

            // optional
            statement.close();

        } catch (Exception e) {

            connection.rollback();

            // observe manually that the illegal insert didn't roll back the already committed previously executed statement
            ResultSet resultSet = connection.createStatement().executeQuery("select id, name from person_transactional");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
            }

            Assertions.assertEquals("Data truncation: Data too long for column 'name' at row 1", e.getMessage());
            return;

        }

        Assertions.fail("expect exception");
    }


    @Test
    public void testTransactionalFullRollback() throws SQLException {

        String sql1 = "insert into person_transactional(id,name) values (2,'new value')";
        String sql2 = "insert into person_transactional(id,name) values (1,'try to write over existing id')";

        try {

            // we control manually the transaction
            connection.setAutoCommit(false);

            // create SQL statement
            Statement statement = connection.createStatement();

            // execute SQL statement and obtain the result
            statement.executeUpdate(sql1);

            // try to execute an insert query that will raise a database error
            statement.executeUpdate(sql2);

            // optional
            statement.close();

        } catch (Exception e) {

            connection.rollback();

            // observe manually that the illegal insert didn't roll back the already committed previously executed statement
            ResultSet resultSet = connection.createStatement().executeQuery("select id, name from person_transactional");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
            }

            Assertions.assertEquals("Data truncation: Data too long for column 'name' at row 1", e.getMessage());
            return;

        }

        Assertions.fail("expect exception");
    }
}
