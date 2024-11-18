package CommonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary 
{
	public static WebDriver driver;
	public static Properties conpro;

	//method for launching browser

	public static WebDriver startBrowser() throws Throwable
	{
		conpro = new Properties();
		conpro.load(new FileInputStream("PropertyFiles\\Environment.properties"));
		if(conpro.getProperty("browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
			//driver.manage().window().maximize();
		}
		else
		{
			Reporter.log("browser value is not matching");
		}
		return driver;	
	}

	//method for launching url
	public static void openUrl()
	{
		driver.get(conpro.getProperty("url"));
	}

	//method for wait fro element
	public static void waitforElement(String LocatorType,String LocatorValue,String TestData)
	{
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			//wait until element is visible
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));	
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			//wait until element is visible
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));	
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			//wait until element is visible
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));	
		}
	}        	  
	//method for  textbox
	public static void typeAction(String LocatorType, String LocatorValue, String TestData)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
	}

	//method for button,checkbox,radio buttons
	public static void clickAction(String locatorType , String LocatorValue) throws Throwable
	{
		if(locatorType.equalsIgnoreCase("xpath"))
		{
			Thread.sleep(2000);
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		if(locatorType.equalsIgnoreCase("name"))
		{
			Thread.sleep(2000);
			driver.findElement(By.name(LocatorValue)).click();
		}
		if(locatorType.equalsIgnoreCase("id"))
		{
			Thread.sleep(2000);
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
	}

	//method for validating page title
	public static void validateTitle(String Expected_Title)
	{
		String Actual_Title =  driver.getTitle();
		try
		{
			Assert.assertEquals(Actual_Title, Expected_Title,"  g");
		}catch (AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
	//method for close browser
	public static void closeBrowser()
	{
		driver.quit();
	}
	//method for listbox
	public static void dropDownAction(String LocatorType,String LocatorValue,String TestData)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			//convert testdata cell into int from string data
			int value = Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.xpath(LocatorValue)));
			element.selectByIndex(value);  
			// value = it will select one item which is present in index start with 0 ,1 we provide index in xl sheet	
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			//convert testdata cell into int from string data
			int value = Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.name(LocatorValue)));
			element.selectByIndex(value);  
			// value = it will select one item which is present in index start with 0 ,1 we provide index in xl sheet	
		} 
		if(LocatorType.equalsIgnoreCase("id"))
		{
			//convert testdata cell into int from string data
			int value = Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.id (LocatorValue)));
			element.selectByIndex(value);  
			// value = it will select one item which is present in index start with 0 ,1 we provide index in xl sheet	
		}
	}

	//method for capturing stock number from notepad
	public static void captureStock(String locatorType , String LocatorValue) throws Throwable
	{
		String StockNumber = "";
		if(locatorType.equalsIgnoreCase("xpath"))
		{
			StockNumber = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(locatorType.equalsIgnoreCase("name"))
		{
			StockNumber = driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		if(locatorType.equalsIgnoreCase("id"))
		{
			StockNumber = driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		//writing stock number into notepad
		FileWriter fw = new FileWriter("./CaptureData/StockNumber.txt");
		//allocating memory during runtime we use bufferedwriter method for storing stocknumber into bw variable
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(StockNumber); 
		bw.flush();
		bw.close(); 
	}

	//method for verifying stocknumber
	public static void stockTable() throws Throwable
	{
		//read the path of notepad file
		FileReader fr= new FileReader("./CaptureData/StockNumber.txt");
		//read the stock number from notepad for that allocate memory using bufferedreader method
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();//read stock number from notepad and store into exp-data variable while execution time
		//search panel is not displayed then click search panel  
		if(!  driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(2000);
		String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Exp_Data+ "   "+Act_Data,true);
		try 
		{
			Assert.assertEquals(Exp_Data, Act_Data,"Stock number not found in table");

		} catch (AssertionError a) 
		{
			System.out.println(a.getMessage());
		}
	}

	//method for capturing supplier number into notepad
	public static void captureSupplier(String LocatorType, String LocatorValue, String TestData) throws Throwable
	{
		String SupplierNumber = "";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			SupplierNumber = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			SupplierNumber = driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			SupplierNumber = driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		//writing stock number into notepad
		FileWriter fw = new FileWriter("./CaptureData/supplierNumber.txt");
		//allocating memory during runtime we use bufferedwriter method for storing stocknumber into bw variable
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(SupplierNumber); 
		bw.flush();
		bw.close(); 
	}

	//method for  reading supplier number and verifying supplier table
	public static void supplierTable() throws Throwable
	{
		//read supplier number from notepad
		FileReader fr= new FileReader("./CaptureData/supplierNumber.txt");
		//read the supplier number from notepad for that allocate memory using bufferedreader method
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();//read supplier number from notepad and store into exp-data variable while execution time
		//search panel is not displayed then click search panel  
		if(!  driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(2000);
		String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		Reporter.log(Exp_Data+ "   "+Act_Data,true);
		try 
		{
			Assert.assertEquals(Exp_Data, Act_Data,"supplier number is not found in table");

		} catch (AssertionError a) 
		{
			System.out.println(a.getMessage());
		}
	}

	//method for capturing customer number into notepad
	public static void captureCustomer(String LocatorType, String LocatorValue, String TestData) throws Throwable
	{
		String CustomerNumber = "";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			CustomerNumber = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			CustomerNumber = driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			CustomerNumber = driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		//writing stock number into notepad
		FileWriter fw = new FileWriter("./CaptureData/CustomerNumber.txt");
		//allocating memory during runtime we use bufferedwriter method for storing stocknumber into bw variable
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(CustomerNumber); 
		bw.flush();
		bw.close(); 
	}

	//method for  reading customer number and verifying supplier table
	public static void customerTable() throws Throwable
	{
		//read customer number from notepad
		FileReader fr= new FileReader("./CaptureData/CustomerNumber.txt");
		//read the customer number from notepad for that allocate memory using bufferedreader method
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();//read customer number from notepad and store into exp-data variable while execution time
		//search panel is not displayed then click search panel  
		if(!  driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(2000);
		String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(Exp_Data+ "   "+Act_Data,true);
		try 
		{
			Assert.assertEquals(Exp_Data, Act_Data,"customer number is not found in table");

		} catch (AssertionError a) 
		{
			System.out.println(a.getMessage());
		}
	}
	
	// method ofr generating data using java time stamp 
	
	public static String generateDate()
	{
		//create new data
		Date  dt =  new Date();
		// create date format
		DateFormat df = new SimpleDateFormat("YYYY_MM_dd");
		return df.format(dt);
		
	}
}







