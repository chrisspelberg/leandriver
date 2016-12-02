package nl.spelberg.selenium.leandriver;

import java.util.List;
import java.util.Objects;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class LeanSearchContext implements SearchContext {

    private final SearchContext originalSearchContext;

    public LeanSearchContext(SearchContext originalSearchContext) {
        Objects.requireNonNull(originalSearchContext, "originalSearchContext is null");
        this.originalSearchContext = originalSearchContext;
    }

    @Override
    public final List<WebElement> findElements(By by) {
        return new LeanWebElementList(() -> originalSearchContext.findElements(by));
    }

    @Override
    public final WebElement findElement(By by) {
        return new LeanWebElement(() -> originalSearchContext.findElement(by));
    }

}
