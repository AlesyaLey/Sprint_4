import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;



// Класс с автотестом
public class PraktikumTest {

    private WebDriver driver;

    @Test
    public void checkEmailInHeader() {
        // создали драйвер для браузера Chrome
             WebDriverManager.chromedriver().setup();
             driver = new ChromeDriver();
        // перешли на страницу тестового приложения
            driver.get("https://qa-mesto.praktikum-services.ru/");

        // создай объект класса страницы авторизации
        LoginPageMesto logIn = new LoginPageMesto(driver);
        // выполни авторизацию
          String email = "leybovich_36@gmail.com";
          String password = "q1w2e3r4";
          // передавай эти переменные внутрь метода
         logIn.login(email,password);
        // создай объект класса заголовка приложения
        HeaderPageMesto page = new HeaderPageMesto(driver);
        // дождись загрузки заголовка
        page.waitForLoadHeader();
        // получи текст элемента в заголовке
        String namePage = page.emailInHeader();
        // сделай проверку, что полученное значение совпадает с email
        assertEquals("Проверка :)",email,namePage);
    }
    @After
    public void tearDown() {
        // Закрой браузер
        driver.quit();
    }
    }