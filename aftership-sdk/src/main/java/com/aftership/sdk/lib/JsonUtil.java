package com.aftership.sdk.lib;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public final class JsonUtil {

    public static <T> T parseJson(String json, Class<T> tClass) {
        if (StrUtil.isBlank(json)) {
            return null;
        }
        return create().fromJson(json, tClass);
    }

    public static Gson create() {
        return create(false);
    }

    public static Gson create(boolean pretty) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new GsonDateDeSerializer());
        if (pretty) {
            builder.setPrettyPrinting();
        }
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.create();
    }

    public static class GsonDateDeSerializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            String dateString = json.getAsJsonPrimitive().getAsString();
            return parseDate(dateString);
        }

        private Date parseDate(String dateString) {
            if (StrUtil.isBlank(dateString)) {
                return null;
            }

            String[] formats = new String[]{DateUtil.FORMAT_WITH_T, DateUtil.FORMAT_WITHOUT_T, DateUtil.FORMAT_WITH_Z,
                    DateUtil.FORMAT_WITH_X};
            for (String item : formats) {
                Optional<Date> optionalDate = DateUtil.parse(item, dateString);
                if (optionalDate.isPresent()) {
                    return optionalDate.get();
                }
            }

            return null;
        }
    }
}
