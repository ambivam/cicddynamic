package tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcPro {
    //jdbc:mysql://localhost:3306/sales
    public static void main(String[] args) {
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/automationdb","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select usid,tcid, concat(usid,\"_\",tcid) as testcases from predictions limit 10");
            while(rs.next()){
                System.out.println(rs.getString("testcases"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
