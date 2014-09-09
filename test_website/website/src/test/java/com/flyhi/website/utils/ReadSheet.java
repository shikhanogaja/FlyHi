package com.flyhi.website.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ReadSheet 
{
	public static int mRows_TC;
	public static int nCols_TC;
    public static String Data_TC[][];
    public static int mRows_TS;
    public static int nCols_TS;
    public static String Data_TS[][];
    public static int mRows_TED;
    public static int nCols_TED;
    public static String Data_TED[][];
    public static int mRows_TD;
    public static int nCols_TD;
    public static String Data_TD[][];
    
    public static void xlRead_TC(String sPath, int sSheet) throws Exception
    {
        File myxl = new File(sPath);
        FileInputStream myStream = new FileInputStream(myxl);
        HSSFWorkbook myWB = new HSSFWorkbook(myStream);
        HSSFSheet mySheet = myWB.getSheetAt(sSheet);
        
        mRows_TC= mySheet.getLastRowNum()+1;
        nCols_TC= mySheet.getRow(0).getLastCellNum();
        Data_TC	= new String[mRows_TC][nCols_TC];
        
        for (int i = 0; i < mRows_TC; i++) 
        {
           HSSFRow row = mySheet.getRow(i);
           
            for (short j = 0; j < nCols_TC; j++) 
            {
               HSSFCell cell= row.getCell(j);
               String value	= cellToString(cell);
               Data_TC[i][j]= value;
            }
        }   
    }
   
    public static void xlRead_TS(String sPath, int sSheet) throws Exception
    {
    	
        File myxl = new File(sPath);
        FileInputStream myStream = new FileInputStream(myxl);
        HSSFWorkbook myWB = new HSSFWorkbook(myStream);
        HSSFSheet mySheet = myWB.getSheetAt(sSheet);

        mRows_TS= mySheet.getLastRowNum()+1;
        nCols_TS= mySheet.getRow(0).getLastCellNum();
        Data_TS = new String[mRows_TS][nCols_TS];
        
        for (int i = 0; i < mRows_TS; i++) 
        {
           HSSFRow row = mySheet.getRow(i);
            for (short j = 0; j < nCols_TS; j++) 
            {
               HSSFCell cell= row.getCell(j);
               String value	= cellToString(cell);
               Data_TS[i][j]= value;
            }
        }   
    }
   
    public static void xlRead_TED(String sPath, int sSheet) throws Exception
    {
        File myxl = new File(sPath);
        FileInputStream myStream = new FileInputStream(myxl);
        HSSFWorkbook myWB = new HSSFWorkbook(myStream);
        HSSFSheet mySheet = myWB.getSheetAt(sSheet);
   
        mRows_TED= mySheet.getLastRowNum()+1;
        nCols_TED= mySheet.getRow(0).getLastCellNum();
        Data_TED = new String[mRows_TED][nCols_TED];
        for (int i = 0; i < mRows_TED; i++) 
        {
           HSSFRow row = mySheet.getRow(i);               
           for (short j = 0; j < nCols_TED; j++) 
            {
               HSSFCell cell = row.getCell(j);
               String value = cellToString(cell);
               Data_TED[i][j] = value;                   
            }
        }   
    }
    
    	 
	public static String cellToString(HSSFCell cell) 
	{
	        // This function will convert an object of type excel cell to a string value
	            int type = cell.getCellType();
	            Object result;
	            switch (type) 
	            {
	                case HSSFCell.CELL_TYPE_NUMERIC: //0
	                	result = cell.getNumericCellValue();
	                    break;
	                case HSSFCell.CELL_TYPE_STRING: //1
	                    result = cell.getStringCellValue();
	                    if(result.toString().startsWith("\"")){
	                    	result=result.toString().substring(1, result.toString().length()-1);
	                    }
	                    break;
	                case HSSFCell.CELL_TYPE_FORMULA: //2
	                    throw new RuntimeException("We can't evaluate formulas in Java");
	                case HSSFCell.CELL_TYPE_BLANK: //3
	                    result = "-";
	                    break;
	                case HSSFCell.CELL_TYPE_BOOLEAN: //4
	                    result = cell.getBooleanCellValue();
	                    break;
	                case HSSFCell.CELL_TYPE_ERROR: //5
	                    throw new RuntimeException ("This cell has an error");
	                default:
	                    throw new RuntimeException("We don't support this cell type: " + type);
	            }
	            return result.toString();
	 }
	 
	 public static void xlWrite(String xlPath) throws Exception
	 {
	    	 File outFile = new File(xlPath);
	         HSSFWorkbook wb = new HSSFWorkbook();         
	         xlwrite(xlPath, "TestEnvironmentData", outFile, wb, mRows_TED, nCols_TED, Data_TED);         
	         xlwrite(xlPath, "TestCaseRESULTS", outFile, wb, mRows_TC, nCols_TC, Data_TC);
	         xlwrite(xlPath, "TestStepRESULTS", outFile, wb, mRows_TS, nCols_TS, Data_TS);
	 }
	   
	 @SuppressWarnings("deprecation")
	 public static void xlwrite(String xlPath, String sheetName, File outFile, HSSFWorkbook wb, int mRows_TS, int nCols_TS, String[][] xldata) throws Exception 
	 {       
	        HSSFSheet osheet = wb.createSheet(sheetName);
	        osheet.setDisplayGridlines(false);
	        osheet.setPrintGridlines(false);
	        osheet.setFitToPage(true);
	        osheet.setHorizontallyCenter(true);
	        
	        for (int myrow = 0; myrow < mRows_TS; myrow++) 
	        {
	            HSSFRow row = osheet.createRow(myrow);

	            for (short mycol = 0; mycol < nCols_TS; mycol++) 
	            {
	                HSSFCell cell = row.createCell(mycol);
	                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	                cell.setCellValue(xldata[myrow][mycol]);	            
	            }
	            FileOutputStream fOut = new FileOutputStream(outFile);
	            wb.write(fOut);
	            fOut.flush();
	            fOut.close();
	        }
    }

}
