package pl.dtarnacki.publishersubscriberexample.event;

import retrofit.RetrofitError;

/**
 * Base event class that consumes RetrofitError
 */
public class BaseErrorEvent {

    private final RetrofitError error;

    public BaseErrorEvent(RetrofitError error) {
        this.error = error;
    }

    public String getMessage() {
        return error.getMessage();
    }
}
