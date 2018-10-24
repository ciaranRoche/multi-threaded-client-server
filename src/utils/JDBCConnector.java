package utils;

import java.sql.*;

import java.util.Properties;


/*
AUTHOR : Ciaran Roche
DATE : 24/OCT/2018

ABOUT : Class JDBCConnector
        Provides methods for:
            creating connection to DB
            returning all records from DB
            returning single record from DB
 */
public class JDBCConnector {


    /** The name of the MySQL account to use (or empty for anonymous) */
    private final String userName = "root";

    /** The password for the MySQL account (or empty for anonymous) */
    private final String password = "root";

    /** The name of the computer running MySQL */
    private final String serverName = "localhost";

    /** The port of the MySQL server (default is 3306) */
    private final int portNumber = 3306;

    /** The name of the database we are testing with (this default is installed with MySQL) */
    private final String dbName = "assignmentDB";


    /*
    Returns a connection to the database
     */
    public Connection getConnection() throws SQLException {
        Connection conn;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        conn = DriverManager.getConnection("jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/" + this.dbName, connectionProps);

        return conn;
    }

    /*
    Returns ResultSet of records from database
     */
    public ResultSet getRecords() throws SQLException{
        Statement stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        String sqlGet = "SELECT * FROM Student";
        stmt.executeQuery(sqlGet);
        ResultSet rs = stmt.getResultSet();
        return rs;
    }

    /*
    Returns ResultSet of found record from database
     */
    public ResultSet returnRecord(String id) throws SQLException{
        Statement stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        String sqlGet = "SELECT * FROM Student WHERE STUD_ID ='" + id + "'";
        stmt.executeQuery(sqlGet);
        ResultSet rs = stmt.getResultSet();
        return rs;
    }
}
