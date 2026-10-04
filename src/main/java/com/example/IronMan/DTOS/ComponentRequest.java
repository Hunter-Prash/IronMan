package com.example.IronMan.DTOS;

import java.util.UUID;

public record ComponentRequest(
        String name,
        String category
) {}