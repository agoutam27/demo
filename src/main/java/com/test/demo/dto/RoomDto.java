package com.test.demo.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {

    private int id;
    private String name;
    private String description;
    private AddressDto address;
    private int capacity;
    private List<String> amenities;
}
