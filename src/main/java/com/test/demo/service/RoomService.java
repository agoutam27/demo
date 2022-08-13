package com.test.demo.service;

import com.test.demo.config.RoomsDB;
import com.test.demo.dto.RoomDto;
import com.test.demo.dto.RoomSearchDto;
import com.test.demo.util.CommonUtil;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoomService {

    @Autowired
    private RoomsDB roomsDB;

    public List<RoomDto> getRooms(RoomSearchDto searchDto) {

        log.info("Searching for available rooms");
        if (searchDto.getStartDate() == null || searchDto.getEndDate() == null) {
            log.error("Invalid payload, start date and end date must be present");
            throw new IllegalArgumentException("invalid payload");
        }

        Set<RoomDto> availableRooms = roomsDB.allAvailableRooms(searchDto.getStartDate(), searchDto.getEndDate());

        Stream<RoomDto> roomDtoStream = availableRooms.stream();

        if (searchDto.getCapacity() != null) {
            log.info("Applying capacity filter");
            roomDtoStream = roomDtoStream.filter(dto -> dto.getCapacity() >= searchDto.getCapacity());
        }

        if (CommonUtil.isNotBlank(searchDto.getAmenities())) {
            log.info("Applying amenities filter");
            roomDtoStream = roomDtoStream
                .filter(dto -> CommonUtil.isSubset(dto.getAmenities(), searchDto.getAmenities()));
        }

        if (searchDto.getAddress() != null) {
            log.info("Applying address filter");
            String city = searchDto.getAddress().getCity();
            String addressLine = searchDto.getAddress().getAddressLine();
            Integer floor = searchDto.getAddress().getFloor();

            if (CommonUtil.isNotBlank(city)) {
                log.info("Applying City filter");
                roomDtoStream = roomDtoStream.filter(dto -> Objects.equals(dto.getAddress().getCity(), city));
            }

            if (CommonUtil.isNotBlank(addressLine)) {
                log.info("Applying address line filter");
                roomDtoStream = roomDtoStream
                    .filter(dto -> Objects.equals(dto.getAddress().getAddressLine(), addressLine));
            }

            if (floor != null) {
                log.info("Applying floor filter");
                roomDtoStream = roomDtoStream.filter(dto -> dto.getAddress().getFloor() == floor);
            }
        }

        return roomDtoStream.collect(Collectors.toList());
    }

    public void bookRoom(int roomId, Date startDate, Date endDate) {

        if (!roomsDB.doesRoomExist(roomId)) {
            throw new IllegalArgumentException("Invalid room id");
        }

        if (!roomsDB.isRoomAvailable(roomId, startDate, endDate)) {
            throw new IllegalArgumentException("room not available");
        }

        roomsDB.bookRoom(roomId, startDate, endDate);
    }
}
