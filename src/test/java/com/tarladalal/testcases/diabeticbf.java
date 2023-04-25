package com.tarladalal.testcases;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.tarladala.utilities.ConfigReader;
import com.tarladala.utilities.ExcelReader;
import com.tarladala.utilities.Loggerload;

public class diabeticbf extends BaseClass{
	
	
	ExcelReader read;
	ConfigReader con;
	
	
	public void clicksearch() {
		
		driver.findElement(By.id("ctl00_imgsearch")).click();
	}
	
	public void diabeticbfstveg() {
		Loggerload.info("The diabetic breakfast vegetarian is searched");
		WebElement search2=driver.findElement(By.id("ctl00_txtsearch"));
		search2.clear();
		search2.sendKeys("diabetic vegetarian breakfast");
		clicksearch();
	}
	
	
	@Test(priority=1)
	
	public void recipeidandname() throws IOException {
		
		Loggerload.info("All diabetic breakfast recipes are captured in the excel sheet");
		diabeticbfstveg();
		con=new ConfigReader();
		//Gettersetter get = new Gettersetter();
	   String excelpath="C:\\Users\\amitf\\eclipse-workspace\\Jobinterview\\Recipescrapinghac\\src\\test\\resources\\TestData\\webscrapping.xlsx";
	   read=new ExcelReader(excelpath);
	   read.setcelldata("Sheet1", 0, 0, "RecipeId");
	   read.setcelldata("Sheet1", 0, 1, "Recipename");
		int pagescount=driver.findElements(By.xpath("//div[@id='maincontent']/div/div/div[3]/a")).size();
		System.out.println("pagecount" +pagescount);
		int cardcount=driver.findElements(By.xpath("//div[@class='rcc_recipecard']")).size();
		System.out.println("cardcount" +cardcount);
		List<String> recipename = new ArrayList<String>();
		List<String> recipeid = new ArrayList<String>();
		for(int j=1; j<=pagescount; j++) {
		for(int i=1; i<=cardcount; i++) {
			 WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
			  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rcc_recipecard']["+i+"]//div[2]/span")));
			  String recipeidwithdate=driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//div[2]/span")).getText();
			  String formattedrecipeid = recipeidwithdate.substring(8, recipeidwithdate.length() - 9);
			  recipeid.add(formattedrecipeid);
			 WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
			  wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")));
			  recipename.add(driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).getText());	
		}
		driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a["+j+"]")).click();
		
	}
	System.out.println(recipename);
	System.out.println(recipename.size());
		  for(int k=0; k<recipename.size(); k++) {
			    read.setcelldata("Sheet1", k+1, 0, recipeid.get(k));
			   read.setcelldata("Sheet1", k+1, 1, recipename.get(k));
			   }
		
			 
		 
}
		
	
	@Test(priority=2)
	public void ingreprepandcookingtime() throws IOException {
		diabeticbfstveg();
	//	con=new ConFigreader();
		   String excelpath="C:\\Users\\amitf\\eclipse-workspace\\Jobinterview\\Recipescrapinghac\\src\\test\\resources\\TestData\\webscrapping.xlsx";
		//	read=new Excelreader(excelpath);
		read.setcelldata("Sheet1", 0, 2, "Ingredients");
		read.setcelldata("Sheet1", 0, 3, "PreparationTime");
		read.setcelldata("Sheet1", 0, 4, "CookingTime");
		int pagescount=driver.findElements(By.xpath("//div[@id='maincontent']/div/div/div[3]/a")).size();
		System.out.println("pagecount" +pagescount);
		int cardcount=driver.findElements(By.xpath("//div[@class='rcc_recipecard']")).size();
		System.out.println("cardcount" +cardcount);
		List<Object> prptime= new ArrayList<Object>();
		List<Object> cooktime= new ArrayList<Object>();
		for(int j=1; j<=pagescount; j++) {
			for(int i=1; i<=cardcount; i++) {
				 WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(30));
				 wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")));
				  driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).click();
			       WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(30));
				   wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[2]//time[@itemprop='prepTime']")));
				   WebElement preptimeelem= driver.findElement(By.xpath("//p[2]//time[@itemprop='prepTime']"));
				   if(!preptimeelem.isDisplayed()) {
					   System.out.println("Prepration time is not present");
					     } else {
				   Object prep= driver.findElement(By.xpath("//p[2]//time[@itemprop='prepTime']")).getText();
				   try {
				   if(prep.equals(null)) {
					   System.out.println("Prep time is null");
				   } else
					   prptime.add(driver.findElement(By.xpath("//p[2]//time[@itemprop='prepTime']")).getText());
				   } catch (NullPointerException e) {
					   System.out.println("Null");
				   }
				   }
				 WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(30));
				 wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[2]//time[2][@itemprop='cookTime']")));
				  WebElement cooktimelem= driver.findElement(By.xpath("//p[2]//time[2][@itemprop='cookTime']"));
				  if(!cooktimelem.isDisplayed()) {
					   System.out.println("Cook time is not present");
					     } else {
				 Object cook= driver.findElement(By.xpath("//p[2]//time[2][@itemprop='cookTime']")).getText();
				 try {
					   if(cook.equals(null)) {
						   System.out.println("Cook time is null");
					   } else
						   cooktime.add(driver.findElement(By.xpath("//p[2]//time[2][@itemprop='cookTime']")).getText());	
					   } catch (NullPointerException e) {
						   System.out.println("Null");
					   }
					     }
				driver.navigate().back();	
			}
			driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a["+j+"]")).click();
		}
		System.out.println("prep" +prptime.size());
		for(int l=0; l<prptime.size(); l++) {
		    read.setcelldata("Sheet1", l+1, 3, prptime.get(l).toString());
		     }
		System.out.println("cook" +cooktime.size());
	  for(int m=0; m<cooktime.size(); m++) {
		    read.setcelldata("Sheet1", m+1, 4, cooktime.get(m).toString());
		     }
	}
