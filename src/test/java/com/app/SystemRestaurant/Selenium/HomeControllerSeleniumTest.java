package com.app.SystemRestaurant.Selenium;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HomeControllerSeleniumTest {
    private static WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    static void tearDownAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    void testLoginPageLoads() {
        driver.get("http://localhost:8080/login");
        String title = driver.getTitle();
        assertTrue(title.contains("Login"), "El título de la página debe contener 'Login'");

        // Verifica que el formulario de login exista
        WebElement form = driver.findElement(By.id("loginForm"));
        assertNotNull(form, "El formulario de login debería estar presente");
    }

    @Test
    @Order(2)
    void testRedirectHomeToLogin() {
        driver.get("http://localhost:8080/");
        String currentUrl = driver.getCurrentUrl();
        assertEquals("http://localhost:8080/login", currentUrl, "Debe redirigir a la página de login");
    }

    @Test
    @Order(3)
    void testLoginFormInteraction() {
        driver.get("http://localhost:8080/login");

        WebElement inputUsuario = driver.findElement(By.id("nombreUsuario"));
        WebElement inputPassword = driver.findElement(By.cssSelector("input[type='password']"));
        WebElement buttonLogin = driver.findElement(By.cssSelector("button.login-btn"));

        inputUsuario.sendKeys("admin");
        inputPassword.sendKeys("1234");

        buttonLogin.click();

        String actualUrl = driver.getCurrentUrl();
        assertTrue(actualUrl.contains("/login") || actualUrl.contains("/index"),
                "Después del login debería permanecer o redirigir a una vista válida");
    }

    @Test
    @Order(4)
    void testIndexPageLoads() {
        driver.get("http://localhost:8080/index");

        assertDoesNotThrow(() -> {
            WebElement h1 = driver.findElement(By.tagName("h1"));
            assertTrue(h1.isDisplayed(), "El encabezado principal debe mostrarse");
        });
    }
}
