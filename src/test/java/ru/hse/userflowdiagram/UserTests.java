package ru.hse.userflowdiagram;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.attribute;

public class UserTests {
    @BeforeAll
    static void setupAllureReports() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(false)
                .savePageSource(true)
        );
    }
    @Test
    @Description("selenide test example")
    void testSomeTasks() {
        Selenide.open("https://www.lamoda.ru");
        ElementsCollection links = Selenide.$$("a").filterBy(attribute("href"));
        SelenideElement elem = links.find(attribute("href"));
        elem.click();
    }
}
