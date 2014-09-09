package com.flyhi.website.driver;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.flyhi.website.utils.Event;
import com.flyhi.website.utils.FolderManagement;
import com.flyhi.website.utils.ReadSheet;


public class Driver 
{	
	File folderPath;

	public static String vEHR_TS, vEHR_TC;
	public static String vEmail, vFirst, vLast, vOrg, vAdmin, vUser;
	public static String vURL, vBrowser, vPassword, vKW, vTDKW;
	public static String TStamp;
	public static String vStep, vDesc, vIP1, vIP2, vIP3, vIP4, vIP5, vIP6;
	public static String vTED, vTCName, vTCID;
	public static String resultPath;
	public static String vEHR_TSDetails;
	public static Properties element;
	public static final String PATH_SEPARATOR = File.separator;
    @Before
    public void setUp() throws Exception 
    {
        TStamp = Event.dateTimeStamp();       
        try
        {        	
        	String userDir = System.getProperty("user.dir");
        	
        	StringBuilder filename = new StringBuilder();
        	filename.append("src").append(PATH_SEPARATOR);
        	filename.append("test").append(PATH_SEPARATOR);
        	filename.append("java").append(PATH_SEPARATOR);
        	filename.append("com").append(PATH_SEPARATOR);
        	filename.append("flyhi").append(PATH_SEPARATOR);
        	filename.append("website").append(PATH_SEPARATOR);
        	filename.append("data").append(PATH_SEPARATOR);
        	filename.append("JustOneClick.xls");
        	
        	ReadSheet.xlRead_TED(userDir+PATH_SEPARATOR+filename.toString(),0);
        	ReadSheet.xlRead_TC(userDir+PATH_SEPARATOR+filename.toString(),1);
        	ReadSheet.xlRead_TS(userDir+PATH_SEPARATOR+filename.toString(),2);
        	
        }
        catch(Exception e) {
        	System.out.println("Please find the location of file or make the proper data in excel sheet" + e);
        } 
        
        try {       
        	element = new Properties();
        	FileInputStream fileObject = new FileInputStream(System.getProperty("user.dir")+"/src/test/java/com/flyhi/website/data/element.properties");
        	element.load(fileObject);
        }
        catch(Exception e) {
        	System.out.println("Please find the location of file or make the proper data in excel sheet" + e);
        }
        
    }
    
    @Test
    public void testUntitled() throws Exception 
    {     	
    	for (int k = 1; k<ReadSheet.mRows_TED; k++) 
    	{
            if (ReadSheet.Data_TED[k][1].equals("Y")) 
            {
            	vTED	 	= ReadSheet.Data_TED[k][0];
            	vURL 	 	= ReadSheet.Data_TED[k][2];
                vBrowser	= ReadSheet.Data_TED[k][3];
                vEmail 		= ReadSheet.Data_TED[k][4];
                vPassword	= ReadSheet.Data_TED[k][5];

                
                Event.broswerConfig(vBrowser);
                Event.implicitWait();

                for (int i=1; i<ReadSheet.mRows_TC; i++) 
                {
                    if ("Y".equalsIgnoreCase(ReadSheet.Data_TC[i][4])) 
                    {
                        vEHR_TC 	= "Pass";
                        vTCID 		= ReadSheet.Data_TC[i][1];
                        folderPath		= FolderManagement.createDirectory();
                        resultPath	= folderPath.getCanonicalPath();
                        System.out.println("result path" +resultPath);
                        for (int j=1; j<ReadSheet.mRows_TS; j++) 
                        {
                            if (vTCID.equals(ReadSheet.Data_TS[j][0])) 
                            {
                            	vTCName = ReadSheet.Data_TS[j][0];
                            	vStep	= ReadSheet.Data_TS[j][2];
                            	vDesc	= ReadSheet.Data_TS[j][3];
                                vKW	 	= ReadSheet.Data_TS[j][4];
                         		vIP1 	= ReadSheet.Data_TS[j][5];
                        		vIP2 	= ReadSheet.Data_TS[j][6];
                                vIP3 	= ReadSheet.Data_TS[j][7];
                                vIP4 	= ReadSheet.Data_TS[j][8];
                                vIP5 	= ReadSheet.Data_TS[j][9];
                                vIP6 	= ReadSheet.Data_TS[j][10];
                                
                                getInputs();
                               
                                vEHR_TS 		= "Pass";
								vEHR_TSDetails 	= "No Error ";
                                
                                try 
                                {
                                   Thread.sleep(2000);
                                   executeKeywords();
                                } 
                                catch (Exception vMYError) 
                                {
                                	System.out.println(vMYError);
                                    vEHR_TS        = "Fail";
                                    vEHR_TSDetails = "Error is: " + vMYError;     
                                    Event.saveScreenshot();                                 
                                }
                                
                                if (vEHR_TS.equals("Fail"))
                                {
                                    vEHR_TC = "Fail";  
                                    Event.saveScreenshot();
                                }
                                
                                ReadSheet.Data_TS[j][11] = vEHR_TS;
                                ReadSheet.Data_TS[j][12] = vEHR_TSDetails;   
                            }
                        }
                        ReadSheet.Data_TC[i][5] = vEHR_TC;
                    }
                }
                
                ReadSheet.xlWrite(resultPath +PATH_SEPARATOR +"Results_" + TStamp + "_" + ReadSheet.Data_TED[k][0] +"_TS_" + ReadSheet.Data_TED[k][3] + ".xls");           	
            }    
        }
    	/*zipFile.zipFormat();
    	sendEmail.emailConfiguration();
    	folderMgt.deleteDirectory();*/
    }
    
