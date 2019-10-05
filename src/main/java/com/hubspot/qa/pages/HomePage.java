package com.hubspot.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hubspot.qa.testbase.TestBase;

public class HomePage extends TestBase {
	
	@FindBy(xpath="//a[@id='nav-primary-contacts-branch']")
	WebElement contactsMenu;
	
	@FindBy(xpath="//a[@id='nav-secondary-contacts']")
	WebElement contactLink;
	
	
	
	public String verifyHomePageTitle() {
		return driver.getTitle();
	}
	
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	

}
