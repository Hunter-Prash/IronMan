package com.example.IronMan.DTOS;

import java.util.List;
/*
* {
  "designation": "Mark III",
  "version": "v1.0",
  "components": [
    {
      "componentId": "c111-...",
      "quantity": 1
    },
    {
      "componentId": "c222-...",
      "quantity": 2
    }
  ]
}
*
*
* */
public record SuitRequest(
        String designation,
        String version,
        List<SuitComponentRequest> components
) {}