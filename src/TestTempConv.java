import static org.junit.Assert.*;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class TestTempConv {
	
   static long t0;
   
   public static void main(String[] args) throws Exception {
       String success = "Online temperature conversion calculator";
       String failure = "Bad Login";
       String lockout = "Frequent Login";
     
       // The Firefox driver supports javascript
       //WebDriver driver = new FirefoxDriver();
       WebDriver driver = new FirefoxDriver();

       TestTempConv gs = new TestTempConv();

       t0 = System.currentTimeMillis();

       driver.get("http://adnan.appspot.com/testing-lab-login.html");
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ": Attempt login 3 times with bad password.");
       gs.testLogin(driver, "charley", "apple", failure);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  1st attempt complete.");

       // login attempts in quick succession will result in lockout delay screen.
       //multiple ways to get back to login page use url here
       driver.get("http://adnan.appspot.com/testing-lab-login.html");
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  2nd attempt initiated.");
       gs.testLogin(driver, "charley", "banana", lockout);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  2nd attempt complete.");

       // login attempts in quick succession will result in lockout delay screen.
       //multiple ways to get back to login page use back navigation
       driver.navigate().back();
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  3rd attempt initiated.");
       gs.testLogin(driver, "charley", "carrot", lockout);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  3rd attempt complete.");

       driver.get("http://adnan.appspot.com/testing-lab-login.html");
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ": 4th attempt initiated with good password expect lockout.");    
       gs.testLogin(driver, "charley", "china", lockout);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  4th attempt complete.");

       driver.get("http://adnan.appspot.com/testing-lab-login.html");
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  bad password attempt");
       gs.testLogin(driver, "bob", "banana", failure);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);

       driver.findElement(By.tagName("a")).click();
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  bad password attempt");
       gs.testLogin(driver, "andy", "banana", failure);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       
       driver.findElement(By.tagName("a")).click();
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  attempt to login BOB ");
       gs.testLogin(driver, " BOB ", "bathtub", lockout);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       driver.navigate().back();

       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  attempt to login andy ");
       gs.testLogin(driver, "andy", "apple", success);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       driver.navigate().back();

       
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  attempt to login charley ");
       gs.testLogin(driver, "charley", "china", success);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       driver.navigate().back();

       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  attempt to login ANDY ");
       gs.testLogin(driver, "ANDY", "apple", lockout);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       driver.navigate().back();

       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  attempt to login bob ");
       gs.testLogin(driver, "bob", "bathtub", success);
       if (driver.getTitle().compareTo(lockout) == 0) testLockout(driver);
       driver.navigate().back();

       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  attempt to login ChArLeY ");
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
   
   public void test_temp_conversion(WebDriver driver, String fahren, String expected) {
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
	   assertEquals(expected, driver.findElement(By.tagName("h2")).getText());
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
