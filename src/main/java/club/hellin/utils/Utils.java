package club.hellin.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class Utils {
    /**
     * Converts a json object to a string
     * @param obj
     * @return
     * @throws JSONException
     */
    public static String jsonToStr(final JSONObject obj) throws JSONException {
        return Utils.jsonToStr(obj, false);
    }

    /**
     * Converts a json object to a string with the option for it to be pretty
     * @param obj
     * @return
     * @throws JSONException
     */
    public static String jsonToStr(final JSONObject obj, final boolean pretty) throws JSONException {
        if (pretty)
            return obj.toString(4);

        return obj.toString();
    }

    /**
     * Converts a json array to a string
     * @param array
     * @return
     * @throws JSONException
     */
    public static String jsonArrayToStr(final JSONArray array) throws JSONException {
        return Utils.jsonArrayToStr(array, false);
    }

    /**
     * Converts a json array to a string with the option for it to be pretty
     * @param array
     * @param pretty
     * @return
     * @throws JSONException
     */
    public static String jsonArrayToStr(final JSONArray array, final boolean pretty) throws JSONException {
        if (pretty)
            return array.toString(4);

        return array.toString();
    }

    /**
     * Converts a string to a json object
     * @param json
     * @return
     * @throws JSONException
     */
    public static JSONObject strToObj(final String json) throws JSONException {
        return new JSONObject(json);
    }

    /**
     * Converts a string to a json array
     * @param json
     * @return
     * @throws JSONException
     */
    public static JSONArray strToArray(final String json) throws JSONException {
        return new JSONArray(json);
    }

    public static JSONObject clone(final JSONObject obj) {
        final JSONObject clonedJsonObj = new JSONObject();

        for (Object key : obj.keySet()) {
            final String keyStr = (String) key;
            final Object value = obj.get(keyStr);
            clonedJsonObj.put(keyStr, value);
        }

        return clonedJsonObj;
    }
}