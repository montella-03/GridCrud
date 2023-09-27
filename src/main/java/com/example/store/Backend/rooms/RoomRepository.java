package com.example.store.Backend.rooms;

import com.example.store.Backend.enumerations.RoomType;
import com.example.store.Backend.enumerations.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.roomStatus = ?1")
    Collection<Room> findAllByRoomStatus(Status roomStatus);
    @Query("SELECT r FROM Room r WHERE r.roomType = ?1")
    Collection<Room> findAllByRoomType(String roomType);
    @Query("SELECT r FROM Room r WHERE r.roomCapacity = ?1")
    Collection<Room> findAllByRoomCapacity(int roomCapacity);
    @Query("SELECT r FROM Room r WHERE r.roomStatus = ?1 AND r.roomType = ?2")
    Collection<Room> findAllByRoomStatusAndRoomType(Status roomStatus, RoomType roomType);

    Room findByRoomNumber(String roomNumber);
}
