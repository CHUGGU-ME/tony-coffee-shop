package tony.coffeeshop.common;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionHandler {

    @Transactional
    public <T> T runOnWriteTransaction(Supplier<T> supplier) {
        return supplier.get();
    }
}
