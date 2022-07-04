/*
 * Copyright 2013-2021 QAPROSOFT (http://qaprosoft.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qaprosoft.carina.demo;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.qaprosoft.carina.demo.gui.components.HeaderMenu;
import com.qaprosoft.carina.demo.gui.components.LoginComponent;
import com.qaprosoft.carina.demo.gui.pages.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.zebrunner.agent.core.annotation.TestLabel;
import com.qaprosoft.carina.core.foundation.utils.ownership.MethodOwner;
import com.qaprosoft.carina.core.foundation.utils.tag.Priority;
import com.qaprosoft.carina.core.foundation.utils.tag.TestPriority;
import com.qaprosoft.carina.demo.gui.components.FooterMenu;
import com.qaprosoft.carina.demo.gui.components.NewsItem;
import com.qaprosoft.carina.demo.gui.components.compare.ModelSpecs;
import com.qaprosoft.carina.demo.gui.components.compare.ModelSpecs.SpecType;

/**
 * This sample shows how create Web test.
 *
 * @author qpsdemo
 */
public class WebSampleTest implements IAbstractTest {
    @Test()
    @MethodOwner(owner = "qpsdemo")
    @TestPriority(Priority.P3)
    @TestLabel(name = "feature", value = {"web", "regression"})
    public void testModelSpecs() {
        // Open GSM Arena home page and verify page is opened
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");
        
        //Closing advertising if it's displayed
        homePage.getWeValuePrivacyAd().closeAdIfPresent();
        
        // Select phone brand
        homePage = new HomePage(getDriver());
        BrandModelsPage productsPage = homePage.selectBrand("Samsung");
        // Select phone model
        ModelInfoPage productInfoPage = productsPage.selectModel("Galaxy A52 5G");
        // Verify phone specifications
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(productInfoPage.readDisplay(), "6.5\"", "Invalid display info!");
        softAssert.assertEquals(productInfoPage.readCamera(), "64MP", "Invalid camera info!");
        softAssert.assertEquals(productInfoPage.readRam(), "6/8GB RAM", "Invalid ram info!");
        softAssert.assertEquals(productInfoPage.readBattery(), "4500mAh", "Invalid battery info!");
        softAssert.assertAll();
    }


    @Test()
    @MethodOwner(owner = "qpsdemo")
    @TestPriority(Priority.P1)
    @TestLabel(name = "feature", value = {"web", "acceptance"})
    public void testCompareModels() {
        // Open GSM Arena home page and verify page is opened
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");
        // Open model compare page
        FooterMenu footerMenu = homePage.getFooterMenu();
        Assert.assertTrue(footerMenu.isUIObjectPresent(2), "Footer menu wasn't found!");
        CompareModelsPage comparePage = footerMenu.openComparePage();
        // Compare 3 models
        List<ModelSpecs> specs = comparePage.compareModels("Samsung Galaxy J3", "Samsung Galaxy J5", "Samsung Galaxy J7 Pro");
        // Verify model announced dates
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(specs.get(0).readSpec(SpecType.ANNOUNCED), "2016, March 31");
        softAssert.assertEquals(specs.get(1).readSpec(SpecType.ANNOUNCED), "2015, June 19");
        softAssert.assertEquals(specs.get(2).readSpec(SpecType.ANNOUNCED), "2017, June");
        softAssert.assertAll();
    }
    
    @Test()
    @MethodOwner(owner = "qpsdemo")
    @TestLabel(name = "feature", value = {"web", "acceptance"})
    public void testNewsSearch() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened!");
        
        NewsPage newsPage = homePage.getFooterMenu().openNewsPage();
        Assert.assertTrue(newsPage.isPageOpened(), "News page is not opened!");
        
