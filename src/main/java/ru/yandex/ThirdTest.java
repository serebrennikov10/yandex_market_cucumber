package ru.yandex;

import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

public class ThirdTest extends WebDriverSetting {

   // @Test(description = "Поиск товаров из файла Excel")
    @Description(value = "Парсинг и поиск товаров из файла Excel")
    @TmsLink(value = "Third test")
    public void noteInFile() throws IOException {
        LOGGER.info("------------ T H I R D  T E S T ------------");
        MarketPage marketPage = PageFactory.initElements(driver, MarketPage.class);
        marketPage.openPage("https://market.yandex.ru/")
                .readAndSearchFromExcel("ListNote.xlsx");
    }
}



