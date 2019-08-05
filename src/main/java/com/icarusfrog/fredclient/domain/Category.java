package com.icarusfrog.fredclient.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Category {
    private Integer id;
    private String name;
    private Integer parentId;
}
