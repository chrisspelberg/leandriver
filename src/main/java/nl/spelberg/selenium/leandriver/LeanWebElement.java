package nl.spelberg.selenium.leandriver;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class LeanWebElement implements WebElement {
    // , RenderedWebElement, Locatable, FindsByXPath, FindsByLinkText, FindsById, FindsByName, FindsByTagName, FindsByClassName, SearchContext {

    private static final int RETRY_MILLIS = 100;

    private static final ThreadLocal<Integer> maxWaitMillisLocal = ThreadLocal.withInitial(() -> 1000);

    private final CachingSupplier<WebElement> webElementSupplier;

    public LeanWebElement(Supplier<WebElement> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        this.webElementSupplier = new CachingSupplier<>(supplier);
        // hit cache to 'fake' original findByXXX() behaviour
        this.webElementSupplier.get();
    }

    private synchronized WebElement webElement() {
        return webElementSupplier.get();
    }

    public static <T> void withMaxWait(int maxWaitMillis, Runnable block) {
        withMaxWait(maxWaitMillis, () -> {
            block.run();
            return null;
        });
    }

    public static <T> T withMaxWait(int maxWaitMillis, Supplier<T> supplier) {
        Integer previousMaxWaitMillis = maxWaitMillisLocal.get();
        maxWaitMillisLocal.set(maxWaitMillis);
        try {
            return supplier.get();
        } finally {
            maxWaitMillisLocal.set(previousMaxWaitMillis);
        }
    }

    private static void perform(Runnable block) {
        perform(() -> {
            block.run();
            return null;
        });
    }

    private static <T> T perform(Supplier<T> block) {
        return perform(block, maxWaitMillisLocal.get(), RETRY_MILLIS);
    }

    private static <T> T perform(Supplier<T> block, int maxWaitMillis, int retryMillis) {
        long untilTimeMillis = System.currentTimeMillis() + maxWaitMillis;
        RuntimeException runtimeException = null;
        while (System.currentTimeMillis() < untilTimeMillis) {
            try {
                return block.get();
            } catch (WebDriverException e) {
                runtimeException = e;
                CachingSupplier.invalidate(block);
            }
            Util.sleep(retryMillis);
        }
        throw new TimeoutException("Timed out after " + maxWaitMillis + " ms", runtimeException);
    }

    @Override
    public void click() {
        perform(() -> webElement().click());
    }

    @Override
    public void submit() {
        perform(() -> webElement().submit());
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        perform(() -> webElement().sendKeys(keysToSend));
    }

    @Override
    public void clear() {
        perform(() -> webElement().clear());
    }

    @Override
    public String getTagName() {
        return perform(() -> webElement().getTagName());
    }

    @Override
    public String getAttribute(String name) {
        return perform(() -> webElement().getAttribute(name));
    }

    @Override
    public boolean isSelected() {
        return perform(() -> webElement().isSelected());
    }

    @Override
    public boolean isEnabled() {
        return perform(() -> webElement().isEnabled());
    }

    @Override
    public String getText() {
        return perform(() -> webElement().getText());
    }

    @Override
    public List<WebElement> findElements(By by) {
        return perform(() -> webElement().findElements(by));
    }

    @Override
    public WebElement findElement(By by) {
        return perform(() -> webElement().findElement(by));
    }

    @Override
    public boolean isDisplayed() {
        return perform(() -> webElement().isDisplayed());
    }

    @Override
    public Point getLocation() {
        return perform(() -> webElement().getLocation());
    }

    @Override
    public Dimension getSize() {
        return perform(() -> webElement().getSize());
    }

    @Override
    public Rectangle getRect() {
        return perform(() -> webElement().getRect());
    }

    @Override
    public String getCssValue(String propertyName) {
        return perform(() -> webElement().getCssValue(propertyName));
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return perform(() -> webElement().getScreenshotAs(target));
    }
}
