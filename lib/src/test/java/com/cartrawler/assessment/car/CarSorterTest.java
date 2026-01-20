package com.cartrawler.assessment.car;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarSorterTest {
	
	private CarSorter carSorter;
	
	private Set<CarResult> CARS ;
	
	
	@BeforeEach
	public void init() {
		carSorter = new CarSorter();
		CARS = new HashSet<>();
		CARS.add(new CarResult("Volkswagen Polo", "NIZA", "EDMR", 12.81d, CarResult.FuelPolicy.FULLEMPTY));
        CARS.add(new CarResult("Ford C-Max Diesel", "NIZA", "CMMD", 22.04d, CarResult.FuelPolicy.FULLEMPTY));
        CARS.add(new CarResult("Volkswagen Polo", "SIXT", "EDMR", 128.93d, CarResult.FuelPolicy.FULLFULL));
        CARS.add(new CarResult("Mercedes A Class", "SIXT", "ICAV", 254.02d, CarResult.FuelPolicy.FULLFULL));
        CARS.add(new CarResult("Toyota Avensis", "AVIS", "IDMR", 373.69d, CarResult.FuelPolicy.FULLFULL));
        CARS.add(new CarResult("Opel Astra", "AVIS", "CCMR", 203.32d, CarResult.FuelPolicy.FULLFULL));
        CARS.add(new CarResult("Peugeot 107", "FIREFLY", "MCMR", 26.57d, CarResult.FuelPolicy.FULLEMPTY));
        CARS.add(new CarResult("Volkswagen Polo", "FIREFLY", "EDMR", 29.79d, CarResult.FuelPolicy.FULLEMPTY));
        CARS.add(new CarResult("Volkswagen Polo", "RHODIUM", "EDMR", 92.9d, CarResult.FuelPolicy.FULLFULL));
        CARS.add(new CarResult("Ford Galaxy", "RHODIUM", "FVMR", 509.37d, CarResult.FuelPolicy.FULLFULL));
        
	}
	
	@Test
	public void testHappyPath() {
		List<CarResult> result = carSorter.process(CARS, false);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(10, result.size());
		Assertions.assertEquals(26.57d,result.get(0).getRentalCost());
		Assertions.assertEquals(29.79d,result.get(1).getRentalCost());
		Assertions.assertEquals(22.04d,result.get(8).getRentalCost());
		Assertions.assertEquals(509.37,result.get(9).getRentalCost());
	}
	
	@Test
	public void testHappyPathWithExpensiveFullFullRemoval() {
		List<CarResult> result = carSorter.process(CARS, true);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(7, result.size());
		Assertions.assertEquals(26.57d,result.get(0).getRentalCost());
		Assertions.assertEquals(29.79d,result.get(1).getRentalCost());
		Assertions.assertEquals(12.81d,result.get(4).getRentalCost());
		Assertions.assertEquals(509.37,result.get(6).getRentalCost());
	}
	@Test
	public void testHappyPathWithNull() {		
		CARS.add(new CarResult("Volkswagen Polo", null, "EDMR", 12.81d, CarResult.FuelPolicy.FULLEMPTY));
		CARS.add(new CarResult("Volkswagen Polo", "NIZA", null, 12.81d, CarResult.FuelPolicy.FULLEMPTY));
		CARS.add(new CarResult("Volkswagen Polo", "NIZA", "EDMR", 12.81d, null));
		List<CarResult> result = carSorter.process(CARS, false);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(13, result.size());
		Assertions.assertEquals(26.57d,result.get(0).getRentalCost());
		Assertions.assertEquals(29.79d,result.get(1).getRentalCost());
		Assertions.assertEquals(null,result.get(8).getSupplierName());
		Assertions.assertEquals(12.81,result.get(11).getRentalCost());
	}

}
