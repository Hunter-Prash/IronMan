package com.example.IronMan.DTOS;

import java.util.UUID;

public record ComponentResponse(
        UUID id,
        String name,
        String category
) {}