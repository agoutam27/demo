package com.test.demo.dto;

import static com.test.demo.config.Constant.DATE_PATTERN;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomSearchDto {

    @JsonFormat(pattern = DATE_PATTERN)
    private Date startDate;

    @JsonFormat(pattern = DATE_PATTERN)
    private Date endDate;

    private Integer capacity;

    private List<String> amenities;

    private AddressDto address;

}
