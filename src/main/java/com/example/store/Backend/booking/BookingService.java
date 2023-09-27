package com.example.store.Backend.booking;

import com.example.store.Backend.config.SecurityService;
import com.example.store.Backend.rooms.RoomService;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.logging.Logger;

@Service
public class BookingService implements CrudListener<Booking> {
    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final SecurityService securityService;
    private static final Logger LOGGER = Logger.getLogger(BookingService.class.getName());

    public BookingService(BookingRepository bookingRepository, RoomService roomService, SecurityService securityService) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
        this.securityService = securityService;
    }

    @Override
    public Collection<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking add(Booking booking) {
        try {


            if (roomService.isRoomAvailable(booking.getRoomType()) &&
                    booking.getNumberOfBeds() <= roomService.findRoomCapacity(booking.getRoomNumber())) {

                booking.setCreatedBy(securityService.getLoggedInUser());
                booking.setCreatedAt(LocalDateTime.now());
                booking.setInvoiceAmount(roomService.findRoomPrice(booking.getRoomNumber()) * booking.getNumberOfDays());
                booking.setBalance(booking.getInvoiceAmount() - booking.getAmountPaid());
                booking.setCheckedIn(false);

                LOGGER.info("Room is available");

                roomService.updateCapacity(booking.getRoomNumber(), booking.getNumberOfBeds());
                return bookingRepository.save(booking);

            }
        } catch (Exception e) {
            throw new RuntimeException("Room not available");
        }
        return null;
    }

    @Override
    public Booking update(Booking booking) {
        booking.setUpdatedBy(securityService.getLoggedInUser());
        booking.setUpdatedAt(LocalDateTime.now());
        return bookingRepository.save(booking);

    }

    @Override
    public void delete(Booking booking) {
        bookingRepository.delete(booking);

    }
}
