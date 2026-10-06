package com.example.IronMan.Helpers;

import org.hibernate.PessimisticLockException;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

@Component
public class RetryHelper {
    public <T> T executeWithRetry(Supplier<T> action, int maxAttempts) throws InterruptedException {
        int attempts=1;
        while(attempts<=maxAttempts){
            try{
                return action.get();
            } catch (PessimisticLockException | CannotAcquireLockException e) {
                attempts++;
                long backoff = (long) (100 * Math.pow(2, attempts));
                long jitter = ThreadLocalRandom.current().nextLong(0, 50);
                Thread.sleep(backoff + jitter);
            }
        }
        throw new RuntimeException("Server Busy");
    }
}
