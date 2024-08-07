import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertEquals;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;

@RunWith(Parameterized.class)
public class ScooterOrder {

    //
    private WebDriver driver;
    //переменная для значения в поле
    private String inputVal;
    //ожидаемый результат
    private String expected;
    private String buttonOrder;
    //переменная для названия поля
    private String inputNameField; //private SuccessOrder ;

    public ScooterOrder (String inputVal,String inputNameField, String expected) { //
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
                {"* Адрес: куда привезти заказ","","Введите корректный адрес"},
                {"* Адрес: куда привезти заказ","","Введите корректный адрес"},
                {"* Адрес: куда привезти заказ","Адрес",""},
                {"* Станция метро","Сокольники",""},
                {"* Станция метро","","Выберите станцию"},
                {"* Телефон: на него позвонит курьер","","Введите корректный номер"},
                {"* Телефон: на него позвонит курьер","8-987-654-32-10",""},
               };
    }


    @Test
    public void SuccessOrder(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver();
        //страница главная
        driver.get("https://qa-scooter.praktikum-services.ru/");
        //кнопка Заказать вверху
        driver.findElement(By.className("Button_Button__ra12g")).click();
        //driver.findElement(By.className("Button_Button__ra12g Button_Middle__1CSJM")).click();
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("Order_Header__BZXOb")));

        OrderScooterForm order = new OrderScooterForm(driver);
        order.OrderFormMenu(inputVal, inputNameField);
        order.takeOrder();
        String actual = order.OrderPageAnswer();
        assertEquals(expected, actual);

    }

   @Test
    public void SuccessOrder1(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
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

        OrderScooterForm order = new OrderScooterForm(driver);
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

class Field {//класс с наименованием полей и значений для них
    public String placeholder;
    public String val;

    public Field(String placeholder, String val) {
        this.placeholder = placeholder;
        this.val = val;
    }
}

class SuccessOrder {
    public SuccessOrder() {
        String placeholder;
        String val;
    }
}

class OrderScooterForm {//поиск поля на форме и заполнение полей

    private WebDriver driver;

    private By nameField;//наименование поля
    private String xpath_sstr;//путь для поля
    private By nextActionButton = By.className("Button_Middle__1CSJM");//кнопка заказа
    public OrderScooterForm(WebDriver driver){this.driver = driver;}

    public void OrderFormMenu(String inputVal, String inputNameField){//заполнение поля
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("select-search__value")));
            //находим поля по имени
            xpath_sstr = "//input[@placeholder='" + inputVal + "']";
            nameField = By.xpath(xpath_sstr);

            WebElement el = driver.findElement(nameField);

           String cn = el.getAttribute("class");
           //отбираем поля с выпадающим списком
            if(cn.equals("select-search__input")){
                if(inputNameField==""){takeOrder();}else{

                el.sendKeys(inputNameField);
                //System.out.println(driver.findElement(By.className("select-search__select")).getAttribute("innerHTML"))
                //кликнуть по выпадающему списку
                driver.findElement(By.className("Order_Text__2broi")).click();

                }
            }else{
                el.click();
                el.sendKeys(inputNameField);}
    }

    public String OrderPageAnswer(){//ответ при ошибке
        WebElement c = driver.findElement(nameField);
        WebElement p = c.findElement(By.xpath("./.."));// находит родителя

        String cn = p.getAttribute("class"); //берет название класса
        String answer = "";


            if(cn.equals("select-search__value")){//отдельный поиск родителя для вложенного списка

                driver.findElement(nextActionButton).click();
                p = p.findElement(By.xpath("./.."));// находит родителя
                p = p.findElement(By.xpath("./.."));// находит родителя
                List<WebElement> divs = p.findElements(By.xpath("div"));//берет дете(потомка)

                if (divs.size()==2){
                answer = divs.get(1).getText();}//получаем текст
       } else {
            WebElement d = p.findElement(By.xpath("div"));//берет дете
            answer = d.getText();//получаем текст
            }
        return answer;
    }
    public void takeOrder(){//кликнуть на Заказать
        driver.findElement(nextActionButton).click();
    }

}