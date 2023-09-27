package com.example.store.Backend.rooms;

import com.example.store.Backend.config.SecurityService;
import com.example.store.Backend.enumerations.RoomType;
import com.example.store.Backend.enumerations.Status;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class RoomService implements CrudListener<Room> {
    private final RoomRepository roomRepository;
    private final SecurityService securityService;

    public RoomService(RoomRepository roomRepository, SecurityService securityService) {
        this.roomRepository = roomRepository;
        this.securityService = securityService;
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room add(Room room) {
        room.setCreatedAt(LocalDateTime.now());
        room.setCreatedBy(securityService.getLoggedInUser());
        return roomRepository.save(room);
    }

    @Override
    public Room update(Room room) {
        room.setUpdatedAt(LocalDateTime.now());
        room.setUpdatedBy(securityService.getLoggedInUser());
        return roomRepository.save(room);
    }

    @Override
    public void delete(Room room) {
        roomRepository.delete(room);

    }
    public Collection<Room> findAllByRoomStatus(Status roomStatus) {
        return roomRepository.findAllByRoomStatus(roomStatus);
    }
    public Collection<Room> findAllByRoomType(String roomType) {
        return roomRepository.findAllByRoomType(roomType);
    }

    public int findRoomCapacity(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber).getRoomCapacity();
    }
    public Collection<Room> findAllByRoomStatusAndRoomType(Status roomStatus, RoomType roomType) {
        return roomRepository.findAllByRoomStatusAndRoomType(roomStatus, roomType);

    }

    public boolean isRoomAvailable(RoomType roomType) {
        return findAllByRoomStatusAndRoomType(Status.AVAILABLE, roomType).isEmpty();
    }

    public void updateCapacity(String roomNumber, int numberOfBeds) {

        Room room = roomRepository.findByRoomNumber(roomNumber);
        room.setRoomCapacity(room.getRoomCapacity()-numberOfBeds);
        if(room.getRoomCapacity()==0){
            room.setRoomStatus(Status.NOT_AVAILABLE);
        }

        roomRepository.save(room);
    }

    public double findRoomPrice(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber).getRoomPrice();
    }

    public List<String> getAvailableRooms() {
        Collection<Room> rooms = roomRepository.findAllByRoomStatus(Status.AVAILABLE);
        return rooms.stream().map(Room::getRoomNumber).toList();
    }
}
