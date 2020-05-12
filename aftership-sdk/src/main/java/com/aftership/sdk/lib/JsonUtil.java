package com.aftership.sdk.lib;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        private final SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        private final SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                String j = json.getAsJsonPrimitive().getAsString();
                return parseDate(j);
            } catch (ParseException e) {
                throw new JsonParseException(e.getMessage(), e);
            }
        }

        private Date parseDate(String dateString) throws ParseException {
            if (dateString != null && dateString.trim().length() > 0) {
                try {
                    return format1.parse(dateString);
                } catch (ParseException ex) {
                    //ex.printStackTrace();//test:
                    return format2.parse(dateString);
                }
            } else {
                return null;
            }
        }
    }
}
