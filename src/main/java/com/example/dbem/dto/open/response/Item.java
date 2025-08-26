package com.example.dbem.dto.open.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private String itemName;

    public static String toString(Item item) {
        return item.itemName;
    }
}
