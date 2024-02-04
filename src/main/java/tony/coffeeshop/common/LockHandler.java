package tony.coffeeshop.common;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LockHandler {
        RedissonClient redissonClient;

        static String REDISSON_KEY_PREFIX = "RLOCK_";

        public <T> T runOnLock(String key, Long waitTimeMs, Long leaseTimeMs, Supplier<T> execute) {
            RLock lock = redissonClient.getLock(REDISSON_KEY_PREFIX + key);
            boolean available = false;
            try {
                available = lock.tryLock(waitTimeMs, leaseTimeMs, TimeUnit.MILLISECONDS);
                if (!available) {
                    throw new RuntimeException("time out");
                }
                log.info("get lock success {}", key);
                return execute.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            } finally {
                if (available) {
                   lock.unlock();
                   log.info("lock released {}", key);
                }
            }
        }
}
