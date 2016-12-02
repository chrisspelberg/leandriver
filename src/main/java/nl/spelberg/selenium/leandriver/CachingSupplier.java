package nl.spelberg.selenium.leandriver;

import java.util.Objects;
import java.util.function.Supplier;

public class CachingSupplier<T> implements Supplier<T> {

    private final Supplier<T> supplier;
    private T cachedValue;

    public CachingSupplier(Supplier<T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        this.supplier = supplier;
    }

    @Override
    public synchronized T get() {
        if (this.cachedValue == null) {
            this.cachedValue = supplier.get();
        }
        return this.cachedValue;
    }

    private synchronized void invalidate() {
        this.cachedValue = null;
        invalidate(supplier);
    }


    public static <T> void invalidate(Supplier<T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        if (supplier instanceof CachingSupplier) {
            ((CachingSupplier) supplier).invalidate();
        }
    }
}