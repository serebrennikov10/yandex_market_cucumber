package ru.yandex;

import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ru.*;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import ru.yandex.fourth_test.ApplicationRest;
import ru.yandex.note_package.Note;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MySteps {
    private WebDriver driver;
    private ApplicationRest app = new ApplicationRest();
    private Note note1 = new Note();
    private Note note2 = new Note();
    private Logger LOGGER;
    {
        LOGGER = Logger.getLogger(MySteps.class.getName());
    }

    @Before(value = "~@without_driver")
    @Step("Инициализация настроек драйвера")
    public void setUp() {
        try {
            Properties property = new Properties();
            FileInputStream file = new FileInputStream("src/main/resources/config.properties");
            property.load(file);
            String browser = property.getProperty("browser");
            if ((browser.equalsIgnoreCase("firefox")) || (browser.equalsIgnoreCase("ff"))) {
                System.setProperty("webdriver.gecko.driver", "./src/main/resources/drivers/geckodriver.exe");
                driver = new FirefoxDriver();
            } else if (browser.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", "./src/main/resources/drivers/chromedriver.exe");
                driver = new ChromeDriver();
            } else if ((browser.equalsIgnoreCase("ie")) || (browser.equalsIgnoreCase("internetexplorer")) || (browser.equalsIgnoreCase("internet explorer"))) {
                System.setProperty("webdriver.ie.driver", "./src/main/resources/drivers/IEDriverServer.exe");
                driver = new InternetExplorerDriver();
            } else {
                LOGGER.warning("incorrect browser: " + browser);
            }
            LOGGER.info("Browser: " + browser);
        }
        catch (IOException e) {
            System.err.println("ERROR: File not found!");
            e.printStackTrace();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        LOGGER.info("Driver settings completed.");
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
        List<Map<String, String>> param = table.asMaps(String.class, String.class);
        for(int i=0;i<param.size();i++){
            if ((param.get(i).get("type_name")).equals("brand")) {
                marketPage.selectFilterByBrand(param.get(i).get("value_name"));
            }  else if ((param.get(i).get("type_name")).equals("color")) {
                marketPage.selectFilterByColor(param.get(i).get("value_name"));
            }  else {
                LOGGER.warning("Incorrect value type: "+param.get(i).get("type_name"));
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

    @Пусть("^ищу самый дешевый ноутбук$")
    public void ищуСамыйДешевыйНоутбук() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.findNoteMinPrice();
    }

    @Пусть("^ищу самый дорогой ноутбук$")
    public void ищуСамыйДорогойНоутбук() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.findNoteMaxPrice();
    }

    @Пусть("^считаю разницу между дорогим и дешевым ноутбуком$")
    public void считаюРазницуМеждуДорогимИДешевымНоутбуком() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.differenceNotePriceMinAndMax();
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

    @Пусть("^удаляю скриншоты в директории \"([^\"]*)\"$")
    public void удаляюСкриншотыВДиректории(String path) throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.deleteScreenshots(path);
    }

    @И("^ищу ноутбук по названию из списка \"([^\"]*)\"$")
    public void ищуНоутбукПоНазваниюИзСписка(String noteName) throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.searchNote(noteName);
    }

    @И("^ищу ноутбук по названию \"([^\"]*)\"$")
    public void ищуНоутбукПоНазванию(String note) throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.searchNote(note);
    }

    @То("^ноутбук с названием из списка \"([^\"]*)\" найден$")
    public void ноутбукСНазваниемИзСпискаНайден(String noteName) {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.equalsNameFirstElement(noteName);
    }

    @То("^ноутбук с названием \"([^\"]*)\" найден$")
    public void ноутбукСНазваниемНайден(String note) {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.equalsNameFirstElement(note);
    }

    @И("^устанавливаю атрибуты раздела Питание для ноутбука (.*)$")
    public void открываюХарактеристикиНоутбука(String note) {
        MarketPage marketPage = new MarketPage(driver);
        if (note.equals("note1"))
            marketPage.setNewAttributes(note1);
        else if (note.equals("note2"))
            marketPage.setNewAttributes(note2);
        else
            LOGGER.warning("Incorrect value type: "+note);
    }

    @И("^сравниваю атрибуты раздела Питание ноутбуков (.*) и (.*)$")
    public void сравниваюАтрибутыРазделаПитаниеНоутбуков(String n1, String n2) {
        MarketPage marketPage = new MarketPage(driver);
        if (n1.equals("note1") && n2.equals("note2")) {
            marketPage.equals(note1, note2);
        }  else {
            LOGGER.warning("Incorrect value type: "+n1+" or "+n2);
        }
    }

    @То("^вывожу результат сравнения атрибутов в консоль$")
    public void вывожуРезультатСравненияАтрибутовВКонсоль() {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.resultEquals();
    }

    @И("^кликаю на первую подсказку popup$")
    public void кликаюНаПервуюПодсказкуPopup() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.clickFirstPopup();
    }

    @То("^считываю и вывожу текст подсказки в консоль$")
    public void считываюИВывожуТекстПодсказкиВКонсоль() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.outFirstPopup();
    }

    @Пусть("^отправляю GET запрос к (.*) с регионом (.*)$")
    public void отправляюGETзапросСРегионом(String ms, String region) {
        if (ms.equals("MS Weather")) {
            app.sendGetRequest(region);
        }
        else {
            LOGGER.warning("Incorrect value type: "+ms);
        }
    }

    @И("^проверяю что от (.*) пришел не пустой ответ$")
    public void проверяюЧтоОтМсПришелНеПустойОтвет(String ms) {
        if (ms.equals("MS Weather")) {
            app.checkResponseBodyNotNull();
        }
        else {
            LOGGER.warning("Incorrect value type: "+ms);
        }
    }

    @Тогда("^экспортирую респонс в отдельный файл$")
    public void экспортируюРеспонсВОтдельныйФайл() throws IOException {
        app.outputResponseInFile();
    }

    @Тогда("^вывожу респонс в консоль$")
    public void вывожуРеспонсВКонсоль() {
        app.outputResponseInfo();
    }

    @То("^вывожу в консоль (.*)$")
    public void вывожуВКонсоль(String final_text) {
        System.out.println(final_text);
    }

    @After(value = "~@without_driver", order = 2)
    @Step("Закрытие драйвера")
    public void close() {
        driver.manage().deleteAllCookies();
        driver.quit();
        LOGGER.info("Close driver.");
    }

    @After(order = 1)
    public void getScenarioInfo(Scenario scenario) {
        System.out.println(scenario.getId());
        System.out.println(scenario.getName());
        System.out.println(scenario.getStatus());
        System.out.println(scenario.getSourceTagNames());
    }
}