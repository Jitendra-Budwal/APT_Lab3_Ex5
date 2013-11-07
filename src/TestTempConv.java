import static org.junit.Assert.assertEquals;

import java.io.File;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;


public class TestTempConv {
	
   static long t0;
   
   public static void main(String[] args) throws Exception {
       String success = "Online temperature conversion calculator";
       String failure = "Bad Login";
       String lockout = "Frequent Login";
     
       // The Firefox driver supports javascript
       //WebDriver driver = new FirefoxDriver();
       ProfilesIni allProfiles = new ProfilesIni();
       FirefoxProfile profile = new FirefoxProfile(new File("C:\\commondir\\Selenium_FireFox_profile"));
       WebDriver driver = new FirefoxDriver(profile);       

       TestTempConv gs = new TestTempConv();
       
       t0 = System.currentTimeMillis();

       driver.get("http://adnan.appspot.com/testing-lab-login.html");
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ": Attempt login 3 times with bad password.");
       gs.testLogin(driver, "charley", "apple", failure);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  1st of 3.");

       // login attempts in quick succession will result in lockout delay screen.
       //multiple ways to get back to login page use url here
       driver.get("http://adnan.appspot.com/testing-lab-login.html");
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  2nd of 3 - initiated.");
       gs.testLogin(driver, "charley", "banana", lockout);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  2nd of 3 - complete.");

       // login attempts in quick succession will result in lockout delay screen.
       //multiple ways to get back to login page use back navigation
       driver.navigate().back();
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  3rd of 3 - initiated.");
       gs.testLogin(driver, "charley", "carrot", lockout);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  3rd of 3 - complete.");

