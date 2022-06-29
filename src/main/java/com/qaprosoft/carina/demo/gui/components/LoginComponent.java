package com.qaprosoft.carina.demo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.carina.demo.gui.pages.HomePage;
import com.qaprosoft.carina.demo.gui.pages.LoginRedirectPage;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;

public class LoginComponent extends AbstractUIObject {

    @FindBy(xpath="//span[@id='login-popup2']/form/p")
    private ExtendedWebElement loginTitle;

    @FindBy(xpath="//input[@id='email']")
    private ExtendedWebElement emailField;

    @FindBy(xpath="//input[@id='upass']")
    private ExtendedWebElement passwordField;

    @FindBy(xpath="//input[@id='nick-submit']")
    private ExtendedWebElement loginButton;

    @FindBy(xpath="//span[@id='login-popup2']/a")
    private ExtendedWebElement forgotLink;

    public LoginComponent(WebDriver driver) {
        super(driver);
    }

    public LoginComponent(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public boolean isLoginTitlePresent() {
        return loginTitle.isPresent(2);
    }

    public boolean isEmailFieldPresent() {
        return emailField.isPresent(2);
    }

    public boolean isPasswordFieldPresent() {
        return passwordField.isPresent(2);
    }

    public boolean isLoginButtonPresent() {
        return loginButton.isPresent(2);
    }

    public boolean isForgotLinkPresent() {
        return forgotLink.isPresent(2);
    }

    public void enterEmail(String email) {
        emailField.type(email);
    }

    public void enterPassword(String password) {
        passwordField.type(password);
    }

    public void hoverLoginButton() {
        loginButton.hover();
    }

    public String getLoginButtonBackgroundColor() {
        pause(1);
        String rgb = loginButton.getElement().getCssValue("background-color");
        return Color.fromString(rgb).asHex();
    }

    public String getEmailFieldValidationMessage() {
        return emailField.getElement().getAttribute("validationMessage");
    }

    public String getPasswordFieldValidationMessage() {
        return passwordField.getElement().getAttribute("validationMessage");
    }

    public LoginRedirectPage clickLogin() {
        loginButton.click();
        return new LoginRedirectPage(driver);
    }

    public LoginRedirectPage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        return clickLogin();
    }
}
