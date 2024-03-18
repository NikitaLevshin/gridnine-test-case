package com.gridnine.testing.util;

import com.gridnine.testing.filter.Filter;
import com.gridnine.testing.model.Flight;

import java.util.List;
import java.util.stream.Collectors;

public class FilterBuilder {
    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    private Filter filter;


    public List<Flight> getFiltered(List<Flight> flights) {
        return flights.stream()
                .filter(filter::validation)
                .collect(Collectors.toList());
    }
}
