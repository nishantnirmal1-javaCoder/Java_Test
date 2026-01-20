package com.cartrawler.assessment.car;


public enum CarCategory {
    MINI(0),
    ECONOMY(1),
    COMPACT(2),
    OTHER(3);

    public final int rank;

    CarCategory(int rank) {
        this.rank = rank;
    }

    public static CarCategory fromSipp(String sipp) {
        if (sipp == null || sipp.isEmpty()) return OTHER;
        return switch (sipp.charAt(0)) {
            case 'M' -> MINI;
            case 'E' -> ECONOMY;
            case 'C' -> COMPACT;
            default -> OTHER;
        };
    }
    
  
}
