package com.gridnine.testing;

import com.gridnine.testing.filter.ArrivalBeforeDeparture;
import com.gridnine.testing.filter.BigLandedTime;
import com.gridnine.testing.filter.DepartureBeforeNow;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.util.FilterBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterTest {
    LocalDateTime threeDaysFromNow;
    List<Flight> unfiltered;
    FilterBuilder filterBuilder;

    @BeforeEach
    void beforeEach() {
        filterBuilder = new FilterBuilder();
        threeDaysFromNow = LocalDateTime.now().plusDays(3);
        unfiltered = Arrays.asList(
                //A normal flight with two hour duration
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                //A normal multi segment flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                //A flight departing in the past
                createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                //A flight that departs before it arrives
                createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),
                //A flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                //Another flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                        threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
    }

    @Test
    public void shouldExcludeFlightsThatHaveArrivalTimeBeforeDeparture() {
        filterBuilder.setFilter(new ArrivalBeforeDeparture());
        List<Flight> filtered = filterBuilder.getFiltered(unfiltered);
        List<Flight> expected = Arrays.asList(createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                        threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
        assertEquals(expected, filtered);
    }

    @Test
    public void shouldExcludeFlightsThatHaveTimeOnTheGroundMoreThan2Hours() {
        filterBuilder.setFilter(new BigLandedTime());
        List<Flight> filtered = filterBuilder.getFiltered(unfiltered);
        List<Flight> expected = Arrays.asList(
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)));
        assertEquals(expected, filtered);
    }

    @Test
    public void shouldExcludeFlightsThatHaveDepartureBeforeNow() {
        filterBuilder.setFilter(new DepartureBeforeNow());
        List<Flight> filtered = filterBuilder.getFiltered(unfiltered);
        List<Flight> expected = Arrays.asList(
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                        threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
        assertEquals(expected, filtered);
    }

    private static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}
