 package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil 
{     
	 XSSFWorkbook wb;
	 //write constructor for excel path  we pass excel path as argument to constructor
	 public ExcelFileUtil(String Excelpath) throws Throwable 
	 {
		 
		 FileInputStream fi  = new FileInputStream(Excelpath);
		 wb = new  XSSFWorkbook(fi);
	 } 
		 //method for writing the counting no of row in sheet
	   public int rowCount(String sheetName)
		 {
			 return wb.getSheet(sheetName).getLastRowNum(); 
		 }
	   
	   public String  getCellData(String sheetName, int  row, int column) 
	   {
		   String data="";
		   if(wb.getSheet(sheetName).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
		   {
			  int celldata =  (int) wb.getSheet(sheetName).getRow(row).getCell(column).getNumericCellValue();
			   data = String.valueOf(celldata);
		   }
		   else
		   {
			   data = wb.getSheet(sheetName).getRow(row).getCell(column).getStringCellValue();

		   }
		return data;
	   }
	   
	   public void setCellData(String sheetName, int row , int column , String status, String writeExcel) throws Throwable
	   {	
		   //get sheet from workbook
		   XSSFSheet ws = wb.getSheet(sheetName);
		   //get row from sheet
		   XSSFRow rc = ws.getRow(row);
		   //get cell from row
		   XSSFCell cell = rc.createCell(column);
		    //write data into cell
		   cell.setCellValue(status);
		  
		   if(status.equalsIgnoreCase("Pass"))
		   {
			   XSSFCellStyle style = wb.createCellStyle();
			   XSSFFont font = wb.createFont();
			   font.setColor(IndexedColors.GREEN.getIndex());
			   font.setBold(true);
			   style.setFont(font);
			   rc.getCell(column).setCellStyle(style);
		   }
		   else if(status.equalsIgnoreCase("Fail"))
		   {
			   XSSFCellStyle style = wb.createCellStyle();
			   XSSFFont font = wb.createFont();
			   font.setBold(true);
			   font.setColor(IndexedColors.RED.getIndex()); 
			   font.setBold(true);
			   style.setFont(font);
			   rc.getCell(column).setCellStyle(style);
		   }
		   else if(status.equalsIgnoreCase("blocked"))
		   {
			   XSSFCellStyle style = wb.createCellStyle();
			   XSSFFont font = wb.createFont();
			   font.setBold(true);
			   font.setColor(IndexedColors.BLUE.getIndex());
			   font.setBold(true);
			   style.setFont(font);
			   rc.getCell(column).setCellStyle(style);
		   }
		   FileOutputStream fo = new FileOutputStream(writeExcel);
		   wb.write(fo);
	   }
}