//}
		
		
		
		@Test(priority=3)
		public void ingredients() throws IOException {
			diabeticbfstveg();
		//	con=new ConFigreader();
			String excelpath="C:\\Users\\amitf\\eclipse-workspace\\Jobinterview\\Recipescrapinghac\\src\\test\\resources\\TestData\\webscrapping.xlsx";
			//read=new Excelreader(excelpath);
			read.setcelldata("Sheet1", 0, 2, "Ingredients");
			int pagescount=driver.findElements(By.xpath("//div[@id='maincontent']/div/div/div[3]/a")).size();
			System.out.println("pagecount" +pagescount);
			int cardcount=driver.findElements(By.xpath("//div[@class='rcc_recipecard']")).size();
			System.out.println("cardcount" +cardcount);
			List<Object> ingrelist= new ArrayList<Object>();
			for(int j=1; j<=2; j++) {
				for(int i=1; i<=cardcount; i++) {
					 WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(30));
					 wait6.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")));
					  driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).click();
					  ingrelist.add( driver.findElement(By.xpath("//div[@id='rcpinglist']")).getText());
				driver.navigate().back();	
			}
			driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a["+j+"]")).click();
		}
			 for(int m=0; m<ingrelist.size(); m++) {
				    read.setcelldata("Sheet1", m+1, 2, ingrelist.get(m).toString());
				     }
			}
		
@Test(priority=4)
public void prepmethod() throws IOException {
	diabeticbfstveg();
	//con=new ConFigreader();
	String excelpath="C:\\Users\\amitf\\eclipse-workspace\\Jobinterview\\Recipescrapinghac\\src\\test\\resources\\TestData\\webscrapping.xlsx";
//	read=new Excelreader(excelpath);
	read.setcelldata("Sheet1", 0, 6, "PreparationMethod");
	int pagescount=driver.findElements(By.xpath("//div[@id='maincontent']/div/div/div[3]/a")).size();
	System.out.println("pagecount" +pagescount);
	int cardcount=driver.findElements(By.xpath("//div[@class='rcc_recipecard']")).size();
	System.out.println("cardcount" +cardcount);
	List<Object> prepmethod= new ArrayList<Object>();
	for(int j=1; j<=2; j++) {
		for(int i=1; i<=cardcount; i++) {
			 WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(30));
			 wait6.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")));
			  driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).click();
			  prepmethod.add( driver.findElement(By.xpath("//div[@id='recipe_small_steps']//ol")).getText());
		driver.navigate().back();	
	}
	driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a["+j+"]")).click();
}
	 for(int prep=0; prep<prepmethod.size(); prep++) {
		    read.setcelldata("Sheet1", prep+1, 6, prepmethod.get(prep).toString());
		     }
	}
@Test
public void recipeurl() throws IOException {
	diabeticbfstveg();
//	con=new ConFigreader();
	String excelpath="C:\\Users\\amitf\\eclipse-workspace\\Jobinterview\\Recipescrapinghac\\src\\test\\resources\\TestData\\webscrapping.xlsx";
//	read=new Excelreader(excelpath);
	read.setcelldata("Sheet1", 0, 9, "Recipeurl");
	int pagescount=driver.findElements(By.xpath("//div[@id='maincontent']/div/div/div[3]/a")).size();
	System.out.println("pagecount" +pagescount);
	int cardcount=driver.findElements(By.xpath("//div[@class='rcc_recipecard']")).size();
	System.out.println("cardcount" +cardcount);
	List<Object> recipeurl= new ArrayList<Object>();
	for(int j=1; j<=2; j++) {
		for(int i=1; i<=cardcount; i++) {
			 WebDriverWait wait7 = new WebDriverWait(driver, Duration.ofSeconds(30));
			 wait7.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")));
			  driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).click();
			  recipeurl.add(driver.getCurrentUrl());
			  driver.navigate().back();	
		}
		driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a["+j+"]")).click();
}
	 for(int rp=0; rp<recipeurl.size(); rp++) {
		    read.setcelldata("Sheet1", rp+1, 9, recipeurl.get(rp).toString());
		     }
	
}
}