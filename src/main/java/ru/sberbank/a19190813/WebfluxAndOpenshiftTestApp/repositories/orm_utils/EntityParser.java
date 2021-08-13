package ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.repositories.orm_utils;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityParser {

    public static String parseTableName(Object obj) {
        Class<?> objClass = obj.getClass();
        return Optional.ofNullable(objClass
                .getAnnotation(Table.class))
                .map(Table::value)
                .orElse(objClass
                        .getSimpleName()
                        .toLowerCase(Locale.ROOT));
    }

    public static String parseFields(Object obj) {
        return parseObject(obj, field -> Optional.ofNullable(field
                .getType()
                .getAnnotation(Column.class))
                .map(Column::value)
                .orElse(field.getName()));
    }

    public static String parseValues(Object obj) {
        return parseObject(obj, field -> {
            try {
                return fieldToString(field, obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public static String parseObject(Object obj, Function<? super Field, String> mapFunc) {
        Class<?> c = obj.getClass();
        return Arrays.stream(c.getDeclaredFields())
                .filter(field -> field.getAnnotation(Transient.class) == null)
                .map(mapFunc)
                .collect(Collectors.joining(", "));
    }

    public static String fieldToString(Field field, Object obj) throws IllegalAccessException {
        field.setAccessible(true);
        Object fieldObj = field.get(obj);
        field.setAccessible(false);
        if (field.getType().getAnnotation(Table.class) != null) {
            Field idField = Arrays.stream(field.getType().getDeclaredFields())
                    .filter(f -> f.getAnnotation(Id.class) != null)
                    .findFirst()
                    .orElse(null);
            String idStr = null;
            if (idField != null) {
                idField.setAccessible(true);
                idStr = idField.get(fieldObj).toString();
                idField.setAccessible(false);
            }
            return idStr;
        } else {
            return String.valueOf(fieldObj);
        }
    }
}