        final String searchQ = "iphone";
        List<NewsItem> news = newsPage.searchNews(searchQ);
        Assert.assertFalse(CollectionUtils.isEmpty(news), "News not found!");
        SoftAssert softAssert = new SoftAssert();
        for(NewsItem n : news) {
            System.out.println(n.readTitle());
            softAssert.assertTrue(StringUtils.containsIgnoreCase(n.readTitle(), searchQ),
                    "Invalid search results for " + n.readTitle());
        }
        softAssert.assertAll();
    }

    @Test()
    @MethodOwner(owner = "Artur")
    public void testRegister() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened!");

        HeaderMenu headerMenu = homePage.getHeaderMenu();
        Assert.assertTrue(headerMenu.isUIObjectPresent(2), "Header menu wasn't found!");

        Assert.assertTrue(headerMenu.isSignupButtonPresent(), "Sign Up button wasn't found!");

        RegisterPage registerPage = headerMenu.openRegisterPage();

        // Check if register elements are present
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(registerPage.isNicknameFieldPresent(), "Nickname field wasn't found");
        softAssert.assertTrue(registerPage.isEmailFieldPresent(), "Email field wasn't found");
        softAssert.assertTrue(registerPage.isPasswordFieldPresent(), "Password field wasn't found");
        softAssert.assertTrue(registerPage.isAgreeCheckboxPresent(), "Agree checkbox wasn't found");
        softAssert.assertTrue(registerPage.isAgeCheckboxPresent(), "Age checkbox wasn't found");
        softAssert.assertTrue(registerPage.isSubmitButtonPresent(), "Submit button wasn't found");
        softAssert.assertAll();

        // Register with random
        Random random = new Random();
        int number = random.nextInt( 100000);
        String randomNickname = "artur" + number;
        String randomEmail = "artur" + number + "@gmail.com";

        registerPage = registerPage.register(randomNickname,randomEmail, "artur1337");
        Assert.assertEquals(registerPage.readRegisterStatus(), "Your account was created.", "Register was not successful!");
    }

    @Test(dataProvider = "DP1")
    @MethodOwner(owner = "Artur")
    public void testLoginModal(String email, String validationMessage) {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened!");

        HeaderMenu headerMenu = homePage.getHeaderMenu();
        Assert.assertTrue(headerMenu.isUIObjectPresent(2), "Header menu wasn't found!");

        Assert.assertTrue(headerMenu.isLoginButtonPresent(), "Login button wasn't found!");

        LoginComponent loginComponent = headerMenu.clickLoginComponent();

        // Check if login elements are present
        /*SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(loginComponent.isLoginTitlePresent(), "Login title wasn't found");
        softAssert.assertTrue(loginComponent.isEmailFieldPresent(), "Email field wasn't found");
        softAssert.assertTrue(loginComponent.isPasswordFieldPresent(), "Password field wasn't found");
        softAssert.assertTrue(loginComponent.isLoginButtonPresent(), "Login button wasn't found");
        softAssert.assertTrue(loginComponent.isForgotLinkPresent(), "Forgot link wasn't found");
        softAssert.assertAll();

        // Check if hover color of login button is red(#d50000)
        loginComponent.hoverLoginButton();
        // wait for hover animation
        Assert.assertEquals(loginComponent.getLoginButtonBackgroundColor(), "#d50000",
                "Hover color of login button is not red(#d50000)!");*/

        // Check validation message on empty email field
        loginComponent.enterEmail(email);
        loginComponent.clickLogin();
        Assert.assertEquals(loginComponent.getEmailFieldValidationMessage(), validationMessage,
                "Email field validation message incorrect!");

        // Password validation - incomplete password
        /*loginComponent.enterEmail("asd@gmail.com");
        loginComponent.enterPassword("asd");
        loginComponent.clickLogin();
        Assert.assertEquals(loginComponent.getPasswordFieldValidationMessage(),
                "Введите данные в указанном формате.",
                "Email field validation message incorrect!");

        // Password validation - incomplete password
        loginComponent.enterEmail("asd@gmail.com");
        loginComponent.enterPassword("asdasd");
        LoginRedirectPage loginRedirectPage = loginComponent.clickLogin();
        Assert.assertEquals(loginRedirectPage.readLoginStatus(),
                "Login failed.",
                "Login status wasn't found!");*/

    }

    @DataProvider(parallel = false, name = "DP1")
    public static Object[][] dataprovider()
    {
        return new Object[][] {
                { "", "Заполните это поле." },
                { "asd", "Адрес электронной почты должен содержать символ \"@\". В адресе \"asd\" отсутствует символ \"@\"." },
                { "asd@", "Введите часть адреса после символа \"@\". Адрес \"asd@\" неполный." }
        };
    }

    @Test()
    @MethodOwner(owner = "Artur")
    public void testLogin() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened!");

        HeaderMenu headerMenu = homePage.getHeaderMenu();
        Assert.assertTrue(headerMenu.isUIObjectPresent(2), "Header menu wasn't found!");

        Assert.assertTrue(headerMenu.isLoginButtonPresent(), "Login button wasn't found!");

        LoginComponent loginComponent = headerMenu.clickLoginComponent();

        // Check if login elements are present
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(loginComponent.isLoginTitlePresent(), "Login title wasn't found");
        softAssert.assertTrue(loginComponent.isEmailFieldPresent(), "Email field wasn't found");
        softAssert.assertTrue(loginComponent.isPasswordFieldPresent(), "Password field wasn't found");
        softAssert.assertTrue(loginComponent.isLoginButtonPresent(), "Login button wasn't found");
        softAssert.assertTrue(loginComponent.isForgotLinkPresent(), "Forgot link wasn't found");
        softAssert.assertAll();

        // Login
        LoginRedirectPage loginRedirectPage = loginComponent.login("pohix51095@qqhow.com", "artur1337");
        Assert.assertEquals(loginRedirectPage.readLoginStatus(), "Login successful.", "Login was not successful!");

        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened!");
    }

}
