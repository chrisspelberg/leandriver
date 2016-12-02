package nl.spelberg.selenium.leandriver;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LeanWebDriverTest {

    private LeanWebDriver webDriver;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGoogle() {
        System.setProperty("webdriver.chrome.driver", "src/test/bin/chromedriver");
        ChromeDriver nativeDriver = new ChromeDriver();

//        System.setProperty("webdriver.gecko.driver", "src/test/bin/geckodriver");
//        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//        capabilities.setCapability("marionette", true);
//        FirefoxDriver nativeDriver = new FirefoxDriver(capabilities);

//        HtmlUnitDriver nativeDriver = new HtmlUnitDriver(false);

        webDriver = new LeanWebDriver(nativeDriver);
        webDriver.get("http://google.com");

        WebElement root = webDriver.findElement(By.xpath("/html"));
        System.out.println("root.getTagName() = " + root.getTagName());
        System.out.println("root.getText() = " + root.getText());

        WebElement element = webDriver.findElement(By.id("lst-ib"));
        element.click();
//        assertTrue(element.isSelected());
        element.clear();
        element.sendKeys("Hello World");

        LeanWebElement.withMaxWait(30000, () -> {
            WebElement button = this.webDriver.findElement(By.name("btnK"));
            button.click();
        });

        Util.sleep(10000);
    }

    public static void main(String[] args) {
        new LeanWebDriverTest().testGoogle();
    }
}