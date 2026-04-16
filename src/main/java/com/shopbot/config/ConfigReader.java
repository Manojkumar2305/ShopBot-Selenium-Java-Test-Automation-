package com.shopbot.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();
    private static ConfigReader instance;

    private ConfigReader() {
        try {
            String path = System.getProperty("user.dir") + "/src/test/resources/config.properties";
            FileInputStream fis = new FileInputStream(path);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config.properties: " + e.getMessage());
        }
    }

    public static ConfigReader getInstance() {
        if (instance == null) instance = new ConfigReader();
        return instance;
    }

    public String getBrowser()         { return properties.getProperty("browser", "chrome").trim(); }
    public String getBaseUrl()         { return properties.getProperty("base.url").trim(); }
    public int    getTimeout()         { return Integer.parseInt(properties.getProperty("timeout", "15").trim()); }
    public boolean isHeadless()        { return Boolean.parseBoolean(properties.getProperty("headless", "false").trim()); }
    public String getScreenshotsPath() { return properties.getProperty("screenshots.path", "screenshots/").trim(); }
    public String getReportPath()      { return properties.getProperty("report.path", "test-output/ShopBotReport.html").trim(); }
}
