package com.icarusfrog.fredclient.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Categories {
    private List<Category> categories;
}
