package com.example.IronMan.DTOS;

import java.util.UUID;

public record SuitComponentResponse(
        UUID componentId,
        String componentName,
        Integer quantity
) {}