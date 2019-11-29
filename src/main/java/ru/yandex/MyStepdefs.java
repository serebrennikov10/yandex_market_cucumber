package ru.yandex;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.it.Ma;
import cucumber.api.java.ru.*;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.fourth_test.ApplicationRest;
import ru.yandex.two_test_package.Note;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class MyStepdefs {

    public WebDriver driver;
    private WebDriverWait waitDriver;
    private Actions MoveToElement;

    @Before(value = "@with_driver")
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

        //MoveToElement = new Actions(driver);
        //waitDriver = new WebDriverWait(driver, 20);
        System.out.println("Start driver...");
    }



public MyStepdefs() {
        PageFactory.initElements(driver, this);
        //this.driver = driver;
        //this.waitDriver = new WebDriverWait(driver, 20);
        //this.MoveToElement = new Actions(driver);
    }

    public Logger LOGGER;
    {
        LOGGER = Logger.getLogger(WebDriverSetting.class.getName());
    }


    public void out(String a){
        System.out.println(a);
    }


    @Дано("^просто (.*)$")
    public void просто(String a) throws Throwable {
        out(a);
    }

    @Когда("^делаю вывод (.*)$")
    public void делаюВывод(String a) throws Throwable {
        out(a);
    }

    @Тогда("^вывожу все (.*)$")
    public void вывожуВсе(String a) throws Throwable {
        out(a);
    }

    @Пусть("^перехожу на страницу Яндекс Маркет$")
    public void перехожуНаСтраницуЯндексМаркет() throws IOException {
    MarketPage marketPage = new MarketPage(driver);
    marketPage.openPage("https://market.yandex.ru/");
    }

    @И("^меняю регион$")
    public void меняюРегион() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.selectNewRegionOnPage();
    }

    @И("^перехожу в Все категории$")
    public void перехожуВВсеКатегории() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.openAllCategories();
    }
    @И("^перехожу в категорию Компьютеры$")
    public void перехожуВКатегориюКомпьютеры() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.openCompCategory();
    }
    @И("^перехожу в подкатегорию Ноутбуки$")
    public void перехожуВПодкатегориюНоутбуки() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.openNotebookCategory();
    }

    @И("^выбираю стиль отображения по столбцам$")
    public void выбираюСтильОтображенияПоСтолбцам() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.setListStyleVisibility();
    }

    @То("^открыта страница Ноутбуки$")
    public void открытаСтраницаНоутбуки() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.waitNotePage();
    }

    @Когда("^устанавливаю фильтр по цене от (\\d+) до (\\d+)$")
    public void устанавливаюФильтрПоЦенеОтДо(String from, String to) throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.selectFilterByCost(from, to);
    }

    @И("^устанавливаю фильтр по бренду (.*)$")
    public void устанавливаюФильтрПоБренду(String brand) throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.selectFilterByBrand(brand);
    }

    @И("^устанавливаю другие фильтры:$")
    public void устанавливаюДругиеФильтры(DataTable table) throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        //Map<String, String> parametrs = param.asMap(String.class, String.class);
        List<Map<String, String>> param = table.asMaps(String.class, String.class);
        for(int i=0;i<param.size();i++){
            if ((param.get(i).get("type_name")).equals("brand")) {
                marketPage.selectFilterByBrand(param.get(i).get("value_name"));
            }  else if ((param.get(i).get("type_name")).equals("color")) {
                marketPage.selectFilterByColor(param.get(i).get("value_name"));
            }  else {
                System.out.println("Incorrect value type: "+param.get(i).get("type_name"));
            }
        }
    }

    @И("^устанавливаю фильтр по цвету (.*)$")
    public void устанавливаюФильтрПоЦвету(String color) throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.selectFilterByColor(color);
    }

    @И("^выполнена фильтрация/сортировка ноутбуков$")
    public void выполненаФильтрацияСортировкаНоутбуков() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.waitSortNotePage();
    }

    @И("^устанавливаю сортировку по цене$")
    public void устанавливаюСортировкуПоЦене() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.sortByPrice();
    }

    @И("^считаю разницу между дорогим и дешевым ноутбуком$")
    public void СчитаюРазницуМеждуДорогимИДешевымНоутбуком() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.findNotePriceMinAndMax();
    }

    @И("^вывожу список ноутбуков в консоль$")
    public void вывожуСписокНоутбуковВКонсоль() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.outputInfoInList();
    }

    @И("^вывожу ноутбуки из Мар <name, price>$")
    public void вывожуНоутбукиИзМар() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.outputInfoInMap();
    }

    @Когда("^выбираю ноутбук с порядковым номером (\\d+)$")
    public void выбираюНоутбукСПорядковымНомером(int number) throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.openNote(number);
    }

    @И("^открываю характеристики ноутбука$")
    public void открываюХарактеристикиНоутбука() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.openSpec();
    }


    @И("^переключаюсь на новую вкладку$")
    public void переключаюсьНаНовуюВкладку() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.switchToNewTabs();
    }

    @И("^переключаюсь на старую вкладку$")
    public void переключаюсьНаСтаруюВкладку() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.switchToOldTabs();
    }

    @И("^ищу ноутбук по названию из списка примеров \"([^\"]*)\"$")
    public void ищуНоутбукПоНазваниюИзСпискаПримеров(String noteName) throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.searchNote(noteName);
    }

    @То("^ноутбук с названием \"([^\"]*)\" найден$")
    public void ноутбукСНазваниемНайден(String noteName)throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.equalsNameFirstElement(noteName);
    }


    Note note1 = new Note();
    Note note2 = new Note();

    @И("^устанавливаю атрибуты раздела Питание для ноутбука (.*)$")
    public void открываюХарактеристикиНоутбука(String note) throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        if (note.equals("note1")) {
            marketPage.setNewAttributes(note1);
        }  else if (note.equals("note2")) {
            marketPage.setNewAttributes(note2);
        }  else {
            System.out.println("Incorrect value type: "+note);
        }
    }

    @И("^сравниваю атрибуты раздела Питание ноутбуков (.*) и (.*)$")
    public void сравниваюАтрибутыРазделаПитаниеНоутбуков(String n1, String n2) throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        if (n1.equals("note1") && n2.equals("note2")) {
            marketPage.equals(note1, note2);
        }  else {
            System.out.println("Incorrect value type: "+n1+" or "+n2);
        }
    }

    @То("^вывожу результат сравнения атрибутов в консоль$")
    public void вывожуРезультатСравненияАтрибутовВКонсоль() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.resultEquals();
    }

    @Пусть("^отправляю GET запрос к (.*) с регионом (.*)$")
    public void отправляюGETзапросСРегионом(String ms, String region) throws IOException {
        ApplicationRest app = new ApplicationRest();

        if (ms.equals("MS Weather")) {
            app.sendGetRequest(region);
        }
        else {
            System.out.println("Incorrect value type: "+ms);
        }
    }

    @И("^проверяю что от (.*) пришел не пустой ответ$")
    public void проверяюЧтоОтМсПришелНеПустойОтвет(String ms) throws IOException {
        ApplicationRest app = new ApplicationRest();
        if (ms.equals("MS Weather")) {
            app.checkResponseBodyNotNull();
        }
        else {
            System.out.println("Incorrect value type: "+ms);
        }
    }

    @Тогда("^вывожу респонс в отдельный файл$")
    public void вывожуРеспонсВОтдельныйФайл() throws IOException {
        ApplicationRest app = new ApplicationRest();
        app.outputResponseInFile();
    }

    @Тогда("^вывожу респонс в консоль$")
    public void вывожуРеспонсВКонсоль() throws IOException {
        ApplicationRest app = new ApplicationRest();
        app.outputResponseInfoLog();
    }

    @То("^вывожу в консоль (.*)$")
    public void вывожуВКонсоль(String final_text) throws IOException {
        System.out.println(final_text);
    }

    @After
    @Step("Закрытие драйвера")
    public void close() {
        System.out.println("Close driver...");
        //driver.close();
        //driver.quit();
    }


    // для примера:
    //  "([^"]*)"
    //  "(\d+)"



}