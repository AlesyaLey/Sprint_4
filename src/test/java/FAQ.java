import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FAQ{ //класс по проверке ответов
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

    }
