package club.hellin;

import club.hellin.objects.impl.Post;
import club.hellin.objects.impl.RedditAccessToken;
import club.hellin.objects.impl.RedditDetails;
import club.hellin.utils.Utils;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@UtilityClass
public final class API {
    public final int MAX_POSTS = 1000;

    /**
     * Load RedditDetails from a file
     * @param file
     * @return
     */
    public RedditDetails loadDetails(final File file) throws IOException {
        final String data = new String(Files.readAllBytes(file.toPath()));
        return new RedditDetails(data);
    }

    /**
     * Load RedditAccessToken from a file
     * @param file
     * @return
     * @throws IOException
     */
    public RedditAccessToken loadAccessToken(final File file) throws IOException {
        final String data = new String(Files.readAllBytes(file.toPath()));
        return new RedditAccessToken(data);
    }

    /**
     * Get Reddit Access Token from the API
     * Be cautious about calling this as it is rate limited
     * @param details
     * @return
     */
    public RedditAccessToken getRedditAccessToken(final RedditDetails details) throws IOException {
        final URL url = new URL(String.format("https://www.reddit.com/api/v1/access_token?grant_type=password&username=%s&password=%s", URLEncoder.encode(details.getUsername(), "UTF-8"), URLEncoder.encode(details.getPassword(), "UTF-8")));
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        final String auth = Base64.getEncoder().encodeToString(String.format("%s:%s", details.getId(), details.getSecret()).getBytes());
        conn.setRequestProperty("Authorization", String.format("Basic %s", auth));
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36");

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.getOutputStream().close();

        final InputStream in = conn.getInputStream();
        final String json = IOUtils.toString(in, StandardCharsets.UTF_8);
        in.close();

        final JSONObject obj = Utils.strToObj(json);

        final String accessToken = obj.getString("access_token");
        final long expiresIn = obj.getLong("expires_in");

        final RedditAccessToken token = new RedditAccessToken(accessToken, expiresIn);
        return token;
    }

    /**
     * Retrieves as many posts from a user as possible
     * @param username
     * @param token
     * @return
     */
    public List<Post> retrievePosts(final String username, final RedditAccessToken token) throws IOException {
        final List<Post> posts = new ArrayList<>();
        retrievePosts(posts, username, token, null);
        return posts;
    }

    /**
     * Retrieves as many posts from a user as possible
     * @param posts
     * @param username
     * @param token
     * @param after
     */
    private void retrievePosts(final List<Post> posts, final String username, final RedditAccessToken token, String after) throws IOException {
        String urlStr = String.format("https://oauth.reddit.com/user/%s/submitted?count=100&show=given&sort=new", username);

        if (after != null)
            urlStr += "&after=" + after;

        final URL url = new URL(urlStr);
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36");
        conn.setRequestProperty("Authorization", "Bearer " + token.getAccessToken());

        final InputStream in = conn.getInputStream();
        final String json = IOUtils.toString(in, StandardCharsets.UTF_8);
        in.close();

        final JSONObject obj = Utils.strToObj(json);
        final JSONObject data = obj.getJSONObject("data");

        after = data.optString("after", null);

        final JSONArray childrenArray = data.getJSONArray("children");
        for (final Object object : childrenArray) {
//            if (!(object instanceof JSONObject))
//                continue;

            final JSONObject postJson = (JSONObject) object;
            final Post post = new Post(postJson.getJSONObject("data"));
            posts.add(post);
        }

        if (after == null || posts.size() >= MAX_POSTS)
            return;

        retrievePosts(posts, username, token, after);
    }
}