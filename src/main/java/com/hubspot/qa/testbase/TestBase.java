package com.hubspot.qa.testbase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.hubspot.qa.util.TestUtil;

public class TestBase {
	public static Properties prop;
	public static WebDriver driver=null;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports reports;
	public static ExtentTest test;
	
	public TestBase() { 
		try {
			prop = new Properties();
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/hubspot/qa/config/config.properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void initialization() {
		String browserName = prop.getProperty("browser");
		if(browserName.equalsIgnoreCase("chrome")) {
			if(driver==null) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chrome/chromedriver.exe");
				driver = new ChromeDriver();
			}
		} else if(browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/drivers/firefox/geckodriver.exe");
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
		driver.get(prop.getProperty("appurl"));
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
	}
	
	@BeforeSuite
	public void setUp() {
		initialization();
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/Automation_Report/Statusreport.html");
		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("Automation Execution Status Report");
		htmlReporter.config().setTheme(Theme.STANDARD);
		reports = new ExtentReports();
		reports.attachReporter(htmlReporter);
		reports.setSystemInfo("Username", "Kavish");
		reports.setSystemInfo("Host", "Localhost");
		reports.setSystemInfo("Operating System", "Windows 10");
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) {
		if(result.getStatus()==ITestResult.FAILURE) {
			String screenshotpath = TestUtil.getScreenshot(driver, result.getName());
			try {
				test.fail("TEST CASE IS FAILED " + result.getName());
				test.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(result.getStatus()==ITestResult.SKIP) {
			test.skip("TEST CASE IS SKIPPED "+ result.getName());
		}
	}
	
	@AfterSuite
	public void tearDown() {
		reports.flush();
		driver.quit();
		driver=null;
	}

}

