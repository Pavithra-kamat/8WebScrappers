package com.tarladalal.testcases;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import baseclass.Base;
import baseclass.Listener;
import utils.ConFigreader;
import utils.Excelreader;
import utils.Loggerload;
@Listeners(Listener.class)
public class DiabetesBfst extends Base{
	
	
	Excelreader read;
	ConFigreader con;
	
	
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
		con=new ConFigreader();
		Gettersetter get = new Gettersetter();
	   String excelpath="C:\\Users\\amitf\\eclipse-workspace\\Jobinterview\\Recipescrapinghac\\src\\test\\resources\\TestData\\webscrapping.xlsx";
		read=new Excelreader(excelpath);
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
		con=new ConFigreader();
		   String excelpath="C:\\Users\\amitf\\eclipse-workspace\\Jobinterview\\Recipescrapinghac\\src\\test\\resources\\TestData\\webscrapping.xlsx";
			read=new Excelreader(excelpath);
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
			con=new ConFigreader();
			String excelpath="C:\\Users\\amitf\\eclipse-workspace\\Jobinterview\\Recipescrapinghac\\src\\test\\resources\\TestData\\webscrapping.xlsx";
			read=new Excelreader(excelpath);
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
	con=new ConFigreader();
	String excelpath="C:\\Users\\amitf\\eclipse-workspace\\Jobinterview\\Recipescrapinghac\\src\\test\\resources\\TestData\\webscrapping.xlsx";
	read=new Excelreader(excelpath);
	read.setcelldata("Sheet1", 0, 5, "PreparationMethod");
	int pagescount=driver.findElements(By.xpath("//div[@id='maincontent']/div/div/div[3]/a")).size();
	System.out.println("pagecount" +pagescount);
	int cardcount=driver.findElements(By.xpath("//div[@class='rcc_recipecard']")).size();
	System.out.println("cardcount" +cardcount);
	List<Object> prepmethod= new ArrayList<Object>();
	for(int j=1; j<=5; j++) {
		for(int i=1; i<=cardcount; i++) {
		    try {
			  driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).click();
			  prepmethod.add( driver.findElement(By.xpath("//div[@id='recipe_small_steps']//ol")).getText()); 
		    }catch(Exception e) {
		    	System.out.println(e +e.getMessage());
		    }
		driver.navigate().back();	
	}
	driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a["+j+"]")).click();
}
	 for(int prep=0; prep<prepmethod.size(); prep++) {
		    read.setcelldata("Sheet1", prep+1, 5, prepmethod.get(prep).toString());
		     } 
	}




@Test
public void recipeurl() throws IOException {
	diabeticbfstveg();
	con=new ConFigreader();
	String excelpath="C:\\Users\\amitf\\eclipse-workspace\\Jobinterview\\Recipescrapinghac\\src\\test\\resources\\TestData\\webscrapping.xlsx";
	read=new Excelreader(excelpath);
	read.setcelldata("Sheet1", 0, 8, "Recipeurl");
	int pagescount=driver.findElements(By.xpath("//div[@id='maincontent']/div/div/div[3]/a")).size();
	System.out.println("pagecount" +pagescount);
	int cardcount=driver.findElements(By.xpath("//div[@class='rcc_recipecard']")).size();
	System.out.println("cardcount" +cardcount);
	List<Object>  recipeurl= new ArrayList<Object>();
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
		    read.setcelldata("Sheet1", rp+1, 8, recipeurl.get(rp).toString());
		     } 
	
}

