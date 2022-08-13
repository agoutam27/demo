package com.test.demo.config;

import com.test.demo.dto.RoomDto;
import com.test.demo.util.CommonUtil;
import com.test.demo.util.Tuple;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties
@PropertySource(value = "classpath:rooms.yml", factory = YmlPropertySourceFactory.class)
public class RoomsDB {

    @Setter(AccessLevel.PACKAGE)
    private List<RoomDto> roomsDb;

    private final Map<Integer, List<Tuple<Date>>> roomBookings = new HashMap<>();

    public List<RoomDto> getRoomsDb() {
        return new ArrayList<>(Collections.unmodifiableCollection(roomsDb));
    }

    public boolean doesRoomExist(int roomId) {
        return roomsDb.stream().anyMatch(dto -> dto.getId() == roomId);
    }

    public void bookRoom(int roomId, Date startDate, Date endDate) {
        List<Tuple<Date>> bookingTimes = roomBookings.getOrDefault(roomId, new ArrayList<>());

        for (Tuple<Date> bookingTime : bookingTimes) {
            if (dateInMid(startDate, bookingTime) || dateInMid(endDate, bookingTime)) {
                throw new IllegalArgumentException("cannot book room for given time range");
            }
        }

        CommonUtil.addInSortedOrder(bookingTimes, new Tuple<>(startDate, endDate),
            Comparator.comparing(Tuple::getFirst));

        roomBookings.put(roomId, bookingTimes);

    }

    private boolean dateInMid(Date date, Tuple<Date> tuple) {
        return date.after(tuple.getFirst()) && date.before(tuple.getSecond());
    }

    public boolean isRoomAvailable(int roomId, Date startDate, Date endDate) {
        if (!roomBookings.containsKey(roomId)) {
            return true;
        }
        List<Tuple<Date>> bookingTimes = roomBookings.get(roomId);
        for (Tuple<Date> bookingTime : bookingTimes) {
            if (dateInMid(startDate, bookingTime) || dateInMid(endDate, bookingTime)) {
                return false;
            }
        }
        return true;
    }

    public Set<RoomDto> allAvailableRooms(Date startDate, Date endDate) {
        Set<RoomDto> rooms = new HashSet<>();
        for (RoomDto room : roomsDb) {
            if (isRoomAvailable(room.getId(), startDate, endDate)) {
                rooms.add(room);
            }
        }
        return rooms;
    }
}
