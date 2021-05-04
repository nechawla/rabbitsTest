import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import util.ReadExcelFile;
import util.ScreenCaptureUtil;



import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class testRedirectLinks {
    static AndroidDriver<MobileElement> driver;
    String url="https://www.rabbitsreviews.com/index.html";

    @Test(dataProvider="testdata")
    public void redirectLink(String linkText,String expectedResult) throws IOException,MalformedURLException, InterruptedException {
        DesiredCapabilities capabilities=new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"UiAutomator2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"Pixel 4");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"9.0");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME,"Chrome");
        capabilities.setCapability(MobileCapabilityType.NO_RESET,"true");
        capabilities.setCapability("appWaitForLaunch" ,"false");
        capabilities.setCapability("androidInstallTimeout", 180000);
        capabilities.setCapability("ignoreHiddenApiPolicyError","true");
        capabilities.setCapability("autoAcceptAlerts", true);
        capabilities.setCapability("autoDismissAlerts", true);
        capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);

         // Works for Desktop not for mobile
        /*ChromeOptions options=new ChromeOptions();
        options.setExperimentalOption("excludeSwitches",
                Arrays.asList("disable-popup-blocking"));
        options.merge(capabilities);
         driver=new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"),options);
         */
        driver=new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"),capabilities);
        driver.get(url);
        driver.findElement(By.linkText("OK")).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText)));
        driver.findElement(By.linkText("Adult Time")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      //  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//button[text()='ALLOW']")));
        //  driver.findElement(By.xpath(".//button[text()='ALLOW']")).click();
     //   wait.until(ExpectedConditions.alertIsPresent());

    // Didnt fix the issue
        driver.switchTo().activeElement();
        System.out.println(driver.getPageSource());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText)));
        String actualText=driver.findElement(By.linkText(linkText)).getText();
        Assert.assertEquals(actualText,expectedResult);
        ScreenCaptureUtil.takeScreenshot(driver,"screenshot/",linkText,"png");
    }



    @AfterMethod
    void ProgramTermination(){
        driver.quit();
    }

    @DataProvider(name="testdata")
    public Object [] [] TestDataFeed()
    {
        String userDir=System.getProperty("user.dir");
        ReadExcelFile config = new ReadExcelFile(userDir+"/src/main/resources/testData/redirectData.xlsx");
        int rows = config.getRowCount(0);
        Object[][] data = new Object[rows][2];
        for(int i=0;i<rows;i++){
            data[i][0]=config.getData(0,i,0);
            data[i][1]=config.getData(0,i,1);
        }
        return data;
    }
}
