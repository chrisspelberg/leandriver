package nl.spelberg.selenium.leandriver;

import java.util.Set;
import org.openqa.selenium.WebDriver;

public class LeanWebDriver extends LeanSearchContext implements WebDriver {

    private final WebDriver webDriver;

    public LeanWebDriver(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                webDriver.quit();
            }
        }));

    }

    @Override
    public void get(String url) {
        webDriver.get(url);
    }

    @Override
    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return webDriver.getTitle();
    }

    @Override
    public String getPageSource() {
        return webDriver.getPageSource();
    }

    @Override
    public void close() {
        webDriver.close();
    }

    @Override
    public void quit() {
        webDriver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return webDriver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return webDriver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return webDriver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return webDriver.navigate();
    }

    @Override
    public Options manage() {
        return webDriver.manage();
    }
}
