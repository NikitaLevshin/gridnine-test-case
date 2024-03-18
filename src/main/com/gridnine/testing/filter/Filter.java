package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;

public interface Filter {
    boolean validation(Flight flight);
}