       driver.get("http://adnan.appspot.com/testing-lab-login.html");
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ": Login charley with good password expect lockout.");    
       gs.testLogin(driver, "charley", "china", lockout);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       if (driver.getTitle().compareTo(success) == 0) {
    	   System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  charley logged in.");
       } else {
    	   System.out.println(((System.currentTimeMillis() - t0)/1000) + ": ***Failure: charley failed to login within timeout.");
       }

       //test the temp conversion
       //use the url to avoid dependence on success of prior tests
       driver.get("http://adnan.appspot.com/testing-lab-calculator.html");
       gs.testTempConversion(driver, "59.9", "59.9 Farenheit = 15.50 Celsius");

       driver.navigate().back();       
       gs.testTempConversion(driver, "9.73e2", "Need to enter a valid temperature!Got a NumberFormatException on 9.73e2");

       driver.navigate().back();       
       gs.testTempConversion(driver, "1e3", "Need to enter a valid temperature!Got a NumberFormatException on 1e3");

       driver.navigate().back();       
       gs.testTempConversion(driver, "59.891", "59.891 Farenheit = 15.50 Celsius");

       driver.navigate().back();       
       gs.testTempConversion(driver, "-40", "-40 Farenheit = -40.0 Celsius");

       driver.navigate().back();       
       gs.testTempConversion(driver, "-39.99", "-39.99 Farenheit = -40.0 Celsius");

       driver.navigate().back();       
       gs.testTempConversion(driver, "59.89", "59.89 Farenheit = 15.49 Celsius");

       driver.navigate().back();       
       gs.testTempConversion(driver, "boil", "Need to enter a valid temperature!Got a NumberFormatException on boil");       

       driver.get("http://adnan.appspot.com/testing-lab-login.html");
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  bob wrong password");
       gs.testLogin(driver, "bob", "banana", failure);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);

       driver.findElement(By.tagName("a")).click();
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  andy wrong password");
       gs.testLogin(driver, "andy", "banana", failure);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       
       driver.findElement(By.tagName("a")).click();
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  BOB verify leading trailing space insensitivity ");
       gs.testLogin(driver, " BOB ", "bathtub", lockout);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       driver.navigate().back();

       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  andy correct password");
       gs.testLogin(driver, "andy", "apple", success);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       driver.navigate().back();
      
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  charley correct password");
       gs.testLogin(driver, "charley", "china", success);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       driver.navigate().back();

       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  ANDY correct password");
       gs.testLogin(driver, "ANDY", "apple", lockout);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       driver.navigate().back();

       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  bob correct password ");
       gs.testLogin(driver, "bob", "bathtub", success);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       driver.navigate().back();

       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  ChArLeY verify case insensitivity.");
       gs.testLogin(driver, "ChArLeY", "china", success);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);

       //end of tests.
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  end of Tests.");
       driver.close();

    }

   public void testLogin(WebDriver webdriver, String username,
		   String password, String expect) {
     //expect to start at login page
     assertEquals("Dummy login page for Testing lab", webdriver.getTitle());

     WebElement userE = webdriver.findElement(By.name("userId"));
     userE.clear();
     userE.sendKeys(username);
     WebElement passE = webdriver.findElement(By.name("userPassword"));
     passE.clear();
     passE.sendKeys(password);
     passE.submit();
     
     if (expect.compareTo(webdriver.getTitle()) == 0 ) {
//    	 System.out.println(((System.currentTimeMillis() - t0)/1000) + ": Login Test Passed: " + username);
     } else {
    	 System.out.println(((System.currentTimeMillis() - t0)/1000) + ": Login Test Failed: " + username); 	 
    	 System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  Expected: [" + expect + "] Actual: [" + webdriver.getTitle() + "]"); 	 
     };
   }
   
   public void testTempConversion(WebDriver driver, String fahren, String expect) {
	   //interact the input page titled "Online temperature conversion calculator"
	   // elements input box name:"farenheitTemperature"
	   // and button value:"Convert" - the output captured from the h2 tag on the 
	   //response page titled "Temperature Converter Result"
	   WebElement fTemp = driver.findElement(By.name("farenheitTemperature"));
	   assertEquals("Online temperature conversion calculator" , driver.getTitle());
	   
	   // clear and the data in the input box
	   fTemp.clear();
	   fTemp.sendKeys(fahren);
	   fTemp.submit();

	   if (expect.compareTo(driver.findElement(By.tagName("h2")).getText()) == 0 ) {
//	    	 System.out.println(((System.currentTimeMillis() - t0)/1000) + ": Login Test Passed: " + fahren);
	     } else {
	    	 System.out.println(((System.currentTimeMillis() - t0)/1000) + ": Temp Conversion Test Failed for: " + fahren); 	 
	    	 System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  Expected: [" + expect + "] Actual: [" + driver.findElement(By.tagName("h2")).getText() + "]"); 	 
	     };

//	   try {
//		   assertEquals(expect, driver.findElement(By.tagName("h2")).getText());
//	   } catch (Exception e) {
//		   System.out.println("Stacktrace follows;");
//           e.printStackTrace();
////ComparisonFailure.getMessage(expected, driver.findElement(By.tagName("h2")).getText()); 	 
//           System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  Expected: [" + expect + "] Actual: [" + driver.getTitle() + "]");		   
//	   }
   }
	      
   private static void testLockout(WebDriver driver) throws InterruptedException {
	   Long t1;
	   Boolean exceededTimeout;
	   // check length of lockout
       t1 = System.currentTimeMillis() + 11000;
       exceededTimeout = true;
       
       do {
    	   if (driver.getTitle().compareTo("Frequent Login") == 0) {
    		   Thread.sleep(1000);
//    		   System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  Title = " + driver.getTitle());
    		   System.out.print(".");
    		   driver.navigate().refresh();
    		   //Now the alert appears. 
    		   Alert alert = driver.switchTo().alert();
    		   alert.accept();
    	   } else {
    		   exceededTimeout = false;
    		   break;
    	   }
       } while (System.currentTimeMillis() < t1);
       System.out.println("|");
       if (exceededTimeout) {
       		System.out.println(((System.currentTimeMillis() - t0)/1000) + ":   Exceeded lockout time.");
       }
    }
}
