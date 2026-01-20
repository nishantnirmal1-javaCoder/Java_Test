package com.cartrawler.assessment.car;

import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import io.vavr.collection.HashSet;

import java.math.BigDecimal;

public final class CarSorterVavr {

    private static final Set<String> CORPORATE_SUPPLIERS =
            HashSet.of(
                "AVIS", "BUDGET", "ENTERPRISE",
                "FIREFLY", "HERTZ", "SIXT", "THRIFTY"
            );

    private CarSorterVavr() {
        // utility class
    }

    public static java.util.List<CarResult> sort(java.util.Set<CarResult> input) {

        return List.ofAll(input)        		
        		.sortBy(c -> sortKey(c))
                .toJavaList();
        
    }

    /**
     * Sort key:
     *  1. non-corporate last
     *  2. category order
     *  3. price low → high
     */
    private static Tuple3<Boolean, CarCategory, Double> sortKey(CarResult car) {
        return Tuple.of(
                !isCorporate(car),
                categoryFromSipp(car.getSippCode()),
                car.getRentalCost()
        );
    }

    private static boolean isCorporate(CarResult car) {
        return CORPORATE_SUPPLIERS.contains(
                car.getSupplierName().toUpperCase()
        );
    }

    private static CarCategory categoryFromSipp(String sipp) {
        if (sipp == null || sipp.isEmpty()) {
            return CarCategory.OTHER;
        }
        return switch (sipp.charAt(0)) {
            case 'M' -> CarCategory.MINI;
            case 'E' -> CarCategory.ECONOMY;
            case 'C' -> CarCategory.COMPACT;
            default  -> CarCategory.OTHER;
        };
    }
}

