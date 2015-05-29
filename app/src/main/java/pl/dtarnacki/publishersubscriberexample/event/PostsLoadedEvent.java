package pl.dtarnacki.publishersubscriberexample.event;

import java.util.List;

import pl.dtarnacki.publishersubscriberexample.model.Post;

/**
 * Event that contain fetched list of post from server
 */
public class PostsLoadedEvent {

    private final List<Post> list;

    public PostsLoadedEvent(List<Post> list) {
        this.list = list;
    }

    public List<Post> getList() {
        return list;
    }
}
