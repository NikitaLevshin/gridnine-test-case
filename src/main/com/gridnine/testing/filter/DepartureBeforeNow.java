package com.gridnine.testing.filter;

import com.gridnine.testing.model.Segment;
import com.gridnine.testing.model.Flight;

import java.time.LocalDateTime;

public class DepartureBeforeNow implements Filter {
    @Override
    public boolean validation(Flight flight) {
        for (Segment segment : flight.getSegments()) {
            if (segment.getDepartureDate().isBefore(LocalDateTime.now())) {
                return false;
            }
        }
        return true;
    }
}
