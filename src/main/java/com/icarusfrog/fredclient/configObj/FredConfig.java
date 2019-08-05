package com.icarusfrog.fredclient.configObj;

import lombok.Data;

@Data
public class FredConfig {
    private String apiKey = "InsertKeyHere";
    private String baseUri = "https://api.stlouisfed.org/fred";
}
