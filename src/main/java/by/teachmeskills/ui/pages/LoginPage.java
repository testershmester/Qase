package by.teachmeskills.ui.pages;


import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.openqa.selenium.By.id;

public class LoginPage {

    public LoginPage open() {
        Selenide.open("/login");
        getWebDriver().manage().window().maximize();
        return new LoginPage();
    }

    public LoginPage login() {
        $(id("inputEmail")).shouldBe(visible).sendKeys("katrinzharskaya@gmail.com");
        $(id("inputPassword")).sendKeys("Qwerty12345");
        $(id("btnLogin")).click();
        return new LoginPage(); //TODO Change on Project page
    }
}
