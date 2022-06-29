package com.qaprosoft.carina.demo.gui.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends AbstractPage {

    @FindBy(xpath = "//fieldset/input[@id='uname']")
    private ExtendedWebElement nicknameField;

    @FindBy(xpath = "//fieldset/input[@id='email']")
    private ExtendedWebElement emailField;

    @FindBy(xpath = "//div/form/input[@id='upass']")
    private ExtendedWebElement passwordField;

    @FindBy(xpath = "//label[@for='iagree1']")
    private ExtendedWebElement agreeCheckbox;

    @FindBy(xpath = "//label[@for='iagree2']")
    private ExtendedWebElement ageCheckbox;

    @FindBy(xpath = "//div[@id='ucsubmit-f']/input[@id='nick-submit']")
    private ExtendedWebElement submitButton;

    @FindBy(xpath = "//div[contains(@class, 'normal-text')]/h3")
    private ExtendedWebElement registerResultText;

    public RegisterPage(WebDriver driver) {
        super(driver);
        setPageURL("/register.php3");
    }

    public boolean isNicknameFieldPresent() {
        return nicknameField.isPresent(2);
    }

    public boolean isEmailFieldPresent() {
        return emailField.isPresent(2);
    }

    public boolean isPasswordFieldPresent() {
        return passwordField.isPresent(2);
    }

    public boolean isAgreeCheckboxPresent() {
        return ageCheckbox.isPresent(2);
    }

    public boolean isAgeCheckboxPresent() {
        return ageCheckbox.isPresent(2);
    }

    public boolean isSubmitButtonPresent() {
        return submitButton.isPresent(2);
    }

    public void typeNickname(String nickname) {
        nicknameField.type(nickname);
    }

    public void typeEmail(String email) {
        emailField.type(email);
    }

    public void typePassword(String password) {
        passwordField.type(password);
    }

    public void checkAgreeCheckbox() {
        agreeCheckbox.check();
    }

    public void checkAgeCheckbox() {
        ageCheckbox.check();
    }

    public RegisterPage clickSubmit() {
        submitButton.isClickable();
        submitButton.click();
        return new RegisterPage(driver);
    }

    public String readRegisterStatus() {
        assertElementPresent(registerResultText);
        return registerResultText.getText();
    }

    public RegisterPage register(String nickname, String email, String password) {
        typeNickname(nickname);
        typeEmail(email);
        typePassword(password);
        checkAgreeCheckbox();
        checkAgeCheckbox();
        return clickSubmit();
    }
}
