package util;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenCaptureUtil {

    public static void takeScreenshot() {
    }

    public static void  takeScreenshot(WebDriver driver, String filePath, String fileName, String extension) throws IOException {
    TakesScreenshot ts=(TakesScreenshot) driver;

    // to create file object for the screenshot
    File file = ts.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File(filePath+fileName+"."+extension));
        }
}
