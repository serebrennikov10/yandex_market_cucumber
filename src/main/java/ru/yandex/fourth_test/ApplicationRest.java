package ru.yandex.fourth_test;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import ru.yandex.MarketPage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class ApplicationRest {
    private Logger LOGGER;
    private static String responseBody;

    {
        LOGGER = Logger.getLogger(ApplicationRest.class.getName());
    }

    @Step("Отправляю запрос GET")
    public void sendGetRequest(String region)  {
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city/";
        RequestSpecification httpRequest = RestAssured.given();
        LOGGER.info("Send GET request to http://restapi.demoqa.com/utilities/weather/city/"+region);
        Response response = httpRequest.request(Method.GET, region);
        responseBody = response.getBody().asString();
    }

    @Step("Проверяю, что респонc не пустой")
    public void checkResponseBodyNotNull() {
        Assert.assertNotNull("Error! Response is null!", responseBody);
    }

    @Step("Вывожу response в Инфо лог")
    public void outputResponseInfo() {
        System.out.println(responseBody);
    }

    @Step("Экспортирую response в отдельный файл html")
    public void outputResponseInFile() throws IOException {
        try {
            FileOutputStream file = new FileOutputStream("./src/main/resources/FourthTest.html");
            file.write(responseBody.getBytes());
            file.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        MarketPage.getBytesAnnotationWithArgs("/FourthTest.html");
    }
}