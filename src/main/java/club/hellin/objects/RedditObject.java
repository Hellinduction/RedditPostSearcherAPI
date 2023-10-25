package club.hellin.objects;

import club.hellin.utils.Encryption;
import club.hellin.utils.Utils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;

public interface RedditObject {
    /**
     * Converts object to json object
     * @return
     */
    JSONObject toJson();

    /**
     * Save object to a file in json format
     * @param file
     */
    default void save(final File file) throws IOException {
        final JSONObject obj = this.toJson();
        String json = Utils.jsonToStr(obj, true);

        final Annotation annotation = this.getClass().getAnnotation(Encrypt.class);
        final boolean shouldEncrypt = annotation != null;

        if (shouldEncrypt)
            json = Encryption.encrypt(json);

        final File parent = file.getParentFile();
        if (!parent.exists())
            parent.mkdirs();

        if (!file.exists())
            file.createNewFile();

        final FileWriter writer = new FileWriter(file);
        writer.write(json);
        writer.close();
    }
}