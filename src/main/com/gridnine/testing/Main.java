package com.gridnine.testing;

import com.gridnine.testing.filter.ArrivalBeforeDeparture;
import com.gridnine.testing.filter.BigLandedTime;
import com.gridnine.testing.filter.DepartureBeforeNow;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.util.FilterBuilder;
import com.gridnine.testing.util.FlightBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Flight> unfiltered = FlightBuilder.createFlights();
        System.out.println("Список перелётов без фильтров: " + unfiltered);


        FilterBuilder filterBuilder = new FilterBuilder();
        filterBuilder.setFilter(new DepartureBeforeNow());
        System.out.println("Список перелётов с фильтрацией даты вылета до текущего момента: "
                + filterBuilder.getFiltered(unfiltered));

        filterBuilder.setFilter(new ArrivalBeforeDeparture());
        System.out.println("Список перелётов с фильтрацией даты вылёта после даты прилёта: "
                + filterBuilder.getFiltered(unfiltered));

        filterBuilder.setFilter(new BigLandedTime());
        System.out.println("Список перелётов c фильтрацией общего времени проведённого на земле, превышающего 2 часа: "
                + filterBuilder.getFiltered(unfiltered));
    }
}
