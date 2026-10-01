package com.example.IronMan.DTOS;

import java.util.UUID;

public record MaterialResponse(
        UUID id,
        String name,
        String unit
) {
}
