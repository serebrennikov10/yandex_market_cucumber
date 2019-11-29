package ru.yandex.fourth_test;

import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import java.io.IOException;

public class FourthTest extends ApplicationRest {

   // @Test(description = "Четвертый тест. Отправка запроса.")
    @Description(value = "метод GET")
    @TmsLink(value = "Fourth test")
    public void GetWeather() throws IOException {
        LOGGER.info("------------ F O U R T H  T E S T  ------------");
        //sentGetRequest("Belgorod");
    }
}
