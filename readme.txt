JDBC = java DataBase Connectivity
JDBC = API

JDBC API = [ 1 API part of JDK see package "java.sql" ] + [ 1 API part JDBC driver provided by database developer ]

usage :
DriverManager => get Connection
Connection => create Statement
Statement => execute native SQL => get ResultSet in case of expected data
ResultSet => extract data row by row / column by column

observe :
- try / catch / finally usage pattern
- interface vs implementation pattern
- native SQL usage
- can use all kind of SQL queries ( DDL / DML / TCl / others )
- programmer is responsible with writing proper SQL native query
- programmer is responsible mapping of database table row to java objects
- in case of database structure changes queries can become invalid and the programmer must adjust them manually

TOPIC = Statement vs PreparedStatement vs CallableStatement

PreparedStatement => avoid SQL injection
PreparedStatement => SQL query optimisation on the database side
CallableStatement => database stored procedures invocation

TOPIC = DataSource vs DriverManager

DriverManager used design patterns => factory + facade
DataSource used design patterns => object pool + proxy + factory + facade

TOPIC = Batches

used for database calls optimisation
send to execution more than 1 query to the database
