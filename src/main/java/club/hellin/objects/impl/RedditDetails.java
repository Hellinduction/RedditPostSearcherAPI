package club.hellin.objects.impl;

import club.hellin.objects.Encrypt;
import club.hellin.objects.RedditObject;
import club.hellin.utils.Encryption;
import club.hellin.utils.Utils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.json.JSONObject;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Encrypt
public final class RedditDetails implements RedditObject {
    private final String username;
    private final String password;
    private final String id;
    private final String secret;

    /**
     * Constructor to load RedditDetails from an encrypted json string
     * @param data
     */
    public RedditDetails(final String data) {
        final String json = Encryption.decrypt(data);
        final JSONObject obj = Utils.strToObj(json);

        this.username = obj.getString("username");
        this.password = obj.getString("password");
        this.id = obj.getString("id");
        this.secret = obj.getString("secret");
    }

    @Override
    public JSONObject toJson() {
        final JSONObject obj = new JSONObject();

        obj.put("username", this.username);
        obj.put("password", this.password);
        obj.put("id", this.id);
        obj.put("secret", this.secret);

        return obj;
    }
}