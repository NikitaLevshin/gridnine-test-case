package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;

import java.time.Duration;

public class BigLandedTime implements Filter {

    @Override
    public boolean validation(Flight flight) {
        long timeOnLand = 0L;
        if (flight.getSegments().size() == 1) {
            return true;
        } else {
            for (int i = 0; i < flight.getSegments().size() - 1; i++) {
                Duration landDuration = Duration.between(flight.getSegments().get(i).getArrivalDate(),
                        flight.getSegments().get(i + 1).getDepartureDate());
                timeOnLand += landDuration.toHours();
                if (timeOnLand > 2) {
                    return false;
                }
            }
        }
        return true;
    }
}
