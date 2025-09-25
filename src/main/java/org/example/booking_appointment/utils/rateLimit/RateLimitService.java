package org.example.booking_appointment.utils.rateLimit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RateLimitService {

    private final Cache<String, Bucket> cache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(Duration.ofHours(2)) // Cleanup po 2h
            .build();

    public boolean isAllowed(String key, int requests, Duration duration) {
        //Bucket bucket = cache.get(key, k -> createBucket(requests, duration));
        Bucket bucket = cache.get(key, k -> Bucket.builder()
                .addLimit(createBucket(requests, duration))
                .build());
        return bucket.tryConsume(1);
    }

    private Bandwidth createBucket(int requests, Duration duration) {
        return Bandwidth.builder()
                .capacity(requests)
                .refillIntervally(requests, duration)
                .build();
    }
}