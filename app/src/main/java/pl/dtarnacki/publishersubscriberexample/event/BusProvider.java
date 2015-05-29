package pl.dtarnacki.publishersubscriberexample.event;

import com.squareup.otto.Bus;

/**
 * Singleton class for shared event bus
 */
public class BusProvider {

    private static Bus bus = new Bus();

    public static Bus getInstance() {
        return bus;
    }

    private BusProvider() {
    }
}
