package nl.spelberg.selenium.leandriver;

import java.util.AbstractList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import org.openqa.selenium.WebElement;

public class LeanWebElementList extends AbstractList<WebElement> {

    private final CachingSupplier<List<WebElement>> webElementListSupplier;

    public LeanWebElementList(Supplier<List<WebElement>> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        this.webElementListSupplier = new CachingSupplier<>(supplier);
    }

    @Override
    public WebElement get(int index) {
        return webElementListSupplier.get().get(index);
    }

    @Override
    public int size() {
        return webElementListSupplier.get().size();
    }
}
