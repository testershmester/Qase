package by.teachmeskills;

import by.teachmeskills.ui.pages.LoginPage;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.testng.ScreenShooter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.By.id;

@Listeners({ScreenShooter.class})
public class LoginTest {

    @Test
    public void firstTest() {

        new LoginPage().open()
                       .login();

        $(id("createButton")).shouldBe(visible);
        assertThat($(id("createButton")).getText()).isNotNull()
                                                   .isEqualTo("Create new project");
        $(id("createButton")).click();
        $x("//button[@data-qase-test='project-create-button']").click();

        SelenideElement projectRowLocator = $x(String.format("//a[text()='%s']//ancestor::tr[@class='project-row']", "ShareLane"));
        projectRowLocator.$x("//a[contains(@class, 'btn-dropdown')]").click();

        projectRowLocator.$x("//a[text()='Delete']").shouldBe(visible).click();

        SelenideElement projectNameInput = $(id("inputTitle"));
        String text;
        if (!(Boolean) Selenide.executeJavaScript("return arguments[0].validity.valid;", projectNameInput)) {
            text = Selenide.executeJavaScript("return arguments[0].validationMessage;", projectNameInput);
        }
    }
}
