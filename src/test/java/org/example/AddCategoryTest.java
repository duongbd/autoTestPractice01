package org.example;

import junit.framework.Assert;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.Calendar;

@Log4j2
public class AddCategoryTest extends BaseTest {

    public void loginCms() {
        driver.get("https://cms.anhtester.com/login");
        log.info(driver.getTitle());
        //login
        driver.findElement(By.id("email")).sendKeys("admin@example.com");
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.cssSelector("div button")).click();
        WebUi.waitForPageLoaded(driver);
    }

    @org.testng.annotations.Test
    public void addNewCateGory() throws InterruptedException {
        loginCms();
        //add new category
        driver.get("https://cms.anhtester.com/admin/categories/create");
        WebUi.waitForPageLoaded(driver);
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
        WebUi.sleep(5);
        driver.findElement(By.cssSelector("div.aiz-file-box-wrap:nth-child(1)")).click();
        driver.findElement(By.cssSelector("div.modal-footer > button")).click();
        WebUi.sleep(3);

        //choose icon
        driver.findElement(By.cssSelector("form > div:nth-of-type(6) div.form-control")).click();
        WebUi.sleep(5);

        driver.findElement(By.cssSelector("div.aiz-file-box-wrap:nth-child(5)")).click();
        driver.findElement(By.cssSelector("div.modal-footer > button")).click();
        WebUi.sleep(3);
        //insert meta title
        driver.findElement(By.cssSelector("form > div:nth-of-type(7) input")).sendKeys(Calendar.getInstance().toString());
        //insert meta description
        driver.findElement(By.cssSelector("form > div:nth-of-type(8) textarea")).sendKeys(Calendar.getInstance().toString());
        //choose Filtering attribute
        new Select(driver.findElement(By.cssSelector("form > div:nth-of-type(9) select"))).selectByVisibleText("Fabric");
        //save category
        driver.findElement(By.cssSelector("form  button:last-child.btn-primary")).click();
        WebUi.waitForPageLoaded(driver);
        Assert.assertTrue( "Can not navigate to Category Details page.", driver.getCurrentUrl().contains("/admin/categories"));

        // verify result
        driver.findElement(By.id("search")).sendKeys(categoryName);
        driver.findElement(By.id("search")).sendKeys(Keys.RETURN);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElements(By.cssSelector("td.footable-first-visible")).forEach(webElement -> {
            if (webElement.getText().equals(categoryName)) {
                Assert.assertTrue(true);
            }
        });
    }
}
