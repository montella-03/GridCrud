package com.example.store.ui;

import com.example.store.Backend.booking.Booking;
import com.example.store.Backend.booking.BookingService;
import com.example.store.Backend.enumerations.Status;
import com.example.store.Backend.rooms.RoomService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route(value = "booking",layout = MainLayout.class)
@RolesAllowed("USER")
public class BookingView extends VerticalLayout {
    private final RoomService roomService;
    private final BookingService bookingService;

    public BookingView(BookingService bookingService, RoomService roomService, BookingService bookingService1){
        this.roomService = roomService;
        this.bookingService = bookingService1;
        GridCrud<Booking> grid = new GridCrud<>(Booking.class,bookingService);
        grid.getGrid().setColumns("name","email","nationality","passportNumber","arrivalDate"
                ,"departureDate","roomType","roomNumber","bedType","numberOfBeds","lockNumber",
                "bookingChannel","invoiceAmount","amountPaid","balance","numberOfDays");
        grid.getGrid().addColumn(booking->booking.isCheckedIn()? "Checked" : "Not Checked").setHeader("Status");
        grid.getGrid().getColumns().forEach(col->col.setWidth("200px"));
//        grid.getGrid().addColumn(booking->booking.isCheckedOut()? "Checked Out" : "Checked In").setHeader("Status");
        grid.setRowCountCaption("%d booking(s) found");
        grid.getCrudFormFactory().setUseBeanValidation(true);
        grid.setAddOperationVisible(false);
        grid.addClassNames("shadow-lg","p-2");
        grid.getCrudLayout().addToolbarComponent(addNewBookingLink());
        grid.setWidth("100%");

        add(
                new H2("Booking"),
                header(),
                grid,
                new HorizontalLayout(
                        new VerticalLayout(),
                        todayGuests()
                )
        );
        addClassName("booking");
    }

    private VerticalLayout todayGuests() {
        Grid<Booking> guests=new Grid<>();
        guests.addColumns("name","nationality","departureDate","amountPaid");
        guests.setItems(bookingService.findTodayGuests());
        return new VerticalLayout(guests);
    }

    private Button addNewBookingLink() {
        var addNewBooking = new Button("Add New Booking",e->getUI().ifPresent(ui -> ui.navigate("/booking/new-booking")));
        addNewBooking.addClassNames("btn","btn-primary","gap-6");
        return addNewBooking;
    }

    private HorizontalLayout header() {
        var div = new Div();
        div.addClassNames("flex","gap-6","p-2","shadow-lg","div");
        div.setWidth("100%");

        div.add(
                AllRooms(),
                AvailableRooms(),
                percentageOfRoomsAvailable()
        );
        return new HorizontalLayout(
                div
        );
    }

    private VerticalLayout percentageOfRoomsAvailable() {

        var verticalLayout = new VerticalLayout();
        var h2 = new H1((roomService.findAllByRoomStatus(Status.AVAILABLE).size() * 100) / roomService.findAll().size() + "%");
        h2.addClassName("number");
        verticalLayout.addClassNames("card");
        verticalLayout.add(
                new VerticalLayout(
                        new H2("Percentage of Rooms Available"),
                        h2
                )
        );
        return verticalLayout;
    }

    private VerticalLayout AvailableRooms() {

        var verticalLayout = new VerticalLayout();
        var h2 = new H1(roomService.findAllByRoomStatus(Status.AVAILABLE).size() + "+");
        h2.addClassName("number");
        verticalLayout.addClassNames("card");
        verticalLayout.add(
                new VerticalLayout(
                        new H2("Available Rooms"),
                        h2
                )
        );
        return verticalLayout;
    }

    private VerticalLayout AllRooms(){
        var verticalLayout = new VerticalLayout();
        var h2 = new H1(roomService.findAll().size() + "+");
        h2.addClassName("number");
        verticalLayout.addClassNames("card");
        verticalLayout.add(
                new VerticalLayout(
                        new H2("All Rooms"),
                        h2

                )
        );
        return verticalLayout;
    }
}
