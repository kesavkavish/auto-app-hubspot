package com.hubspot.qa.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hubspot.qa.pages.LoginPage;
import com.hubspot.qa.testbase.TestBase;

public class LoginPageTest extends TestBase {
	
	@Test(priority=1)
	public void hubspotLoginPageTitleTest() {
		test = reports.createTest("hubspotLoginPageTitleTest");
		new LoginPage().verifyLoginPageDisplayed();
		String loginPageTitle = new LoginPage().verifyLoginPageTitle();
		Assert.assertEquals(loginPageTitle, "HubSpot Login1", "HubSpot login page title is missing");
	}
	
	@Test(priority=2)
	public void hubspotLoginTest() throws Exception {
		test = reports.createTest("hubspotLoginTest");
		new LoginPage().verifyHubspotLogin(prop.getProperty("admin_user"), prop.getProperty("admin_password"));
	}
}
