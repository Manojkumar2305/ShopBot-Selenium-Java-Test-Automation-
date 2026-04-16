package com.shopbot.utils;

import com.shopbot.config.ConfigReader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    private ScreenshotUtil() {}

    public static String capture(String testName) {
        String ts       = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String folder   = ConfigReader.getInstance().getScreenshotsPath();
        String filePath = folder + testName + "_" + ts + ".png";
        try {
            Files.createDirectories(Paths.get(folder));
            File src = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
            Files.copy(src.toPath(), Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("Screenshot failed: " + e.getMessage());
        }
        return Paths.get(filePath).toAbsolutePath().toString();
    }
}
