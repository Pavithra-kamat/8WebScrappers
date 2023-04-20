package com.tarladalal.testcases;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.tarladala.utilities.ConfigReader;

import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;
//pranusha creating branch 
public class BaseClass {
 ConfigReader configReader = new ConfigReader();

	public String baseURL = configReader.getApplicationURl();

	public static WebDriver driver;

	@Parameters("browser")
	@BeforeClass
	public void setup(String br) {

		if (br.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			driver = WebDriverManager.chromedriver().capabilities(options).create();
		} else if (br.contains("firefox")) {
			driver = WebDriverManager.firefoxdriver().create();
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
	public void captureScreeshot(WebDriver driver,String tname) throws IOException {
		TakesScreenshot ts=(TakesScreenshot) driver;
		File source=ts.getScreenshotAs(OutputType.FILE);
		File target=new File(System.getProperty("user.dir")+"/Screenshots"+tname+".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");
	}
}
