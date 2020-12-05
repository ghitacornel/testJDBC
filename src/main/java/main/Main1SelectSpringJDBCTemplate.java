package main;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import main.model.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

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
public class Main1SelectSpringJDBCTemplate {

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

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        HikariDataSource dataSource = new HikariDataSource(config);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // SQL string to execute
        String sql = "SELECT id, name FROM person";

        // expected data
        List<Person> people = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Person.class));

        // verify result by checking database table data and console output
        System.out.println(people);
    }

}
