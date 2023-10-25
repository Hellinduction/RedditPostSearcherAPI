package club.hellin.objects.impl;

import club.hellin.objects.Encrypt;
import club.hellin.objects.RedditObject;
import club.hellin.utils.Encryption;
import club.hellin.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONObject;

import java.time.Instant;

@Getter
@AllArgsConstructor
@Encrypt
public final class RedditAccessToken implements RedditObject {
    private final String accessToken;
    private final long expiresAt;

    /**
     * Constructor to load RedditAccessToken from an encrypted json string
     * @param data
     */
    public RedditAccessToken(final String data) {
        final String json = Encryption.decrypt(data);
        final JSONObject obj = Utils.strToObj(json);

        this.accessToken = obj.getString("access_token");
        final long expiresIn = obj.getLong("expires_in");
        this.expiresAt = Instant.now().getEpochSecond() + expiresIn;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject obj = new JSONObject();

        obj.put("access_token", this.accessToken);
        obj.put("expires_in", this.expiresAt);

        return obj;
    }

    public boolean isExpired() {
        final long now = Instant.now().getEpochSecond();
        return now >= this.expiresAt;
    }
}