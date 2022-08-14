package by.teachmeskills.ui.pages;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ProjectsPage {

    public ProjectsPage open() {
        Selenide.open("/login");
        getWebDriver().manage().window().maximize();
        return new ProjectsPage();
    }
}
