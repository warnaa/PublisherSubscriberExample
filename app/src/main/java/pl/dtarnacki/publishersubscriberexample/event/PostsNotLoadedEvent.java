package pl.dtarnacki.publishersubscriberexample.event;

import retrofit.RetrofitError;

/**
 * Event published when fetching from server failed
 */
public class PostsNotLoadedEvent extends BaseErrorEvent {

    public PostsNotLoadedEvent(RetrofitError error) {
        super(error);
    }
}
