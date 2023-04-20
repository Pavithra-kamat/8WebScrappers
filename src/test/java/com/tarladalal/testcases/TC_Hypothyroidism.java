package com.tarladalal.testcases;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.tarladala.utilities.ExcelReader;
import com.tarladala.utilities.Loggerload;

public class TC_Hypothyroidism extends BaseClass {
	ExcelReader read;

	@Test(priority = 1)
	public void browserlaunch() throws InterruptedException, IOException {
		driver.get(baseURL);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		// Thread.sleep(1000);
		if (driver.getTitle().equals("Indian Recipes | Indian Vegetarian Recipes | Top Indian Veg Dishes")) {
			Assert.assertTrue(true);
			Loggerload.info("Successfully Launched the website");
		} else {
			captureScreeshot(driver, "browserlaunch");
			Assert.assertTrue(false);
			Loggerload.info("Failed to Launch the website");
		}
	}

	@SuppressWarnings("null")
	@Test(priority = 2)
	public void getRecipeDetails() throws InterruptedException {
		WebElement searchtxt;
		searchtxt = driver.findElement(By.xpath("//div[@id='search']/input[@id='ctl00_txtsearch']"));
		searchtxt.click();
		// Thread.sleep(1000);
		searchtxt.sendKeys("Diabetes Recipe");
		WebElement submitbtn = driver.findElement(By.name("ctl00$imgsearch"));
		submitbtn.click();

	}

	@Test(priority = 3)
	public void captureDatainsheet() throws IOException {
		String excelpath=System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\8WebScrappersdiabeticdata.xlsx";
		read=new ExcelReader(excelpath);
		Loggerload.info("Read Excel file");
		read.setcelldata("DiabeticbreakfastRecipe", 0, 0, "RecipeId");
		read.setcelldata("DiabeticbreakfastRecipe", 0, 1, "Recipename");
		int pagescount=driver.findElements(By.xpath("//div[@id='maincontent']/div/div/div[3]/a")).size();
		Loggerload.info("pagecount" +pagescount);
		int cardcount=driver.findElements(By.xpath("//div[@class='rcc_recipecard']")).size();
		Loggerload.info("cardcount" +cardcount);
		List<String> lst = new ArrayList<String>();
		List<String> lstforid = new ArrayList<String>();
		for(int j=1; j<pagescount; j++) {
		for(int i=1; i<=cardcount; i++) {
			 WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
			   wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rcc_recipecard'][\"+i+\"]//div[2]/span")));
			   lstforid.add(driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//div[2]/span")).getText());	   
			 WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
			   wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")));
		lst.add(driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).getText());	
		}
		driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a["+j+"]")).click();
		
	}
		System.out.println(lst.size());
		System.out.println(lstforid.size());
		  for(int k=1; k<lst.size(); k++) {
			    read.setcelldata("DiabeticbreakfastRecipe", k, 0, lstforid.get(k));
			   read.setcelldata("DiabeticbreakfastRecipe", k, 1, lst.get(k));
		   }
		  Loggerload.info("Succfully read all data");

}

}
