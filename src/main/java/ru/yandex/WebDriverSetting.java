package ru.yandex;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.annotations.*;
import ru.yandex.two_test_package.TwoTest;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public abstract class WebDriverSetting {
    public WebDriver driver;
        public Logger LOGGER;
    {
        LOGGER = Logger.getLogger(WebDriverSetting.class.getName());
    }

    //@BeforeTest
    //@Step("Удаление старых сриншотов")
    public void deleteScreenshots(){
        try {
            FileUtils.deleteDirectory(new File("./target/screenshots/"));
            FileUtils.deleteDirectory(new File("./src/main/resources/screenshots/"));
            System.out.println("Screenshots removed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //@BeforeClass
    //@Before
    @Step("Инициализация настроек драйвера")
    public void setUp() {
        try {
            Properties property = new Properties();
            FileInputStream file = new FileInputStream("src/main/resources/config.properties");
            property.load(file);
            String browser = property.getProperty("browser");
            if ((browser.equalsIgnoreCase("firefox")) || (browser.equalsIgnoreCase("ff"))) {
                System.out.println("Browser: " + browser);
                System.setProperty("webdriver.gecko.driver", "./src/main/resources/drivers/geckodriver.exe");
                driver = new FirefoxDriver();
            } else if (browser.equalsIgnoreCase("chrome")) {
                System.out.println("Browser: " + browser);
                System.setProperty("webdriver.chrome.driver", "./src/main/resources/drivers/chromedriver.exe");
                driver = new ChromeDriver();
            } else if ((browser.equalsIgnoreCase("ie")) || (browser.equalsIgnoreCase("internetexplorer"))
                    || (browser.equalsIgnoreCase("internet explorer"))) {
                System.out.println("Browser: " + browser);
                System.setProperty("webdriver.ie.driver", "./src/main/resources/drivers/IEDriverServer.exe");
                driver = new InternetExplorerDriver();
            } else {
                System.out.println("incorrect browser: " + browser);
            }
        }
        catch (IOException e) {
            System.err.println("ERROR: File not found!");
            e.printStackTrace();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("Start driver...");
    }

    //@AfterClass
    //@After
    @Step("Закрытие драйвера")
    public void close() {
        System.out.println("Close driver...");
        driver.close();
    }
}
