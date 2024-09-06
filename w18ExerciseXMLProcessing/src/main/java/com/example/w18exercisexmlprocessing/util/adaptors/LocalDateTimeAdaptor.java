package com.example.w18exercisexmlprocessing.util.adaptors;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;

public class LocalDateTimeAdaptor extends XmlAdapter<String, LocalDateTime> {
    @Override
    public LocalDateTime unmarshal(String str) throws Exception {
        return LocalDateTime.parse(str);
    }

    @Override
    public String marshal(LocalDateTime localDateTime) throws Exception {
        return localDateTime.toString();
    }
}
