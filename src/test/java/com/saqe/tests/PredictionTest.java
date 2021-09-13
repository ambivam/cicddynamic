package com.saqe.tests;

import com.automation.utilities.database.JDBCUtilities;

import java.sql.ResultSet;

import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PredictionTest {

    JDBCUtilities util = null;
    ResultSet rs = null;
    ResultSet testResultSet = null;
    String tagline = null;
    String suitename = null;
    TestNG myTestNG = null;
    XmlSuite mySuite = null;
    XmlTest myTest = null;
    List<XmlClass> myClasses = null;
    String testcasequery = null;

    @Test
    public void testPredictions() throws SQLException {
        //Retrieving different taglines from predictions table in automationdb database
        String taglinequery = "select distinct tagline from predictions limit 5";

        util = new JDBCUtilities();
        rs = util.getResultSet(taglinequery);

        while(rs.next()){
            //Assigning suitename with tagline
            tagline = rs.getString("tagline");
            suitename = rs.getString("tagline").trim().replaceAll("\\s","");
            myTestNG = new TestNG();
            mySuite = new XmlSuite();
            mySuite.setName(suitename);
            mySuite.setParallel(XmlSuite.ParallelMode.METHODS);

            //Creating TestModules
            myTest = new XmlTest(mySuite);
            myTest.setName(suitename);

            //Create a list which can contain the classes that you want to run.
            myClasses = new ArrayList<XmlClass>();
            testcasequery = "select usid,tcid,concat(usid,\"_\",tcid) as testcases from predictions where tagline in ('"+tagline+"')";
            System.out.println(testcasequery);
            testResultSet = util.getResultSet(testcasequery);
            String temp = null;

            while(testResultSet.next()) {

                temp = "tests."+testResultSet.getString(3);
                try{
                    myClasses.add(new XmlClass(temp));
                    System.out.println("Added Successfully ha ha"+ temp + "  suitename is  "+suitename);
                }catch(Exception e){
                    System.out.println("Unable to find class "+temp);
                }
            }
        }

        //Assign that to the XmlTest Object created earlier.
        myTest.setXmlClasses(myClasses);

        //Create a list of XmlTests and add the Xmltest you created earlier to it.
        List<XmlTest> myTests = new ArrayList<XmlTest>();
        myTests.add(myTest);

        //add the list of tests to your Suite.
        mySuite.setTests(myTests);

        //Add the suite to the list of suites.
        List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
        mySuites.add(mySuite);

        //Set the list of Suites to the testNG object you created earlier.
        myTestNG.setXmlSuites(mySuites);
        mySuite.setFileName(mySuite+".xml");
        mySuite.setThreadCount(3);
        myTestNG.run();

    }


}