@Test
public void nutrientvalues() throws IOException {
	diabeticbfstveg();
	con=new ConFigreader();
	String excelpath="C:\\Users\\amitf\\eclipse-workspace\\Jobinterview\\Recipescrapinghac\\src\\test\\resources\\TestData\\webscrapping.xlsx";
	read=new Excelreader(excelpath);
	read.setcelldata("Sheet1", 0, 6, "Nutrient");
	read.setcelldata("Sheet1", 0, 7, "Value");
	int pagescount=driver.findElements(By.xpath("//div[@id='maincontent']/div/div/div[3]/a")).size();
	System.out.println("pagecount" +pagescount);
	int cardcount=driver.findElements(By.xpath("//div[@class='rcc_recipecard']")).size();
	System.out.println("cardcount" +cardcount);
	 Map<String, Object> map = new HashMap<String, Object>();
	 List<ArrayList<String>> finalistforkey = new ArrayList<ArrayList<String>>();
		List<ArrayList<Object>> finalistforvalue = new ArrayList<ArrayList<Object>>();
	 List<Object> value = new ArrayList<Object>();
	 List<Object>  recipeurl= new ArrayList<Object>();
	for(int j=1; j<=2; j++) {
		for(int i=1; i<=cardcount; i++) {
			  driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).click();
			  recipeurl.add(driver.getCurrentUrl());
			  int trsize= driver.findElements(By.xpath("//table[@id='rcpnutrients']//tr")).size();
			  for(int tr=1; tr<trsize; tr++) {
				 String k=driver.findElement(By.xpath("//table[@id='rcpnutrients']//tr["+tr+"]/td[1]")).getText();
				  String v=driver.findElement(By.xpath("//table[@id='rcpnutrients']//tr["+tr+"]/td[2]/span")).getText();
				  map.put(k, v); 
			  }	
			  ArrayList<String> key = new ArrayList<String>(map.keySet());
			  ArrayList<Object> valueList = new ArrayList<Object>(map.values());
				
				finalistforkey.add(key);
				finalistforvalue.add(valueList);
				System.out.println(finalistforkey);
				System.out.println(finalistforvalue);
			  driver.navigate().back();	
		}
		driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a["+j+"]")).click();
}
	for(int kv=0; kv<recipeurl.size(); kv++) {
		    	 read.setcelldata("Sheet1", kv+1, 6, finalistforkey.get(kv).toString());
		    	 read.setcelldata("Sheet1", kv+1, 7, finalistforvalue.get(kv).toString());
		    }
		     } 
	
}




//@Test
//public void nutrientvalues() throws IOException {
//	diabeticbfstveg();
//	con=new ConFigreader();
//	String excelpath="C:\\Users\\amitf\\eclipse-workspace\\Jobinterview\\Recipescrapinghac\\src\\test\\resources\\TestData\\webscrapping.xlsx";
//	read=new Excelreader(excelpath);
//	read.setcelldata("Sheet1", 0, 8, "Nutrientvalues");
//	int pagescount=driver.findElements(By.xpath("//div[@id='maincontent']/div/div/div[3]/a")).size();
//	System.out.println("pagecount" +pagescount);
//	int cardcount=driver.findElements(By.xpath("//div[@class='rcc_recipecard']")).size();
//	System.out.println("cardcount" +cardcount);
//	Map<String, Object> recipeurl= new HashMap<String, Object>();
//	for(int j=1; j<=2; j++) {
//		for(int i=1; i<=cardcount; i++) {
//			 WebDriverWait wait7 = new WebDriverWait(driver, Duration.ofSeconds(30));
//			 wait7.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")));
//			  driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).click();
//			  recipeurl.add(driver.getCurrentUrl());
//			  driver.navigate().back();	
//		}
//		driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a["+j+"]")).click();
//}
//	 for(int rp=0; rp<recipeurl.size(); rp++) {
//		    read.setcelldata("Sheet1", rp+1, 9, recipeurl.get(rp).toString());
//		     } 
//	
//}
//}

//		List<String> nv = new ArrayList<String>();

//		List<Object> methods= new ArrayList<Object>();
//		List<Object> nvalues= new ArrayList<Object>();

