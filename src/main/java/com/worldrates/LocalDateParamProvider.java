package com.worldrates;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;

@Provider
public class LocalDateParamProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (LocalDate.class.equals(rawType)) {
            return new ParamConverter<T>() {
                @Override
                public T fromString(String value) {
                    return value == null ? null : (T) LocalDate.parse(value);
                }

                @Override
                public String toString(T value) {
                    return value == null ? null : value.toString();
                }
            };
        }
        return null;
    }
}
