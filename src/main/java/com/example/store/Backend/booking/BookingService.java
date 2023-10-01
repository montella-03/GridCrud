package com.example.store.Backend.booking;

import com.example.store.Backend.config.SecurityService;
import com.example.store.Backend.enumerations.Status;
import com.example.store.Backend.rooms.Room;
import com.example.store.Backend.rooms.RoomService;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
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

          Room room = roomService.findByRoomNumber(booking.getRoomNumber());
          if(room.getRoomType().equals(booking.getRoomType()) && room.getRoomCapacity() >= booking.getNumberOfBeds()) {

              booking.setCreatedAt(LocalDateTime.now());
              booking.setCreatedBy(securityService.getLoggedInUser());
              booking.setInvoiceAmount(room.getRoomPrice() * booking.getNumberOfBeds());
              booking.setBalance(booking.getInvoiceAmount() - booking.getAmountPaid());
              booking.setNumberOfDays(booking.getDepartureDate().getDayOfYear() - booking.getArrivalDate().getDayOfYear());
              booking.setCheckedIn(false);

                if(room.getRoomCapacity()==booking.getNumberOfBeds()){
                    room.setRoomStatus(Status.NOT_AVAILABLE);
                }
              room.setRoomCapacity(room.getRoomCapacity() - booking.getNumberOfBeds());
              roomService.update(room);
              return bookingRepository.save(booking);


          }
          else{
              LOGGER.severe("Room type or capacity does not match");
                return null;
            }
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

    public List<Booking> findTodayGuests() {

        return bookingRepository.findTodayGuests(LocalDate.now());


    }
}
