package com.parkinglot.strategis;

import java.time.Duration;
import java.time.LocalDateTime;

public class CarFeeCalculationStrategy implements FeeCalculationStrategy{
    private static final double HOURLY_RATE = 8.0;
    @Override
    public Double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime) {
        Duration duration = Duration.between(entryTime, exitTime);
        return duration.toHours() * HOURLY_RATE;
    }
}
