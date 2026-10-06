package com.example.IronMan.DTOS;

import java.util.List;
import java.util.UUID;

public record SuitResponse(
        UUID id,
        String designation,
        String version,
        String status,
        List<SuitComponentResponse> components
) {}