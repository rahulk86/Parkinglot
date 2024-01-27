package com.parkinglot.strategis;

import com.parkinglot.modal.VehicleType;

public class FeeCalculationStrategyFactory {

   public static FeeCalculationStrategy getFeeCalculationStrategy(VehicleType vehicleType){
        switch (vehicleType){
           case BUS -> {return new BusFeeCalculationStrategy();}
           case CAR -> {return new CarFeeCalculationStrategy();}
           case BIKE -> {return new BikeFeeCalculationStrategy();}
           default ->  {
              return null;
           }
        }
   }
}
