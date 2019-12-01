package ru.yandex;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.note_package.Note;
import ru.yandex.note_package.NotePrice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class MarketPage  {
    private WebDriver driver;
    private WebDriverWait waitDriver;
    private Actions MoveToElement;
    private static NotePrice minPrice = new NotePrice();
    private static NotePrice maxPrice = new NotePrice();
    private static boolean result;
    private Logger LOGGER;
    {
        LOGGER = Logger.getLogger(MarketPage.class.getName());
    }

    public MarketPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.waitDriver = new WebDriverWait(driver, 20);
        this.MoveToElement = new Actions(driver);
    }

    @FindBy(css = "button[class*='button region-select-form']")
    private WebElement buttonSelectRegion;
    @FindBy(xpath = "//input[contains(@placeholder, 'Укажите другой регион')]")
    private WebElement inputRegion;
    @FindBy(xpath = "//span[contains(@class, 'header2-menu__item_type_region')]")
    private WebElement changeRegion;
    @FindBy(xpath = "//span[text()='Все категории']/../..")
    private WebElement allCategory;
    @FindBy(xpath = "//a[contains(@href, '/catalog--kompiuternaia-tekhnika/')]")
    private WebElement compCategory;
    @FindBy(linkText = "Ноутбуки")
    private WebElement notebookCategory;
    @FindBy(xpath = "//span[contains(@class, 'view-switcher__item_type_list')]/..")
    private WebElement setListStyle;
    @FindBy(linkText = "по цене")
    private WebElement sortPrice;
    @FindBy(xpath = "//a[text()='по цене']/..")
    private WebElement divNameSortPrice;
    @FindBy(xpath = "//input[@id=\"glpricefrom\"]")
    private WebElement selectCostFrom;
    @FindBy(xpath = "//*[@id=\"glpriceto\"]")
    private WebElement selectCostTo;
    @FindBy(xpath = "//li[@data-name='spec']")
    private WebElement pageNoteSpec;
    @FindBy(xpath = "//*[text()='?']/..")
    private WebElement firstPopup;
    @FindBy(xpath = "//div[contains(@class, 'popup_visibility_visible')]//div[@class='n-hint-button__article']")
    private WebElement popupWindow;
    @FindBy(id = "header-search")
    private WebElement searchInputHeader;
    @FindBy(xpath = ".//div[@class='n-snippet-card2__title']")
    private List<WebElement> elementsName;
    @FindBy(className = "n-snippet-card2__main-price")
    private List<WebElement> elementsPrice;
    @FindBy(xpath = "//div[contains(@class, 'n-snippet-list')]")
    private List<WebElement> elementsInfoNote;
    @FindBy(xpath = "//fieldset[@data-autotest-id='7893318']")
    private List<WebElement> brandsNames;
    @FindBy(xpath = "//fieldset[@data-autotest-id='13887626']")
    private List<WebElement> colorsNames;
    @FindBy(xpath = "//h2[text()='Питание']/..")
    private List<WebElement> blockPowerList;

    private By adaptiveLayout = By.className("n-adaptive-layout");
    private By frameInputRegion = By.className("header2-region-popup");
    private By controlHamburger = By.cssSelector("div[class*='n-w-tab__control-hamburger']");
    private By categoryTabsColumn  = By.className("n-w-tabs__tabs-column");
    private By compCategoryPage = By.xpath("//h1[text()='Компьютерная техника']");
    private By noteHeadlineHeader  = By.className("headline__header");
    private By noteSnippetList =  By.xpath("//div[contains(@class, 'n-snippet-list')]");
    private By noteNameTitle = By.className("n-snippet-card2__title");
    private By noteMainPrice = By.className("n-snippet-card2__main-price");
    private By notePage = By.className("n-product-content-block");
    private By timeWorkLine = By.xpath(".//*[text()='Время работы']/../..");
    private By valueBatteryLine = By.xpath(".//*[text()='Емкость аккумулятора']/../..");
    private By valueBatteryPowerLine = By.xpath(".//*[text()='Емкость аккумулятора (Вт*ч)']/../..");
    private By cellsLine = By.xpath(".//*[text()='Количество ячеек батареи']/../..");
    private By typeBatteryLine = By.xpath(".//*[text()='Тип аккумулятора']/../..");
    private String asc = "n-filter-sorter i-bem n-filter-sorter_js_inited n-filter-sorter_sort_asc n-filter-sorter_state_select";
    private String desc = "n-filter-sorter i-bem n-filter-sorter_js_inited n-filter-sorter_sort_desc n-filter-sorter_state_select";

    @Step("Открываю страницу")
    public MarketPage openPage(String url) throws IOException {
        driver.get(url);
        waitDriver.until(ExpectedConditions.visibilityOfElementLocated(adaptiveLayout));
        saveScreenshotPNG (driver, "Страница ЯндексМаркет");
        getTitle(driver.getTitle());
        return new MarketPage(driver);
    }

    @Step("Проверяю title")
    public MarketPage getTitle(String title) {
        Assert.assertEquals("Яндекс.Маркет — выбор и покупка товаров из проверенных интернет-магазинов", title);
        return new MarketPage(driver);
    }

    public static class RegionOnPage {
        private static volatile RegionOnPage instance;
        private RegionOnPage() {
        }
        public static RegionOnPage getInstance() {
            if (instance == null) {
                synchronized (RegionOnPage.class) {
                    if (instance == null) {
                        instance = new RegionOnPage();
                    }
                }
            }
            return instance;
        }
    }

    @Step("Меняю регион")
    public void selectNewRegionOnPage() throws IOException {
        RegionOnPage region1 = RegionOnPage.getInstance();
        String nowRegion = changeRegion.getText();
        String newRegion = "Воронеж";
        if (!nowRegion.equals(newRegion)) {
            changeRegion.click();
            waitDriver.until(ExpectedConditions.visibilityOfElementLocated(frameInputRegion));
            inputRegion.sendKeys(newRegion);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            inputRegion.sendKeys(Keys.ENTER);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MoveToElement.moveToElement(buttonSelectRegion).perform();
            buttonSelectRegion.click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        saveScreenshotPNG (driver, "Установлен регион "+newRegion);
    }

    @Step("Открываю категорий")
    public MarketPage openAllCategories() throws IOException {
        waitDriver.until(ExpectedConditions.visibilityOfElementLocated(controlHamburger));
        MoveToElement.moveToElement(allCategory).perform();
        allCategory.click();
        waitDriver.until(ExpectedConditions.visibilityOfElementLocated(categoryTabsColumn));
        saveScreenshotPNG (driver, "Все категории");
        return new MarketPage(driver);
    }

    @Step("Открываю категорию: Компьютеры")
    public MarketPage openCompCategory() throws IOException {
        MoveToElement.moveToElement(compCategory).perform();
        compCategory.click();
        waitDriver.until(ExpectedConditions.visibilityOfElementLocated(compCategoryPage));
        saveScreenshotPNG (driver, "Компьютерная техника");
        return new MarketPage(driver);
    }

    @Step("Открываю Ноутбуки")
    public MarketPage openNotebookCategory() throws IOException {
        MoveToElement.moveToElement(notebookCategory).perform();
        notebookCategory.click();
        saveScreenshotPNG (driver, "Ноутбуки");
        return new MarketPage(driver);
    }

    public MarketPage waitNotePage() throws IOException {
        waitDriver.until(ExpectedConditions.visibilityOfElementLocated(noteHeadlineHeader));
        saveScreenshotPNG (driver, "Ноутбуки");
        return new MarketPage(driver);
    }

    public MarketPage waitSortNotePage() throws IOException {
        waitDriver.until(ExpectedConditions.visibilityOfElementLocated(noteSnippetList));
        return new MarketPage(driver);
    }

    public MarketPage setListStyleVisibility(){
        MoveToElement.moveToElement(setListStyle).perform();
        setListStyle.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new MarketPage(driver);
    }

    @Step("Выбираю категории 'Компьютеры' -> 'Ноутбуки'")
    public MarketPage openNoteCategoryNow() throws IOException {
        openAllCategories();
        openCompCategory();
        openNotebookCategory();
        setListStyleVisibility();
        return new MarketPage(driver);
    }

    @Step("Сортирую по цене")
    public MarketPage sortByPrice() throws IOException {
        sortPrice.click();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        saveScreenshotPNG(driver, "Сортировка по цене");
        return new MarketPage(driver);
    }

    @Step("Сортирую по возрастанию")
    public MarketPage sortPriceByAsc() throws IOException {
        String classNameSortPrice = divNameSortPrice.getAttribute("class");
        if (classNameSortPrice.equals(asc)) {
        }
        else {
            sortPrice.click();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        saveScreenshotPNG(driver, "Сортировка по возрастанию (дешевый)");
        return new MarketPage(driver);
    }

    @Step("Сортирую по убыванию")
    public MarketPage sortPriceByDesc() throws IOException {
        String classNameSortPrice = divNameSortPrice.getAttribute("class");
        if (classNameSortPrice.equals(desc)) {
        }
        else {
            sortPrice.click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        saveScreenshotPNG(driver, "Сортировка по убыванию (дорогой)");
        return new MarketPage(driver);
    }

    @Step("Задаю параметры поиска по стоимости: от {From} до {To}")
    public MarketPage selectFilterByCost(String From, String To) throws IOException {
        selectCostFrom.click();
        selectCostFrom.sendKeys(From);
        selectCostTo.click();
        selectCostTo.sendKeys(To);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        saveScreenshotPNG(driver, "Установлен фильтр стоимости");
        return new MarketPage(driver);
    }

    @Step("Задаю параметры поиска по бренду: {brand}")
    public MarketPage selectFilterByBrand(String brand) throws IOException {
        for  (WebElement brandsList:brandsNames) {
            brandsList.findElement(By.xpath("//span[text()='"+brand+"']/..")).click();
            waitDriver.until(ExpectedConditions.visibilityOfElementLocated(noteSnippetList));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            saveScreenshotPNG(driver, brand);
        }
        return new MarketPage(driver);
    }

    @Step("Задаю параметры поиска по цвету: {color}")
    public MarketPage selectFilterByColor(String color) throws IOException {
        for (WebElement colorList:colorsNames){
            colorList.findElement(By.xpath("//span[text()='Цвет "+color+"']/..")).click();
            waitDriver.until(ExpectedConditions.visibilityOfElementLocated(noteSnippetList));
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        saveScreenshotPNG(driver, "Цвет "+color);
        return new MarketPage(driver);
    }

    @Step("Считаю разницу между дорогим и дешевым ноутбуком")
    public MarketPage differenceNotePriceMinAndMax() {
        differencePrice(minPrice.price, maxPrice.price);
        return new MarketPage(driver);
    }

    @Step("Ищу дешевый ноутбук")
    public MarketPage findNoteMinPrice() throws IOException {
        sortPriceByAsc();
        System.out.println("Дешевый Ноутбук:");
        setFirstNameAndPriceForNote(minPrice);
        return new MarketPage(driver);
    }

    @Step("Ищу дорогой ноутбук")
    public MarketPage findNoteMaxPrice() throws IOException {
        sortPriceByDesc();
        System.out.println("Дорогой Ноутбук:");
        setFirstNameAndPriceForNote(maxPrice);
        return new MarketPage(driver);
    }

    private void setFirstNameAndPriceForNote(NotePrice note){
        for (WebElement element : elementsInfoNote) {
            note.setName(element.findElement(noteNameTitle).getText());
            note.setPrice(Integer.parseInt((element.findElement(noteMainPrice).getText()).replaceAll("\\D+", "")));
            System.out.println(note);
            stepAllure("Название:  "+note.getName()+" Цена: "+note.getPrice());
        }
    }

    @Step("Считаю разницу")
    private MarketPage differencePrice(int min, int max) {
        Integer differencePrice = max - min;
        Assert.assertNotNull("ERROR: Difference price is NULL!" ,differencePrice);
        stepAllure("Разница в цене: "+ differencePrice +" р.");
        System.out.println("Разница в цене составляет "+ differencePrice +" р.");
        return new MarketPage(driver);
    }

    class WebElementComparator implements Comparator<WebElement> {
        @Override
        public int compare(WebElement o1, WebElement o2) {
            if (o1 == o2) return 0;
            return o1.getText().compareTo(o2.getText());
        }
    }

    @Step("Список ноутбуков")
    public void stepOutputInfoInList(String list){    }
    @Step("Вывожу список ноутбуков")
    public MarketPage outputInfoInList() throws IOException {
        for (WebElement elementInfoAboutNote:elementsInfoNote) {
            List<WebElement> elements = new ArrayList<>(elementInfoAboutNote.findElements(noteNameTitle));
            elements.sort(new WebElementComparator()); //сортировка
            System.out.println("Вывожу отсортированные элементы списка:");
            for (WebElement element:elements) {
                System.out.println(element.getText());
            }
            String text = elements.stream().map(WebElement::getText).collect(Collectors.joining("\n"));
            stepOutputInfoInList(text);
        }
        saveScreenshotPNG(driver, "Ноутбуки");
        return new MarketPage(driver);
    }

    @Step("Список map ноутбуков")
    public void stepOutputInfoInMap(String list){    }
    @Step("Вывожу ноутбуки из Мар <name, price>")
    public MarketPage outputInfoInMap() throws IOException {
        Map<String , String> map = new HashMap<>();
        for(int i=0;i<elementsName.size();i++)
            map.put(elementsName.get(i).getText(), elementsPrice.get(i).getText());
        System.out.println("Вывожу данные из map: <name, price>");
        Map<String, String> reversedMap = new TreeMap<>(map);
        for (Map.Entry entry : reversedMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        String text = map.entrySet().stream()
                .map(entry -> (entry.getKey() + " - " + entry.getValue()))
                .collect(Collectors.joining("\n"));
        stepOutputInfoInMap(text);
        saveScreenshotPNG(driver, "Элементы map");
        return new MarketPage(driver);
    }

    @Step("Выбираю ноутбук, порядковый номер - {number}")
    public MarketPage openNoteSpecNow(int number) throws IOException{
        openNote(number);
        return new MarketPage(driver);
    }

    @Step("Открываю характеристики ноутбука")
    public MarketPage openNoteSpec() throws IOException{
        openSpec();
        return new MarketPage(driver);
    }

    @Step("Открываю выбранный ноут")
    public MarketPage openNote(int number) throws IOException {
        int n = number - 1;
        WebElement note = elementsName.get(n);
        String noteName = note.getText();
        MoveToElement.moveToElement(note).perform();
        note.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("Click on note: "+noteName);
        saveScreenshotPNG (driver, noteName);
        return new MarketPage(driver);
    }

    @Step("Открываю характеристики ноутбука")
    public MarketPage openSpec() throws IOException {
        MoveToElement.moveToElement(pageNoteSpec).perform();
        pageNoteSpec.click();
        saveScreenshotPNG (driver, "Характеристики");
        return new MarketPage(driver);
    }

    @Step("Переключаюсь на новую вкладку")
    public MarketPage switchToNewTabs() throws IOException {
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        LOGGER.info("Switch to new tabs...");
        driver.switchTo().window(tabs.get(1));
        LOGGER.info("Switch is done.");
        return new MarketPage(driver);
    }

    @Step("Переключаюсь на старую вкладку")
    public MarketPage switchToOldTabs() throws IOException {
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        LOGGER.info("Clode tabs...");
        driver.close();
        LOGGER.info("Switch to old tabs...");
        driver.switchTo().window(tabs.get(0));
        LOGGER.info("Switch is done.");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new MarketPage(driver);
    }

    @Step("Кликаю на первую подсказку popup")
    public MarketPage clickFirstPopup() throws IOException {
        firstPopup.click();
        popupWindow.click();
        saveScreenshotPNG (driver, "открытие popup");
        return new MarketPage(driver);
    }

    @Step("Считываю и вывожу текст подскази в консоль")
    public MarketPage outFirstPopup() throws IOException {
        String allPopup = popupWindow.getText();
        String popup = allPopup.replace("Словарь терминов по категории Ноутбуки", "");
        System.out.println("Текст подсказки: \n"+popup);
        stepAllure("Текст подсказки: \n"+popup);
        return new MarketPage(driver);
    }

    @Step("Поиск товаров из файла {FileName}")
    public MarketPage readAndSearchFromExcel(String FileName) throws IOException {
        XSSFWorkbook excelFile = new XSSFWorkbook(new FileInputStream("src/main/resources/"+FileName));
        XSSFSheet excelSheet = excelFile.getSheet("book1");
        int rowSize = excelSheet.getLastRowNum()+1;
        for(int i=0;i<rowSize;i++) {
            XSSFRow row = excelSheet.getRow(i);
            String noteName = row.getCell(0).getStringCellValue();
            searchNote(noteName);
        }
        return new MarketPage(driver);
    }

    @Step("Ищу: {noteName}")
    public MarketPage searchNote(String noteName) throws IOException {
        LOGGER.info("Ищу: "+noteName);
        searchInputHeader.clear();
        searchInputHeader.sendKeys(noteName);
        searchInputHeader.sendKeys(Keys.ENTER);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setListStyleVisibility();
        captureScreen(noteName);
        saveScreenshotPNG (driver, noteName);
        return new MarketPage(driver);
    }

    @Step("Поиск найденного ноутбука на странице")
    public MarketPage equalsNameFirstElement(String noteName)  {
        WebElement elementsNoteName = elementsName.get(0);
        String firstNoteName = elementsNoteName.getText();
        LOGGER.info("Первый ноутбук из списка наденных: "+firstNoteName);
        System.out.println("Найден: "+noteName);
        assertTrue("Error! Result is False!", firstNoteName.contains(noteName));
        return new MarketPage(driver);
    }

    @Step("Устанавливаю атрибуты")
    public MarketPage setNewAttributes(Note note){
        String timeWork = null;
        String valueBattery = null;
        String valueBatteryPower = null;
        String cells = null;
        String typeBattery = null;
        for (WebElement blockPowerElements:blockPowerList) {
            try {
                timeWork = blockPowerElements.findElement(timeWorkLine).getText();
            } catch(NoSuchElementException | org.openqa.selenium.NoSuchElementException ignored) {
                timeWork = "Время работы \n Нет значения";
            }
            try {
                valueBattery = blockPowerElements.findElement(valueBatteryLine).getText();
            } catch(NoSuchElementException | org.openqa.selenium.NoSuchElementException ignored) {
                valueBattery = "Емкость аккумулятора \n Нет значения";
            }
            try {
                valueBatteryPower = blockPowerElements.findElement(valueBatteryPowerLine).getText();
            } catch(NoSuchElementException | org.openqa.selenium.NoSuchElementException ignored) {
                valueBatteryPower = "Емкость аккумулятора (Вт*ч) \n Нет значения";
            }
            try {
                cells = blockPowerElements.findElement(cellsLine).getText();
            } catch(NoSuchElementException | org.openqa.selenium.NoSuchElementException ignored) {
                cells = "Количество ячеек батареи \n Нет значения";
            }
            try {
                typeBattery = blockPowerElements.findElement(typeBatteryLine).getText();
            } catch(NoSuchElementException | org.openqa.selenium.NoSuchElementException ignored) {
                typeBattery = "Тип аккумулятора \n Нет значения";
            }
        }
        note.setTimeWork(timeWork);
        note.setValueBattery(valueBattery);
        note.setValueBatteryPower(valueBatteryPower);
        note.setCells(cells);
        note.setTypeBattery(typeBattery);
        System.out.println("Значения блока Питание:\n"+note);
        stepAllure("Значения блока Питание: "+note);
        return new MarketPage(driver);
    }

    @Step("Сравниваю два ноутбука")
    public void equals(Note one, Note two){
        result = one.equals(two);
    }

    @Step("Вывожу результат сравнения")
    public void resultEquals(){
        System.out.println("Сравниваю значение: "+result);
        stepAllure("Результат: "+result);
    }

    @Step("Удаление старых сриншотов")
    public MarketPage deleteScreenshots(String path){
        try {
            FileUtils.cleanDirectory(new File(path));
            LOGGER.info("Screenshots removed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MarketPage(driver);
    }


    @Attachment(value = "{nameScreen}", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver, String nameScreen) throws IOException {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Вложение", type = "application/json", fileExtension = ".txt")
    public static byte [] getBytesAnnotationWithArgs(String resourceName) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/resources", resourceName));
    }

    @Step("{title}")
    private void stepAllure(String title) {
    }

    private void captureScreen(String noteName) {
        String path;
        try {
            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            path = "./src/main/resources/screenshots/" + noteName + ".png";
            FileUtils.copyFile(screenshot, new File(path));
        }
        catch(IOException e) {
            e.getMessage();
        }
    }
}