package com.example.dbem.dto.open.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Item {
    private String itemName;

    public static String toString(Item item) {
        return item.itemName;
    }
}
