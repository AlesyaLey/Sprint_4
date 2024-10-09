import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageScooter { //главная страница
         //проверяем, что страница загружена
         //кнопка заказа вписать сюда
         private WebDriver driver;
         private By homeHeader = By.className("Home_Header__iJKdX");//заголовок страницы

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