    @After
    public void tearDown() throws Exception 
    {
    	Event.browserStop();      	
    }
    
    public void getInputs()
    {
        if (vIP1.equals("vURL"))
        {
            vIP1 = vURL;
        }
        else if (vIP1.equals("vEmail"))
        {
            vIP1 = vEmail;
        }
        else if (vIP1.equals("vPassword"))
        {
            vIP1 = vPassword;
        }
        else if (vIP2.equals("vURL"))
        {
            vIP2 = vURL;
        }
        else if (vIP2.equals("vEmail"))
        {
            vIP2 = vEmail;
        }
        else if (vIP2.equals("vPassword"))
        {
            vIP2 = vPassword;
        }       
    }
    
    public void executeKeywords() throws Exception
    {   
    	/*
    	 * Generic Keywords - Can be used across the application
    	 */
		if (vKW.equals("enterSite"))
		{
			Event.enterSite();
		}
		else if (vKW.equalsIgnoreCase("enter"))
		{
			Event.enter(vIP1);
		}
		else if (vKW.equalsIgnoreCase("resultLoad"))
		{
			Event.implicitWait();
			Event.implicitWait();
		}
		else if (vKW.equalsIgnoreCase("clickButton")) 
		{
			Event.clickButton(vIP1);
		}
		else if (vKW.equalsIgnoreCase("clickLink")
				|| vKW.equalsIgnoreCase("clickTab"))
		{
			Event.clickLink(vIP1);
		}
		else if (vKW.equals("editInput")) 
		{
			Event.editInput(vIP1, vIP2);
		}		
		else if (vKW.equalsIgnoreCase("editDate"))
		{
			Event.editDate(vIP1, vIP2);
		}
		else if (vKW.equalsIgnoreCase("editAutoFill"))
		{
			Event.editAutoFill(vIP1, vIP2, vIP3);
		}
		else if (vKW.equalsIgnoreCase("selectFromDropDown"))
		{
			// Call to function here
		}
		else if (vKW.equalsIgnoreCase("clickMenu"))
		{
			Event.clickLink(vIP1);
		}
		else if (vKW.equalsIgnoreCase("verifyPageTitle")
				|| vKW.equalsIgnoreCase("invalidCredentials") 
				|| vKW.equalsIgnoreCase("validMandatory")
				|| vKW.equalsIgnoreCase("checkPullOut"))
		{
			Event.verifyElement(vIP1);
		}
		else if (vKW.equalsIgnoreCase("defaultSelected"))
		{
			Event.defaultSelected(vIP1);
		}
		else if (vKW.equalsIgnoreCase("browserClose"))
		{
			Event.driverQuit();
		}
		else if(vKW.equalsIgnoreCase("getTextData"))
		{ 
            Thread.sleep(2000);
			String text = Event.getTextData(vIP1);
			if(text != null)
			{
				System.out.println(text + " found");
			}
			else
			{
				System.out.println(text + " not found");
			}
		}
    	// TODO - Use Better Synchronization in future
		else if (vKW.equals("pageSync"))
		{
			String s = vIP1;
            double d = Double.valueOf(s);
            long l   = (new Double(d)).longValue();
            Thread.sleep(Long.valueOf(l)*1000);
		}
    	
    	/*
    	 * Business Keywords - Specific to the application
    	 */
		else if (vKW.equals("businessKeywordsPlaceholders"))
		{
			// // Call to business keyword here
		}       
    }
}