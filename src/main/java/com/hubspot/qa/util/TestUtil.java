package com.hubspot.qa.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hubspot.qa.testbase.TestBase;

public class TestUtil extends TestBase {
	
	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 10;
	
	public static void sendKeys(WebDriver driver, WebElement element, int time, String value) {
		waitForElement(driver, element, time);
		element.sendKeys(value);
	}
	
	public static void clickOn(WebDriver driver, WebElement element, int time) {
		new WebDriverWait(driver, time).until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
	
	public static void waitForElement(WebDriver driver, WebElement element, int time) {
		new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOf(element));
	}
	
	public static String getScreenshot(WebDriver driver, String imageName) {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		Date d = new Date();
		String timestamp = s.format(d);
		String destination = System.getProperty("user.dir")+"/screenshots/"+imageName+"_"+timestamp+".png";
		File destFile = new File(destination);
		try {
			FileHandler.copy(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destination;
	}

}
