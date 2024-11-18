package DriverFactory;

import java.io.File;

import javax.annotation.processing.SupportedSourceVersion;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript 
{
	WebDriver driver;
	String inputpath ="./FileInput\\Controller.xlsx";
	String outputpath ="./fileOutput\\Hybridresults.xlsx";
	String TCSheet ="MasterTestCases";
	ExtentReports reports;
	ExtentTest logger;
	public void startTest() throws Throwable
	{
		String Module_Status="";
		String Module_New ="";
		//create reference obejct for accessing excel methods
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		//iterate all rows in TCSheet
		for(int i=1;i<=xl.rowCount(TCSheet);i++)
		{
			if(xl.getCellData(TCSheet, i, 2).equalsIgnoreCase("Y"))
			{
				//read testcases from TCSheet
				String TCModule =xl.getCellData(TCSheet, i, 1);
				//defing path of html reports
				reports = new ExtentReports("./target/Reports/"+TCModule+"----"+FunctionLibrary.generateDate()+".html");
				logger =reports.startTest(TCModule); 
				logger.assignAuthor("Vaishali");
				//iterate all rows in TCmodule sheet
				for(int j=1; j<=xl.rowCount(TCModule); j++)
				{
					//read all cells from TCModule sheet
					String Description = xl.getCellData(TCModule, j, 0);
					String ObjectType = xl.getCellData(TCModule, j, 1);
					String Ltype =xl.getCellData(TCModule, j, 2);
					String Lvalue = xl.getCellData(TCModule, j, 3);
					String TestData = xl.getCellData(TCModule, j, 4);
					
					try {
						if(ObjectType.equalsIgnoreCase("StartBrowser"))
						{
							driver =FunctionLibrary.startBrowser(); 
						// logger object is holding statis of reports  and reports holding path of html report
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectType.equalsIgnoreCase("OpenUrl"))
						{
							FunctionLibrary.openUrl();
							logger.log(LogStatus.INFO, Description);

						}
						if(ObjectType.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitforElement(Ltype, Lvalue, TestData);
							logger.log(LogStatus.INFO, Description);

						}
						if(ObjectType.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(Ltype, Lvalue, TestData);
							logger.log(LogStatus.INFO, Description);

						}
						if(ObjectType.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(Ltype, Lvalue);
							logger.log(LogStatus.INFO, Description);

						}
						if(ObjectType.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(TestData);
							logger.log(LogStatus.INFO, Description);

						}
						if(ObjectType.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, Description);

						}

						if(ObjectType.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(Ltype, Lvalue, TestData);
							logger.log(LogStatus.INFO, Description);

						}
						if(ObjectType.equalsIgnoreCase("captureStock"))
						{
							FunctionLibrary.captureStock(Ltype, Lvalue);
							logger.log(LogStatus.INFO, Description);

						}
						if(ObjectType.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable();
							logger.log(LogStatus.INFO, Description);

						}

						if(ObjectType.equalsIgnoreCase("captureSupplier"))
						{
							FunctionLibrary.captureSupplier(Ltype, Lvalue, TestData);
						}
						if(ObjectType.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable();
						}

						if(ObjectType.equalsIgnoreCase("captureCustomer"))
						{
							FunctionLibrary.captureCustomer(Ltype, Lvalue, TestData);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectType.equalsIgnoreCase("customerTable"))
						{
							FunctionLibrary.customerTable();
							logger.log(LogStatus.INFO, Description); 
						}

						//write as pass into status cell in TCModule sheet
						xl.setCellData(TCModule, j, 5, "Pass", outputpath);
						Module_Status ="True";
						logger.log(LogStatus.PASS, Description);

					} catch (Exception e) {
						System.out.println(e.getMessage());
						//write as Fail into status cell in TCModule sheet
						xl.setCellData(TCModule, j, 5, "Fail", outputpath);
						Module_New="False";
						logger.log(LogStatus.FAIL, Description);
						File Screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						 FileUtils.copyFile(Screen, new File("./target/Screenshot/"+Description+"----"+FunctionLibrary.generateDate()+".png"));
					}

					if(Module_Status.equalsIgnoreCase("True"))
					{
						//write as pass into TCsheet in Status cell
						xl.setCellData(TCSheet, i, 3, "Pass", outputpath);
					}
					//stop logging to report object so report.endtest()
					reports.endTest(logger); 
					// existing reports remove it by flush 
					reports.flush();
				}
				if(Module_New.equalsIgnoreCase("False"))
				{
					//write as Fail into TCsheet in Status cell
					xl.setCellData(TCSheet, i, 3, "Fail", outputpath);
				}
			}
			else
			{
				//write as blocked for testcases which are flag to N
				xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);
			}
		}
         
	}
	

}


