package club.hellin.objects.impl;

import club.hellin.objects.RedditObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONObject;

@Getter
@AllArgsConstructor
public final class BasicPost implements RedditObject {
    private final String subreddit;
    private final String title;
    private final String selftext;

    /**
     * Constructor to load BasicPost from a JSONObject
     * @param obj
     */
    public BasicPost(final JSONObject obj) {
        this.subreddit = obj.getString("subreddit");
        this.title = obj.getString("title");
        this.selftext = obj.getString("selftext");
    }

    @Override
    public JSONObject toJson() {
        final JSONObject obj = new JSONObject();

        obj.put("subreddit", this.subreddit);
        obj.put("title", this.title);
        obj.put("selftext", this.selftext);

        return obj;
    }
}