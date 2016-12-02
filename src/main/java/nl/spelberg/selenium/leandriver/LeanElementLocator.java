package nl.spelberg.selenium.leandriver;

import java.lang.reflect.Field;
import java.util.List;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public class LeanElementLocator extends DefaultElementLocator implements ElementLocator {
    public LeanElementLocator(SearchContext searchContext, Field field) {
        super(searchContext, field);
    }

    public LeanElementLocator(SearchContext searchContext, AbstractAnnotations annotations) {
        super(searchContext, annotations);
    }

    @Override
    public WebElement findElement() {
        throw new UnsupportedOperationException("TODO: LeanElementLocator.findElement(...) not implemented");
    }

    @Override
    public List<WebElement> findElements() {
        throw new UnsupportedOperationException("TODO: LeanElementLocator.findElements(...) not implemented");
    }
}
