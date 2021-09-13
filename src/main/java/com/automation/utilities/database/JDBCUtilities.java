package com.automation.utilities.database;

import java.sql.*;

public class JDBCUtilities {

    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    ResultSet testResultSet = null;

    String dbURL = "jdbc:mysql://localhost:3306/automationdb";
    String dbuser = "root";
    String password = "root";

    protected Statement getStatement() throws SQLException {
        con = DriverManager.getConnection(dbURL,dbuser,password);
        return con.createStatement();
    }

    public ResultSet getResultSet(String query) throws SQLException {
        rs = getStatement().executeQuery(query);
        return rs;
    }


}
