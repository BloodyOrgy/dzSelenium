package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouBeSuccessfulForm(){
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Обмайкин Андрей Юрьевич");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79372785728");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        WebElement actualElement = driver.findElement(By.cssSelector("[data-test-id=order-success]"));
        String actualText = actualElement.getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText);
                assertTrue(actualElement.isDisplayed());
    }


    @Test
    public void shouldBeFailedEmptyNameInput() {
       driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+78996788347");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
       driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
               driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
       assertTrue(driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).isDisplayed());
    }

    @Test
    public void shouldBeFailedEnteringNameInLatin() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Andrey");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79372785728");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
                assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).isDisplayed());


    }
    @Test
    public void shouldBeFailedEnteringPhoneWrongNumber() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Обмайкин Андрей Юрьевич");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79372785728");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).isDisplayed());

    }

    @Test
    public void shouldBeFailedDontClickCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Обмайкин Андрей Юрьевич");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79372785728");
        driver.findElement(By.cssSelector("button.button")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).isDisplayed());


    }

}

