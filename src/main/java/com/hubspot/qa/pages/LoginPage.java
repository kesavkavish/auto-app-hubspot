package com.hubspot.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hubspot.qa.testbase.TestBase;
import com.hubspot.qa.util.TestUtil;

public class LoginPage extends TestBase {
	
	@FindBy(xpath="//div[@class='auth-box marketing-box']")
	WebElement authBox;
	
	@FindBy(xpath="//input[@id='username']")
	WebElement username;
	
	@FindBy(xpath="//input[@id='password']")
	WebElement password;
	
	@FindBy(xpath="//button[@id='loginBtn']")
	WebElement btnLogin;
	
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void verifyLoginPageDisplayed() {
		TestUtil.waitForElement(driver, authBox, 8);
	}
	
	public String verifyLoginPageTitle() {
		return driver.getTitle();
	}
	
	public void verifyHubspotLogin(String user, String pass) throws Exception {
		TestUtil.sendKeys(driver, username, 5, user);
		TestUtil.sendKeys(driver, password, 5, pass);
		TestUtil.clickOn(driver, btnLogin, 5);
	}

}
