package datasource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDataSourceConnectionFailure {

    BasicDataSource dataSource;

    BasicDataSource createDataSource() {
        String machine = "localhost";// machine ip or localhost if the database is locally installed
        String port = "3306";
        String databaseName = "mydatabase";// or schema name
        String url = "jdbc:mysql://" + machine + ":" + port + "/" + databaseName;
        String username = "cornel";
        String password = "sefusefu";

//        // create data source
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setJdbcUrl(url);
//        hikariConfig.setUsername(username);
//        hikariConfig.setPassword(password);
//        HikariDataSource dataSource = new HikariDataSource(hikariConfig);// specific external data source
//        {// configure data source
//            hikariConfig.setJdbcUrl(url);
//            hikariConfig.setUsername(username);
//            hikariConfig.setPassword(password);
//            hikariConfig.setMinimumIdle(5);
//            hikariConfig.setValidationTimeout(100);
//        }
//        return dataSource;

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMinIdle(5);
        dataSource.setMaxTotal(10);
//        dataSource.setValidationQuery("select 1");
//        dataSource.setValidationQueryTimeout(1);
        return dataSource;
    }

    @Before
    public void setUp() throws SQLException {
        dataSource = createDataSource();
        Connection connection = dataSource.getConnection();
        connection.createStatement().execute("drop table if exists person_fail");
        connection.createStatement().execute("create table if not exists person_fail(id int primary key, name varchar(20))");
        connection.createStatement().execute("insert into person_fail(id,name) values (1,'one')");
        connection.close();
    }

    @After
    public void tearDown() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.createStatement().execute("drop table if exists person_fail");
        connection.close();
    }

    @Test
    public void testTransactionalPartialRollback() throws Exception {

        while (true) {
            Connection connection = dataSource.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("select id, name from person_fail");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
            }
            resultSet.close();
            connection.close();
            Thread.sleep(10000);
        }

    }

}
