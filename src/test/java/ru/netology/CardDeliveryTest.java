package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {


    public String visitDate(int longDate) {
        LocalDate today = LocalDate.now();
        LocalDate method = today.plusDays(longDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return method.format(formatter);
    }


    @Test
    void shouldDeliveryTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Уфа");
        String planData = visitDate(3);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $(".calendar-input input").setValue(planData);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79685959595");
        $("[role='presentation']").click();
        $(".button__text").click();
        $x("//div//*[@class='notification__content']")
                .should(Condition.text("Встреча успешно забронирована на " + planData), Duration.ofSeconds(12));
        $x("//div//*[@class='notification__content']")
                .shouldBe(Condition.visible);

    }

    @Test
    void deliveryNoNameTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Уфа");
        String planData = visitDate(3);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $(".calendar-input input").setValue(planData);
        $("[data-test-id='phone'] input").setValue("+79685959595");
        $("[role='presentation']").click();
        $(".button__text").click();
        $("[data-test-id=name] span.input__sub")
                .should(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void deliveryNoCityTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        //$("[data-test-id=city] input").setValue("Уфа");
        String planData = visitDate(3);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $(".calendar-input input").setValue(planData);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79685959595");
        $("[role='presentation']").click();
        $(".button__text").click();
        $("[data-test-id=city]")
                .should(Condition.text("Поле обязательно для заполнения"));

    }

    @Test
    void deliveryNotCorrectNumberPhoneTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Уфа");
        String planData = visitDate(3);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $(".calendar-input input").setValue(planData);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("89685959595");
        $("[role='presentation']").click();
        $(".button__text").click();
        $("[data-test-id=phone]")
                .should(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void deliveryNoConsentTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Уфа");
        String planData = visitDate(3);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $(".calendar-input input").setValue(planData);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79855959595");
        $(".button__text").click();
        $("[data-test-id=agreement]")
                .should(Condition.cssClass("input_invalid"));

    }
}