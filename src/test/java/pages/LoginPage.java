package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// Page Object Class for the Login Page
public class LoginPage {

    WebDriver driver;

    // Constructor: Initializes the driver and all @FindBy elements on this page
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Page Locators (Using PageFactory pattern)
    @FindBy(id = "username")
    WebElement usernameField;

    @FindBy(id = "password")
    WebElement passwordField;

    @FindBy(id = "login-btn")
    WebElement loginButton;

    @FindBy(css = ".alert.error")
    WebElement errorMessage;

    // Page Actions/Methods - what a user can do on this page
    public void enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    // A helper method to perform the entire login action
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // A method to check if we are on the login page
    public boolean isOnPage() {
        return driver.getCurrentUrl().contains("login");
    }
}
