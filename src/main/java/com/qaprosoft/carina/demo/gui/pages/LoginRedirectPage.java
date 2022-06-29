package com.qaprosoft.carina.demo.gui.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class LoginRedirectPage extends AbstractPage {

    // $x(//div[@class='normal-text']/h3)
    @FindBy(xpath = "//div[contains(@class, 'normal-text')]/h3")
    private ExtendedWebElement loginResultText;

    public LoginRedirectPage(WebDriver driver) {
        super(driver);
        setPageURL("/login.php3");
    }

    public String readLoginStatus() {
        assertElementPresent(loginResultText);
        return loginResultText.getText();
    }
}
