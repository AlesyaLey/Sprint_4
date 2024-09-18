import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.Assert.assertTrue;


public class TrueOrderScooter{
    private WebDriver driver;
    //переменная для значения в поле
    private String inputVal;
    //ожидаемый результат
    private String expected;
    //переменная для названия поля
    private String inputNameField;
    boolean actual;

    @Test
    public void TrueOrderScooterWithUpButton() {
        //WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        //driver = new ChromeDriver();
        driver = new FirefoxDriver();
        //главная страница
        driver.get("https://qa-scooter.praktikum-services.ru/");
        //Кнопка Заказать сверху
        driver.findElement(By.className("Button_Button__ra12g")).click();
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("Button_Middle__1CSJM")));//подождать загрузки страницы заказа

        OrderScooterForm order = new OrderScooterForm(driver);
        OrderScooterForm1 order1 = new OrderScooterForm1(driver);
        order.OrderFormMenu("* Имя", "Имя");
        order.OrderFormMenu("* Фамилия", "Фамилия");
        order.OrderFormMenu("* Адрес: куда привезти заказ", "Адрес");
        order.OrderFormMenu("* Станция метро", "Сокольники");
        order.OrderFormMenu("* Телефон: на него позвонит курьер", "89876543210");
        order1.clickButtonNext();
        order.takeOrder();
        //Дождаться кнопки Назад
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("Button_Inverted__3IF-i")));

        order1.insertDates("18.08.2023");
        order1.clickEmptySpace();
        order1.clickArroy();
        order1.clickTwoDay();
        order1.clickBlack();
        order1.writeCommentСourier("Комментарий");
        order1.clickButtonOrderMiddle(); // Заказать
        order1.clickYesButtonConfirmOrder(); // Кнопка ДА
        actual = order1.displayingButtonViewOrder();
        assertTrue("Кнопка 'Посмотреть статус' не найдена", actual);
    }

    @Test
    public void TrueOrderScooterWithDownButton(){
        //WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        //driver = new ChromeDriver();
        driver = new FirefoxDriver();
        //главная страница
        driver.get("https://qa-scooter.praktikum-services.ru/");
        //спуститься вниз до кнопки Заказа
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2500)");
        //кликнуть по кнопке заказа внизу
        driver.findElement(By.className("Button_Middle__1CSJM")).click();
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("Button_Middle__1CSJM")));//подождать загрузки страницы заказа

        OrderScooterForm order = new OrderScooterForm(driver);
        OrderScooterForm1 order1 = new OrderScooterForm1(driver);
        order.OrderFormMenu("* Имя", "Имя");
        order.OrderFormMenu("* Фамилия","Фамилия");
        order.OrderFormMenu("* Адрес: куда привезти заказ","Адрес");
        order.OrderFormMenu("* Станция метро","Сокольники");
        order.OrderFormMenu("* Телефон: на него позвонит курьер","89876543210");
        order1.clickButtonNext();
        order.takeOrder();
        //Дождаться кнопки Назад
       new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("Button_Inverted__3IF-i")));

        order1.insertDates("18.08.2023");
        order1.clickEmptySpace();
        order1.clickArroy();
        order1.clickOneDay();
        order1.clickGray();
        order1.writeCommentСourier("Комментарий");
        order1.clickButtonOrderMiddle(); // Заказать
        order1.clickYesButtonConfirmOrder(); // Кнопка ДА
        actual = order1.displayingButtonViewOrder();
        assertTrue("Кнопка 'Посмотреть статус' не найдена", actual);
    }


   @After
    public void tearDown() {
        // Закрой браузер
        driver.quit();
    }
}
