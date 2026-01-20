package com.cartrawler.assessment.car;


import java.util.*;
import java.util.stream.Collectors;

import com.cartrawler.assessment.car.CarResult.FuelPolicy;

public class CarSorter {

    private static final Set<String> CORPORATE_SUPPLIERS = Set.of(
        "AVIS", "BUDGET", "ENTERPRISE",
        "FIREFLY", "HERTZ", "SIXT", "THRIFTY"
    );

    public List<CarResult> process(Set<CarResult> cars,
                                   boolean removeExpensiveFullFull) {

        Comparator<CarResult> comparator = buildComparator();

        List<CarResult> sorted =
            cars.stream()
                  .sorted(comparator)
                  .toList();

        if (!removeExpensiveFullFull) {
            return sorted;
        }

        return removeAboveMedianFullFull(sorted, comparator);
    }

    private Comparator<CarResult> buildComparator() {
        return Comparator
                .comparing((CarResult c) -> !isCorporate(c)) // false first
                .thenComparing(c -> CarCategory.fromSipp(c.getSippCode())) // SIPP sorting 
                .thenComparing(CarResult::getRentalCost); // rental cost sorting

    }

    private boolean isCorporate(CarResult c) {
        return Optional.ofNullable(c.getSupplierName()).map(sipp -> CORPORATE_SUPPLIERS.contains(sipp)).orElse(false);
    }

    private record GroupKey(boolean corporate, CarCategory category) {}

    private List<CarResult> removeAboveMedianFullFull(
            List<CarResult> cars,
            Comparator<CarResult> comparator) {

        Map<GroupKey, List<CarResult>> grouped =
            cars.stream()
                .collect(Collectors.groupingBy(
                    c -> new GroupKey(
                        isCorporate(c),
                        CarCategory.fromSipp(c.getSippCode())
                    )
                ));

        return grouped.values().stream()
            .flatMap(group -> {
                double median = median(group);
                return group.stream()
                    .filter(c ->
                        !(c.getFuelPolicy() == FuelPolicy.FULLFULL
                          && c.getRentalCost() > median)
                    );
            })
            .sorted(comparator)
            .toList();
    }

    private double median(List<CarResult> cars) {
        List<Double> prices =
            cars.stream()
                .map(CarResult::getRentalCost)
                .sorted()
                .toList();

        int n = prices.size();
        if (n == 0) return 0;

        return (n % 2 == 1)
            ? prices.get(n / 2)
            : (prices.get(n / 2 - 1) + prices.get(n / 2)) / 2.0;
    }
}

