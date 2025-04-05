package com.example.football.util;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdaptor extends XmlAdapter<String, LocalDate> {
    private final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate unmarshal(String s) throws Exception {
        return LocalDate.parse(s, inputFormatter);
    }

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return localDate.format(outputFormatter);
    }
}
