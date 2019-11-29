package ru.yandex.two_test_package;

import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.openqa.selenium.support.PageFactory;
//import org.testng.annotations.Test;
import ru.yandex.MarketPage;
import ru.yandex.WebDriverSetting;

import java.io.IOException;

public class TwoTest extends WebDriverSetting {

    //@Test(description = "Второй тест. ч2")
    @Description(value = "Второй тест. ч2")
    @TmsLink(value = "Two test")
    public void outPopup() throws IOException {
        LOGGER.info("------------ T W O  T E S T (2) ------------");
        MarketPage marketPage = PageFactory.initElements(driver, MarketPage.class);
        marketPage.openPage("https://market.yandex.ru/")
                .openNoteCategoryNow()
                .sortByPrice()
                .openNoteSpecNow(2)
                .outFirstPopup();
    }

    //@Test(description = "Второй тест. ч1")
    @Description(value = "Второй тест. ч1")
    @TmsLink(value = "Two test")
    public void twoTest() throws IOException {
        LOGGER.info("------------ T W O  T E S T (1) ------------");
        MarketPage marketPage = PageFactory.initElements(driver, MarketPage.class);
        Note note1 = new Note();
        marketPage.openPage("https://market.yandex.ru/")
                .openNoteCategoryNow()
                .openNoteSpecNow(1)
                .setNewAttributes(note1);

        Note note2 = new Note();
        marketPage.openPage("https://market.yandex.ru/")
                .openNoteCategoryNow()
                .openNoteSpecNow(2)
                .setNewAttributes(note2);
        marketPage.equals(note1, note2);
    }

}