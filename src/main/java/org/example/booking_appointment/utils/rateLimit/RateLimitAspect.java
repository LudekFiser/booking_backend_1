package org.example.booking_appointment.utils.rateLimit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.booking_appointment.service.AuthService;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class RateLimitAspect {

    private final RateLimitService rateLimitService;

    private final AuthService authService;

    /*@Around("@annotation(rateLimit)")
    public Object handleRateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {

        // 1) Aktuální profil (vyhodí, pokud není přihlášen)
        var profile = authService.getCurrentProfile();

        // 2) Jedinečný klíč pro limit
        String key = rateLimit.keyPrefix() + ":" + profile.getId();

        // 3) Převod na Duration
        Duration duration = Duration.of(
                rateLimit.timeAmount(),
                convertToChronoUnit(rateLimit.timeUnit())
        );

        // 4) Kontrola limitu
        if (!rateLimitService.isAllowed(key, rateLimit.requests(), duration)) {
            String timeText = formatTimeUnit(rateLimit.timeAmount(), rateLimit.timeUnit());
            throw new TooManyAttemptsException(
                    String.format("Too many requests. Maximum %d requests per %s allowed.",
                            rateLimit.requests(), timeText)
            );
        }

        log.info("Rate limit check passed for key: {}", key);
        return joinPoint.proceed();
    }

    private ChronoUnit convertToChronoUnit(TimeUnit timeUnit) {
        return switch (timeUnit) {
            case MINUTES -> ChronoUnit.MINUTES;
            case HOURS -> ChronoUnit.HOURS;
            case DAYS -> ChronoUnit.DAYS;
            case SECONDS -> ChronoUnit.SECONDS;
            default -> ChronoUnit.HOURS;
        };
    }

    private String formatTimeUnit(int amount, TimeUnit unit) {
        String unitText = switch (unit) {
            case MINUTES -> amount == 1 ? "minute" : "minutes";
            case HOURS -> amount == 1 ? "hour" : "hours";
            case DAYS -> amount == 1 ? "day" : "days";
            case SECONDS -> amount == 1 ? "second" : "seconds";
            default -> "hours";
        };
        return amount + " " + unitText;
    }*/
}