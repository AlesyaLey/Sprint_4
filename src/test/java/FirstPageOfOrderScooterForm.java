import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FirstPageOfOrderScooterForm {//поиск поля на форме и заполнение полей

    private WebDriver driver;

    private By nameField;//наименование поля
    private String xpath_sstr;//путь для поля
    private By nextActionButton = By.className("Button_Middle__1CSJM");//кнопка Заказа нижняя

    public FirstPageOfOrderScooterForm(WebDriver driver){
        this.driver = driver;
    }

    public void OrderFormMenu(String inputVal, String inputNameField){//заполнение поля
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("select-search__value")));
            //находим поля по имени, отображающеся в самом поле
            xpath_sstr = "//input[@placeholder='" + inputVal + "']";
            nameField = By.xpath(xpath_sstr);

            WebElement el = driver.findElement(nameField);

           String cn = el.getAttribute("class");
           //отбираем поля с выпадающим списком с адресами станций метро
            if(cn.equals("select-search__input")){
                if(inputNameField==""){
                    takeOrder();
                }
                else {
                el.sendKeys(inputNameField);
                //System.out.println(driver.findElement(By.className("select-search__select")).getAttribute("innerHTML"))
                //кликнуть по выпадающему списку
                driver.findElement(By.className("Order_Text__2broi")).click();
                }
            }
            else {
                el.click();
                el.sendKeys(inputNameField);
            }
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
