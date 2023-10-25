package club.hellin.objects.impl;

import club.hellin.objects.RedditObject;
import club.hellin.utils.Utils;
import lombok.Getter;
import org.json.JSONObject;

@Getter
public final class Post implements RedditObject {
    private final JSONObject originalJson;

    private final String subreddit;
    private final String selftext;
    private final String authorFullName;
    private final boolean saved;
    private final String modReasonTitle;
    private final int gilded;
    private final boolean clicked;
    private final String title;
    private final String subredditNamePrefixed;
    private final boolean hidden;
    private final int pwls;
    private final int downs;
    private final String name;
    private final boolean quarantine;
    private final String linkFlairTextColor;
    private final int upvoteRatio;
    private final String authorFlairBackgroundColor;
    private final String subredditType;
    private final int ups;
    private final int totalRewardsReceived;
    private final boolean isOriginalContent;
    private final int score;
    private final boolean edited;
    private final String selftextHtml;
    private final String id;
    private final String permalink;

    /**
     * Constructor to load Post from a JSONObject
     * @param obj
     */
    public Post(final JSONObject obj) {
        this.originalJson = Utils.clone(obj);

        this.subreddit = obj.optString("subreddit", null);
        this.selftext = obj.optString("selftext", null);
        this.authorFullName = obj.optString("author_full_name", null);
        this.saved = obj.optBoolean("saved");
        this.modReasonTitle = obj.optString("mod_reason_title", null);
        this.gilded = obj.optInt("gilded");
        this.clicked = obj.optBoolean("clicked");
        this.title = obj.optString("title", null);
        this.subredditNamePrefixed = obj.optString("subreddit_name_prefixed", null);
        this.hidden = obj.optBoolean("hidden");
        this.pwls = obj.optInt("pwls");
        this.downs = obj.optInt("downs");
        this.name = obj.optString("name", null);
        this.quarantine = obj.optBoolean("quarantine");
        this.linkFlairTextColor = obj.optString("link_flair_text_color", null);
        this.upvoteRatio = obj.optInt("upvote_ratio");
        this.authorFlairBackgroundColor = obj.optString("author_flair_background_color", null);
        this.subredditType = obj.optString("subreddit_type", null);
        this.ups = obj.optInt("ups");
        this.totalRewardsReceived = obj.optInt("total_rewards_received");
        this.isOriginalContent = obj.optBoolean("is_original_content");
        this.score = obj.optInt("score");
        this.edited = obj.optBoolean("edited");
        this.selftextHtml = obj.optString("selftext_html", null);
        this.id = obj.optString("id", null);
        this.permalink = obj.optString("permalink", null);
    }

    @Override
    public JSONObject toJson() {
        final JSONObject obj = Utils.clone(this.originalJson);
        return obj;
    }

    /**
     * Convert object a much simpler object with only key information
     * @return
     */
    public BasicPost toBasic() {
        final BasicPost basicPost = new BasicPost(this.subreddit, this.title, this.selftext);
        return basicPost;
    }
}