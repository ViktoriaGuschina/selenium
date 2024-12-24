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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChromeCreateTests {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
        ;
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        //options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void validForm() {
        List<WebElement> inputs = driver.findElements(By.cssSelector(".input__control"));
        inputs.get(0).sendKeys("Лето Джарерд");
        inputs.get(1).sendKeys("+79605034669");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(result.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", result.getText().trim());
    }

    @Test
    void InvalidNameInEnglishTest() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Buzova");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79605034669");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", result.getText().trim());
    }

    @Test
    void EmptyFirstNameSurnameFieldTest() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79605034669");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id= 'name'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Поле обязательно для заполнения", result.getText().trim());
    }

    @Test
    void SpacesInSurnameNameFieldTest() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("       ");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79605034669");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id= 'name'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Поле обязательно для заполнения", result.getText().trim());
    }

    @Test
    void IntroducingALocalNumberTest() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Саша");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("89605034669");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id= 'phone'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", result.getText().trim());
    }

    @Test
    void EnteringTenDigitsInPhoneFieldTest() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Александр Петрович Жмуров");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+7960503466");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id= 'phone'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", result.getText().trim());
    }

    @Test
    void EnteringTwentyDigitsInPhoneFieldTest() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Максим-Жмуриков");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+796050346651");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id= 'phone'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", result.getText().trim());
    }

    @Test
    void EmptyPhoneFieldTest() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Иванов-Петров Иван");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id= 'phone'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Поле обязательно для заполнения", result.getText().trim());
    }


    @Test
    void EmptyCheckboxTest() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Гусева-Лебедева Инна");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79605034669");
        driver.findElement(By.cssSelector(".button__text")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text"));
        assertTrue(result.isDisplayed());
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", result.getText().trim());
    }


}
