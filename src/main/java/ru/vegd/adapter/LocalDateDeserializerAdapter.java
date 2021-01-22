package ru.vegd.adapter;

import com.google.gson.*;
import ru.vegd.entity.Employee;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateDeserializerAdapter implements JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
    }
}
