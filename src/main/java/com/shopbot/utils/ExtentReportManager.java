package com.shopbot.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.shopbot.config.ConfigReader;

public class ExtentReportManager {

    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    private ExtentReportManager() {}

    public static synchronized ExtentReports getExtentReports() {
        if (extentReports == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(ConfigReader.getInstance().getReportPath());
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("ShopBot – Test Report");
            spark.config().setReportName("SauceDemo Automation Suite");
            extentReports = new ExtentReports();
            extentReports.attachReporter(spark);
            extentReports.setSystemInfo("AUT", "saucedemo.com");
            extentReports.setSystemInfo("Framework", "Selenium + TestNG + POM");
        }
        return extentReports;
    }

    public static ExtentTest getTest()             { return testThreadLocal.get(); }
    public static void setTest(ExtentTest test)    { testThreadLocal.set(test); }
    public static void flush()                     { if (extentReports != null) extentReports.flush(); }
}
