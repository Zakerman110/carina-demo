package com.qaprosoft.carina.demo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.carina.demo.gui.pages.RegisterPage;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class HeaderMenu extends AbstractUIObject {

    @FindBy(xpath = "//a[@id='login-active']")
    private ExtendedWebElement loginButton;

    @FindBy(xpath = "//a[contains(@class, 'signup-icon')]")
    private ExtendedWebElement signupButton;

    public HeaderMenu(WebDriver driver) {
        super(driver);
    }

    public HeaderMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public boolean isLoginButtonPresent() {
        return loginButton.isPresent(2);
    }

    public boolean isSignupButtonPresent() {
        return signupButton.isPresent(2);
    }

    public LoginComponent clickLoginComponent() {
        loginButton.click();
        return new LoginComponent(driver);
    }

    public RegisterPage openRegisterPage() {
        signupButton.click();
        return new RegisterPage(driver);
    }
}
