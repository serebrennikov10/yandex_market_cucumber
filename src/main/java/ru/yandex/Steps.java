package ru.yandex;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Пусть;
import cucumber.api.java.ru.То;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;

import java.io.IOException;

public class Steps {
/*    MyStepdefs step = new MyStepdefs();

    @Пусть("^перехожу на страницу$")
    public void перехожуНаСтраницу() throws IOException {
        step.openPage("https://market.yandex.ru/");
    }*/


/*
    @И("^меняю регион$")
    public void меняюРегион() throws IOException {
        step.selectNewRegionOnPage();
*/





/* private WebDriver driver;
    //private WebDriverWait waitDriver;
    //private Actions MoveToElement;


    public Steps(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        //this.waitDriver = new WebDriverWait(driver, 20);
        //this.MoveToElement = new Actions(driver);
    }



    @Пусть("^перехожу на страницу$")
    public void перехожуНаСтраницу() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.openPage("https://market.yandex.ru/");
    }

    @И("^меняю регион$")
    public void меняюРегион() throws IOException {
        MarketPage marketPage = new MarketPage(driver);
        marketPage.selectNewRegionOnPage();
    }

    @То("^вывод на экран (.*)$")
    public void выводНаЭкран(String final_text) throws IOException {
        System.out.println(final_text);
    }*/


}
