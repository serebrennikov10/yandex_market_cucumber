package ru.yandex;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Hooks {
    public WebDriver driver;
    //public WebDriverWait waitDriver;
    //public Actions MoveToElement;
/*

    @Before
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

       // MoveToElement = new Actions(driver);
        //waitDriver = new WebDriverWait(driver, 20);
        System.out.println("Start driver...");

    }

    @After
    @Step("Закрытие драйвера")
    public void close() {
        System.out.println("Close driver...");
        driver.close();
    }
*/


}
