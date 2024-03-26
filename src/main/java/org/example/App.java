package org.example;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.util.Calendar;

/**
 * Hello world!
 *
 */
@Log4j2
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        ChromeOptions chromeOptions = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "driver/win/chrome/chromedriver.exe");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().setSize(new Dimension(700, 700));
        log.info("Started driver");
        driver.get("https://cms.anhtester.com/login");
        log.info(driver.getTitle());
        //login
        driver.findElement(By.id("email")).sendKeys("admin@example.com");
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.cssSelector("div button")).click();
        //add new category
        driver.get("https://cms.anhtester.com/admin/categories/create");
            //insert name
        String categoryName = "nbd-test-" + Calendar.getInstance().getTimeInMillis();
        driver.findElement(By.id("name")).sendKeys(categoryName);
            //choose parent category
        new Select(driver.findElement(By.cssSelector("form > div:nth-of-type(2) select"))).selectByVisibleText("Sport shoes");
            //insert ordering number
        driver.findElement(By.id("order_level")).sendKeys("5");
            //choose type category
        new Select(driver.findElement(By.cssSelector("form > div:nth-of-type(4) select"))).selectByVisibleText("Physical");
            //choose banner
        driver.findElement(By.cssSelector("form > div:nth-of-type(5) div.form-control")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("div.aiz-file-box-wrap:nth-child(1)")).click();
        driver.findElement(By.cssSelector("div.modal-footer > button")).click();
        Thread.sleep(3000);
            //choose icon
        driver.findElement(By.cssSelector("form > div:nth-of-type(6) div.form-control")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("div.aiz-file-box-wrap:nth-child(5)")).click();
        driver.findElement(By.cssSelector("div.modal-footer > button")).click();
        Thread.sleep(3000);
        //insert meta title
        driver.findElement(By.cssSelector("form > div:nth-of-type(7) input")).sendKeys(Calendar.getInstance().toString());
        //insert meta description
        driver.findElement(By.cssSelector("form > div:nth-of-type(8) textarea")).sendKeys(Calendar.getInstance().toString());
        //choose Filtering attribute
        new Select(driver.findElement(By.cssSelector("form > div:nth-of-type(9) select"))).selectByVisibleText("Fabric");
        //save category
        driver.findElement(By.cssSelector("form  button:last-child.btn-primary")).click();
//        Thread.sleep(5000);

        // verify result
        driver.findElement(By.id("search")).sendKeys(categoryName);
        driver.findElement(By.id("search")).sendKeys(Keys.RETURN);
        Thread.sleep(5000);
        driver.findElements(By.cssSelector("td.footable-first-visible")).forEach(webElement -> {
            if (webElement.getText().equals(categoryName)) {
                log.info("Add new category success");
            }
        });
        driver.quit();
    }
}
