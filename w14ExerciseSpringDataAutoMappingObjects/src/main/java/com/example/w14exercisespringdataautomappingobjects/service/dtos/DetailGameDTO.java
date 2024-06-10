package com.example.w14exercisespringdataautomappingobjects.service.dtos;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class DetailGameDTO {

    private String title;
    private double price;
    private String description;
   private LocalDate releaseDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return String.format("%s%n%.2f%n%s%n%s%n", title, price, description, releaseDate);
    }
}
