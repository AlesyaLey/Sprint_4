import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;
import java.awt.*;
import java.time.Duration;

@RunWith(Parameterized.class)
public class Scooter {

    private WebDriver driver;
    private int numberRow;//ноемр строки
    private String expected;//ожидаемый результат

    public Scooter(int numberRow, String expected){
        this.numberRow = numberRow;
        this.expected = expected;
    }
    @Parameterized.Parameters
    public static Object[][] getAnswer() { // это метод, который возвращает массив [][]
        return new Object[][] {
                { 0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                { 1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                { 2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                { 3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                { 4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                { 5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                { 6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                { 7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        };
    }

    @Test
    public void checkFAQ() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");
        driver.findElement(By.className("App_CookieButton__3cvqF")).click();

        MainPageScooter page = new MainPageScooter(driver);
        page.waitForLoadHeader();
        page.titleInHeader();

        driver.findElement(By.id("accordion__heading-0")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");//скролл страницы

        FAQ some = new FAQ(driver);
        some.clickOpenListButton(numberRow);
        Thread.sleep(1000);
        String actual = some.checkListAnswer(numberRow);
        assertEquals(expected,actual);
    }

    @After
    public void tearDown() {
        // Закрой браузер
        driver.quit();
    }
}

     class MainPageScooter { //главная страница
         //проверяем, что страница загружена
         //кнопка заказа вписать сюда
         private WebDriver driver;
         private By homeHeader = By.className("Home_Header__iJKdX");//заголовок страницы
         private By ButtonOrderUp = By.className("Button_Button__ra12g");
         private By ButtonOderDown = By.className("Button_Middle__1CSJM");

         public MainPageScooter(WebDriver driver){
             this.driver = driver;
         }
         // метод ожидания загрузки страницы, проверяем, что тсраница загружена
         public void waitForLoadHeader(){
             new WebDriverWait(driver, Duration.ofSeconds(15))
                     .until(ExpectedConditions.visibilityOfElementLocated(homeHeader));
         }
         // метод для получения текста элемента в заголовке
         public String titleInHeader(){
             return driver.findElement(homeHeader).getText();
         }


    }


    class FAQ{ //класс по проверке ответов
        private WebDriver driver;

        public FAQ(WebDriver driver){this.driver = driver;}

        public void clickOpenListButton(int numberRow) {

            By listQuestions = By.xpath(".//div[@id='accordion__heading-"+numberRow+"']");
            driver.findElement(listQuestions).click();
        }

            public String checkListAnswer(int numberRow){

               By listOfAnswer = By.xpath(".//div[@id='accordion__panel-" + numberRow + "']");
               String text = driver.findElement(listOfAnswer).getText();
                return text;
            }

            /*public String checkFAQ(int numberRow)
            {
                clickOpenListButton(numberRow);
                checkListAnswer(numberRow);
            }*/

           /* switch (numberRow) {
                case (1):
                   driver.findElement(By.id("accordion__heading-0")).click();
                   return text = driver.findElement(By.xpath(".//div[@id='accordion__panel-0']")).getText();

                case (2):
                   driver.findElements(By.id("accordion__heading-1"));
                     text = driver.findElement(By.xpath(".//div[@id='accordion__panel-1']")).getText();
                    break;
               case (3):
                    driver.findElements(By.id("accordion__heading-2"));
                     text = driver.findElement(By.xpath(".//div[@id='accordion__panel-2']")).getText();
                   break;
               case (4):
                    driver.findElements(By.id("accordion__heading-3"));
                     text = driver.findElement(By.xpath(".//div[@id='accordion__panel-3']")).getText();
                   break;
               case (5):
                    driver.findElements(By.id("accordion__heading-4"));
                     text = driver.findElement(By.xpath(".//div[@id='accordion__panel-4']")).getText();
                   break;
                case (6):
                    driver.findElements(By.id("accordion__heading-5"));
                     text = driver.findElement(By.xpath(".//div[@id='accordion__panel-5']")).getText();
                    break;
                case (7):
                    driver.findElements(By.id("accordion__heading-6"));
                     text = driver.findElement(By.xpath(".//div[@id='accordion__panel-6']")).getText();
                    break;
                case (8):
                    driver.findElements(By.id("accordion__heading-7"));
                     text = driver.findElement(By.xpath(".//div[@id='accordion__panel-7']")).getText();
                    break;
               default:
                    text = "Error";
            }
            return text;

        }*/
    }
