package com.example.IronMan.DTOS;

public record MaterialRequest(
        String name,
        String unit,
        double stockQuantity
) {
}
