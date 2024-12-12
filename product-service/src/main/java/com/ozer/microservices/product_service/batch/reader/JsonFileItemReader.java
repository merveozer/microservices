package com.ozer.microservices.product_service.batch.reader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

public class JsonFileItemReader<T> implements Iterator<T>{
    private final Iterator<T> iterator;

    public JsonFileItemReader(String fileName, Class<T> type) {
        try {
            Gson gson = new Gson();
            Type listType = TypeToken.getParameterized(List.class, type).getType();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: " + fileName);
            }
            Reader reader = new InputStreamReader(inputStream);
            List<T> items = gson.fromJson(reader, listType);
            this.iterator = items.iterator();
        } catch (Exception e) {
            throw new RuntimeException("Error reading JSON file", e);
        }
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        return iterator.next();
    }
}
