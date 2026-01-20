package com.cartrawler.assessment.view;

import com.cartrawler.assessment.car.CarResult;

import java.util.List;
import java.util.Set;

public class Display {
    public void render(Set<CarResult> cars) {
    	int i = 0;
        for (CarResult car : cars) {
            System.out.println (i++ +": "+car.toString());
        }
    }
    
    public void renderList(List<CarResult> cars) {
    	int i = 0;
        for (CarResult car : cars) {
            System.out.println (i++ +": "+car.toString());
        }
    }
}
