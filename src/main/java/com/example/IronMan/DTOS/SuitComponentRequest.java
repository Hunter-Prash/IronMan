package com.example.IronMan.DTOS;

import java.util.UUID;

public record SuitComponentRequest(
        UUID componentId,
        Integer quantity
) {}