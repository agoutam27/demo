package com.test.demo.controller;

import static com.test.demo.config.Constant.DATE_PATTERN;

import com.test.demo.dto.RoomDto;
import com.test.demo.dto.RoomSearchDto;
import com.test.demo.service.RoomService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class RoomsController {

    @Autowired
    private RoomService service;

    @PostMapping("/rooms")
    public List<RoomDto> getRooms(@RequestBody RoomSearchDto searchDto) {

        return service.getRooms(searchDto);
    }

    @GetMapping("/rooms/{id}")
    public void bookRoom(
        @PathVariable("id") int roomId,
        @RequestParam @DateTimeFormat(pattern = DATE_PATTERN) Date startDate,
        @RequestParam @DateTimeFormat(pattern = DATE_PATTERN) Date endDate) {

        service.bookRoom(roomId, startDate, endDate);

    }
}
