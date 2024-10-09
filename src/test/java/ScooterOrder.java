import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertEquals;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;

@RunWith(Parameterized.class)
public class ScooterOrder {


    private WebDriver driver;
    //переменная для значения в поле
    private String inputVal;
    //ожидаемый результат
    private String expected;
    //переменная для названия поля
    private String inputNameField;

    public ScooterOrder (String inputVal,String inputNameField, String expected) {
        this.inputVal = inputVal;
        this.inputNameField = inputNameField;
        this.expected = expected;
    }

   @Parameterized.Parameters
   public static Object[][] getAnswerScooterOrder() { // это метод, который возвращает массив [][]
        return new Object[][]{
                {"* Имя", "","Введите корректное имя"},
                {"* Имя", "Имя",""},
                {"* Фамилия","","Введите корректную фамилию"},
                {"* Фамилия","Фамилия",""},
                {"* Адрес: куда привезти заказ","Aдре","Введите корректный адрес"},
                {"* Адрес: куда привезти заказ","Адрес",""},
                {"* Станция метро","Сокольники",""},
                {"* Станция метро","","Выберите станцию"},
                {"* Телефон: на него позвонит курьер","","Введите корректный номер"},
                {"* Телефон: на него позвонит курьер","89876543210",""},
               };
    }


    @Test
    public void SuccessOrder(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver();
        //страница главная
        driver.get("https://qa-scooter.praktikum-services.ru/");
        //кнопка Заказать вверху
        driver.findElement(By.className("Button_Button__ra12g")).click();//Кнопка Заказать верхняя
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("Order_Header__BZXOb")));//ожидаем заголовка Для кого самокат

        FirstPageOfOrderScooterForm order = new FirstPageOfOrderScooterForm(driver);
        order.OrderFormMenu(inputVal, inputNameField);
        order.takeOrder();
        String actual = order.OrderPageAnswer();
        assertEquals(expected, actual);

    }

   @Test
    public void SuccessOrder1(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //главная страница
        driver.get("https://qa-scooter.praktikum-services.ru/");
        //спуститься вниз до кнопки Заказа
       JavascriptExecutor js = (JavascriptExecutor) driver;
       js.executeScript("window.scrollBy(0,2500)");
       //кликнуть по кнопке заказа внизу
       driver.findElement(By.className("Button_Middle__1CSJM")).click();
       new WebDriverWait(driver, Duration.ofSeconds(15))
               .until(ExpectedConditions.visibilityOfElementLocated(By.className("Button_Middle__1CSJM")));//подождать загрузки страницы заказа

        FirstPageOfOrderScooterForm order = new FirstPageOfOrderScooterForm(driver);
        order.OrderFormMenu(inputVal, inputNameField);
        order.takeOrder();
        String actual = order.OrderPageAnswer();
        assertEquals(expected, actual);
        }


    @After
    public void tearDown() {
        // Закрой браузер
        driver.quit();
    }
}



