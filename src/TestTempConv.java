import java.util.concurrent.TimeUnit;

import org.junit.Test;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;


public class TestTempConv {
	
   static long t0;
   
   public void test_login_success(WebDriver webdriver, String username,
		   String password, String expect) {
     //go to login page
//     webdriver.get("http://adnan.appspot.com/testing-lab-login.html");
//     assertEquals("Online temperature conversion calculator", webdriver.getTitle());

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
	      
   public static void main(String[] args) throws Exception {
       // The Firefox driver supports javascript
       //WebDriver driver = new FirefoxDriver();
       WebDriver driver = new FirefoxDriver();

       String success = "Online temperature conversion calculator";
       String failure = "Bad Login";
       String slowdown = "Frequent Login";
 
       driver.get("http://adnan.appspot.com/testing-lab-login.html");
       
       TestTempConv gs = new TestTempConv();

       t0 = System.currentTimeMillis();

       System.out.println(((System.currentTimeMillis() - t0)/1000) + ": Attempt login 3 times with bad password.");
       gs.test_login_success(driver, "charley", "apple", failure);
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  1st attempt complete.");

//     System.out.println(" Passed charley login test.");
//     driver.navigate().back();
//     driver.findElement(By.tagName("a")).click();
       driver.get("http://adnan.appspot.com/testing-lab-login.html");

       gs.test_login_success(driver, "charley", "china", slowdown);
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  2nd attempt complete.");
//   System.out.println(" Passed charley login test.");
//   driver.navigate().back();
//   driver.findElement(By.tagName("a")).click();
    // Waiting 20 seconds for an element to be present on the page, checking
       // for its presence once every 1 seconds.
       Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
           .withTimeout(20, TimeUnit.SECONDS)
           .pollingEvery(1, TimeUnit.SECONDS)
           .ignoring(NoSuchElementException.class);

       WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
         public WebElement apply(WebDriver driver) {
           return driver.findElement(By.tagName("a"));
         }
       });
//       long t1 = System.currentTimeMillis() + 20000;
//       while System.currentTimeMillis() < t1)
//       (new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>() {
//           public Boolean apply(WebDriver d) {
//    		   d.navigate().refresh();
//        	   if (d.findElement(By.tagName("a")) == null) {
//        	       System.out.print( ".");
//        		   return false;
//        	   } else {
//        	       System.out.println( "#");
//        		   return true;
//        	   }
//           }
//       });
       
       driver.get("http://adnan.appspot.com/testing-lab-login.html");

       gs.test_login_success(driver, "charley", "carrot", slowdown);
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  3rd attempt complete.");
//       System.out.println(" Passed charley login test.");
//       driver.navigate().back();
//       driver.findElement(By.tagName("a")).click();
       driver.get("http://adnan.appspot.com/testing-lab-login.html");

       System.out.println(((System.currentTimeMillis() - t0)/1000) + ": Attempt login 4 with good password expect lockout.");    
       gs.test_login_success(driver, "charley", "china", success);
       System.out.println(((System.currentTimeMillis() - t0)/1000) + ":  expect lock out.");
//       System.out.println(" Passed charley login test.");
//       driver.navigate().back();
//       driver.findElement(By.tagName("a")).click();
       driver.get("http://adnan.appspot.com/testing-lab-login.html");

       gs.test_login_success(driver, "bob", "banana", failure);
//       System.out.println(" Passed bob login test.");
//       driver.navigate().back();
       driver.findElement(By.tagName("a")).click();

       gs.test_login_success(driver, "andy", "banana", failure);
//       System.out.println(" Passed andy login test.");
//       driver.navigate().back();
       driver.findElement(By.tagName("a")).click();

       gs.test_login_success(driver, " BOB ", "bathtub", success);
//     System.out.println(" Passed BOB login test.");
     driver.navigate().back();

       gs.test_login_success(driver, "andy", "apple", success);
//       System.out.println(" Passed andy login test.");
       driver.navigate().back();

       gs.test_login_success(driver, "bob", "banana", failure);
//       System.out.println(" Passed bob login test.");
//       driver.navigate().back();
       driver.findElement(By.tagName("a")).click();

       gs.test_login_success(driver, "charley", "china", success);
//       System.out.println(" Passed charley login test.");
       driver.navigate().back();

       gs.test_login_success(driver, "ANDY", "apple", success);
//       System.out.println(" Passed ANDY login test.");
       driver.navigate().back();

       gs.test_login_success(driver, "bob", "bathtub", success);
//       System.out.println(" Passed bob login test.");
       driver.navigate().back();

       gs.test_login_success(driver, "ChArLeY", "china", success);
//       System.out.println(" Passed ChArLeY login test.");

    }
}
