package com.example.store.Backend.rooms;

import com.example.store.Backend.config.SecurityService;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.time.LocalDateTime;
import java.util.Collection;
@Service
public class RoomService implements CrudListener<Room> {
    private final RoomRepository roomRepository;
    private final SecurityService securityService;

    public RoomService(RoomRepository roomRepository, SecurityService securityService) {
        this.roomRepository = roomRepository;
        this.securityService = securityService;
    }

    @Override
    public Collection<Room> findAll() {
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
    public Collection<Room> findAllByRoomStatus(String roomStatus) {
        return roomRepository.findAllByRoomStatus(roomStatus);
    }
    public Collection<Room> findAllByRoomType(String roomType) {
        return roomRepository.findAllByRoomType(roomType);
    }

    public Collection<Room> findAllByRoomCapacity(int roomCapacity) {
        return roomRepository.findAllByRoomCapacity(roomCapacity);
    }
    public Collection<Room> findAllByRoomStatusAndRoomType(String roomStatus, String roomType) {
        return roomRepository.findAllByRoomStatusAndRoomType(roomStatus, roomType);

    }

}