//		read.setcelldata("Sheet1", 0, 7, "Energy");
//		read.setcelldata("Sheet1", 0, 8, "Protein");
//		read.setcelldata("Sheet1", 0, 9, "Carbohydrates");
//		read.setcelldata("Sheet1", 0, 10, "Fiber");
//		read.setcelldata("Sheet1", 0, 11, "Fat");
//		read.setcelldata("Sheet1", 0, 12, "Cholesterol");
//		read.setcelldata("Sheet1", 0, 13, "Sodium");
////		read.setcelldata("Sheet1", 0, 6, "PreparationMethod");
////		read.setcelldata("Sheet1", 0, 7, "NutrientValues");
////		read.setcelldata("Sheet1", 0, 8, "MorbidCondition");
////		read.setcelldata("Sheet1", 0, 9, "Recipeurl");

//	
//		

//		for(int j=1; j<2; j++) {
//			for(int i=1; i<=cardcount; i++) {
//				 WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
//				   wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")));
//			driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).click();
//			int noofnutrients=driver.findElements(By.xpath("//table[@id='rcpnutrients']/tbody/tr")).size();
//			for(int k=1; k<noofnutrients; k++) {
//			String value = driver.findElement(By.xpath("//table[@id='rcpnutrients']/tbody/tr["+k+"]/td[2]/span")).getText();
//			nv.add(value);
//			for(int mj=7; mj<=13; mj++) {
//				read.setcelldata("Sheet1", k, mj, nv.get(k-1));
//				
//			}
//			driver.navigate().back();
//			driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a["+j+"]")).click();
//		}	
//			
//		}
//	
//		
//		
	//}
//		
//			 WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(30));
//			   wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")));
//			 WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(30));
//			   wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")));
//			driver.findElements(By.xpath("//div[@id='recipe_small_steps']//ol")).size();
//			System.out.println("recipe" +i++);
//			System.out.println("Method name " +noofmethods);
//			for(int a=1; a<=noofmethods; a++) {
//				 WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(30));
//				 wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='recipe_small_steps']//li["+a+"]//span")));
//				 methods.add(driver.findElement(By.xpath("//div[@id='recipe_small_steps']//ol[1]//li["+a+"]//span")).getText());	
//			}
			  
//			  for(int m=0;m<methods.size(); m++) {
//				  
//				  read.setcelldata("Sheet1", m+1, 4, cooktime.get(m).toString());
//	}	
//	@Test(priority=5)
//	public void capturebfstveg() throws IOException {
//		con=new ConFigreader();
//		diabeticbfstveg();
//		Loggerload.info("The diabetic breakfast vegetarian is captured in the excel sheet");
//		  excelpath="C:\\Users\\amitf\\eclipse-workspace\\Jobinterview\\Recipescrapinghackathon\\src\\test\\resources\\TestData\\scrapingdata.xlsx";
//		read=new Excelreader(excelpath);
//		List<String> ing = new ArrayList<String>();
//		List<String> category = new ArrayList<String>();
//		Gettersetter get = new Gettersetter();
//		read.setcelldata("Sheet1", 0, 2, "FoodCategory");
//		read.setcelldata("Sheet1", 0, 3, "Ingredients");
//		int pagescount=driver.findElements(By.xpath("//div[@id='maincontent']/div/div/div[3]/a")).size();
//		int cardcount=driver.findElements(By.xpath("//div[@class='rcc_recipecard']")).size();
//		for(int j=1; j<pagescount; j++) {
//			for(int i=1; i<=cardcount; i++) {
//				 WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
//				   wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")));
//		         driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).getText();
////		          int ingredientscount = driver.findElements(By.xpath("//div[@id='rcpinglist']//span//a")).size();
////		          for(int in=1; in<ingredientscount; in++) {
////		        	ing.add(driver.findElement(By.xpath("//div[@id='rcpinglist']//span["+in+"]//a")).getText());
//		        	List<String> nonveg = new ArrayList<String>();
//		        	nonveg.add("Chicken");
//		        	nonveg.add("Fish");
//		        	nonveg.add("Shellfish");
//		        	if(!ing.contains(nonveg)) {
//		        		read.setcelldata("Sheet1", i, 2, "Vegetarian");
//		        	}
//		        	driver.navigate().back();
//		          }
//			}
//		           
//		          driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a["+j+"]")).click();   
//	
//		} 
//	}
//}
//	}

