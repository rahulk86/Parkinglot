package com.parkinglot.strategis;

import java.time.LocalDateTime;

public interface FeeCalculationStrategy {
   Double calculateFee(LocalDateTime entryTime,LocalDateTime exitTime);
}
