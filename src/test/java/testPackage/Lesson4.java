package testPackage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.files.DownloadActions.click;

public class Lesson4 {

    private final String containsCode =
            """
                    @ExtendWith({SoftAssertsExtension.class})
                    class Tests {
                      @Test
                      void test() {
                        Configuration.assertionMode = SOFT;
                        open("page.html");
                    
                        $("#first").should(visible).click();
                        $("#second").should(visible).click();
                      }
                    }
                    """;


    @BeforeAll
    static void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
    }


    @Test
    void testProjectSelenide() {
        //открыть главную страницу Гитхаба
        open("https://github.com");
        //ввести в поле поиска selenide и нажать enter
        $(".search-input").click();
        $("#query-builder-test").setValue("selenide").pressEnter();
        //кликнуть на репозиторий selenide/selenide
        $("a[href='/selenide/selenide']").click();
        //проверить, что мы в репе selenide/selenide
        $("#repository-container-header").shouldHave(text("selenide / selenide"));
        //перейти на страницу wiki
        $("#wiki-tab").click();
        //Убедиться, что в списке страниц (Pages) есть страница SoftAssertions
        $("#wiki-pages-filter").setValue("SoftAssertions");
        $("#wiki-pages-box").shouldHave(text("SoftAssertions"));
        //Открыть страницу SoftAssertions
        $("#wiki-pages-box").$(byText("SoftAssertions")).click();
        //Проверить, что внутри есть пример кода для JUnit5
        $(".markdown-body").shouldHave(text("3. Using JUnit5 extend test class:"));
        $(".markdown-body").shouldHave(Condition.text(containsCode));
    }
}
