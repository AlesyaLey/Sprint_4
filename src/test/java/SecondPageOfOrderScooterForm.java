import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class SecondPageOfOrderScooterForm {
    private WebDriver driver;
    public SecondPageOfOrderScooterForm(WebDriver driver){
        this.driver = driver;
    }
    // Input "Далее"
    private By nextList = By.className("Order_NextButton__1_rCA");
    // Input "Когда привести самокат"
    private By inputDate = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    // Input Когда "Срок аренды"
    private By clickArroyPeriod = By.xpath(".//div[@class='Dropdown-placeholder']");
    // Listbox "Сутки"
    private By OneDay = By.xpath(".//div[@class='Dropdown-option'][1]");
    // Listbox "Двое суток"
    private By TwoDay = By.xpath(".//div[@class='Dropdown-menu']/div[2]");
    //  Заголовок "Про аренду"
    private By headOfSamokat = By.xpath(".//div[@class='Order_Header__BZXOb']");
    // Чекбокс "Черный жемчуг"
    private By Black = By.id("black");
    // Чекбокс "Серая безысходность"
    private By Gray = By.id("grey");
    // Input "Комментарий курьеру"
    private By commentCourier = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    // Кнопка внизу "Заказать"
    private By buttonOrderMiddle = By.xpath("/html/body/div/div/div[2]/div[3]/button[2]");
    // Кнопка "Да" в подтверждении заказа
    private By yesButtonConfirmButton = By.xpath(".//div[@class='Order_Buttons__1xGrp']/button[text() ='Да']");
    // Кнопка "Посмотреть статус"
    protected By buttonViewOrder = By.xpath("/html/body/div/div/div[2]/div[5]/div[2]/button");

    public void clickButtonNext() {
        driver.findElement(nextList).click();
    }
    public void insertDates (String text) {
        driver.findElement(inputDate).sendKeys(text);
    }
    public void clickArroy () {
        driver.findElement(clickArroyPeriod).click();
    }
    public void clickOneDay () {
        driver.findElement(OneDay).click();
    }
    public void clickTwoDay() {
        driver.findElement(TwoDay).click();
    }
    public void clickEmptySpace() {
        driver.findElement(headOfSamokat).click();
    }
    public void clickBlack() {
        driver.findElement(Black).click();
    }
    public void clickGray() {
        driver.findElement(Gray).click();
    }
    public void clickButtonOrderMiddle() {
        driver.findElement(buttonOrderMiddle).click();
    }
    public void clickYesButtonConfirmOrder() {
        driver.findElement(yesButtonConfirmButton).click();
    }
    public boolean displayingButtonViewOrder () {
        return driver.findElement(buttonViewOrder).isDisplayed();
    }
    public void writeCommentCourier(String text) {driver.findElement(commentCourier).sendKeys(text);
    }
}
