package com.example.store.ui;

import com.example.store.Backend.booking.Booking;
import com.example.store.Backend.booking.BookingService;
import com.example.store.Backend.enumerations.BedType;
import com.example.store.Backend.enumerations.BookingChannel;
import com.example.store.Backend.enumerations.RoomType;
import com.example.store.Backend.rooms.RoomService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.Objects;

@Route(value = "booking/new-booking",layout = MainLayout.class)
@RolesAllowed({"USER","MANAGER"})
public class NewBookingView extends VerticalLayout {
    private final RoomService roomService;
    private TextField name = new TextField("Name");
    private EmailField email = new EmailField("Email");
    private TextField phoneNumber = new TextField("Phone Number");
    private ComboBox<String> nationality= new ComboBox<>("Nationality");
    private TextField passportNumber = new TextField("Passport Number");
    private DatePicker arrivalDate = new DatePicker("Arrival Date");
    private DatePicker departureDate = new DatePicker("Departure Date");
    private ComboBox<RoomType> roomType = new ComboBox<>("Room Type");
    private ComboBox<String>roomNumber = new ComboBox<>("Room Number");
    private ComboBox<BedType> bedType = new ComboBox<>("Bed Type");
    private IntegerField numberOfBeds = new IntegerField("Number of Beds");
    private TextField lockNumber = new TextField("Lock Number");
    private ComboBox<BookingChannel> bookingChannel = new ComboBox<>("Booking Channel");
    private IntegerField amountPaid = new IntegerField("Amount Paid");


    public NewBookingView(BookingService bookingService, RoomService roomService) {
        this.roomService = roomService;
        List<String> availableRooms = this.roomService.getAvailableRooms();
        List<String> countries = List.of("KENYA","UGANDA","TANZANIA");

        roomType.setItems(RoomType.values());
        bedType.setItems(BedType.values());
        bookingChannel.setItems(BookingChannel.values());
        roomNumber.setItems(availableRooms);
        nationality.setItems(countries);
        numberOfBeds.setRequiredIndicatorVisible(true);

        name.setRequired(true);
        email.setRequired(true);
        lockNumber.setRequired(true);
        roomType.setRequired(true);
        roomNumber.setRequired(true);
        bedType.setRequired(true);
        passportNumber.setRequired(true);
        numberOfBeds.setRequired(true);


        var prefix = new Div();
        prefix.setText("KES");
        amountPaid.setPrefixComponent(prefix);

        var binder = new Binder<>(Booking.class);
        binder.bindInstanceFields(this);

        binder.forField(numberOfBeds)
                .withValidator(numberOfBeds -> numberOfBeds > 0, "Number of beds must be greater than 0")
                .bind(Booking::getNumberOfBeds, Booking::setNumberOfBeds);
        binder.forField(amountPaid)
                .withValidator(amountPaid -> amountPaid > 0, "Amount paid must be greater than 0")
                .bind(Booking::getAmountPaid, Booking::setAmountPaid);
        binder.forField(arrivalDate)
                .withValidator(arrivalDate -> arrivalDate.isBefore(departureDate.getValue()), "Arrival date must be before departure date")
                .bind(Booking::getArrivalDate, Booking::setArrivalDate);
        binder.forField(departureDate)
                .withValidator(departureDate -> departureDate.isAfter(arrivalDate.getValue()), "Departure date must be after arrival date")
                .bind(Booking::getDepartureDate, Booking::setDepartureDate);
        binder.forField(roomNumber)
                .withValidator(Objects::nonNull, "Room number must be selected")
                .bind(Booking::getRoomNumber, Booking::setRoomNumber);
        binder.forField(roomType)
                .withValidator(Objects::nonNull, "Room type must be selected")
                .bind(Booking::getRoomType, Booking::setRoomType);
        binder.forField(bedType)
                .withValidator(Objects::nonNull, "Bed type must be selected")
                .bind(Booking::getBedType, Booking::setBedType);
        binder.forField(bookingChannel)
                .withValidator(Objects::nonNull, "Booking channel must be selected")
                .bind(Booking::getBookingChannel, Booking::setBookingChannel);
        binder.forField(passportNumber)
                .withValidator(passportNumber -> passportNumber.length() == 8, "Passport number must be 8 characters")
                .bind(Booking::getPassportNumber, Booking::setPassportNumber);
        binder.forField(phoneNumber)
                .withValidator(phoneNumber -> phoneNumber.length() == 12, "Phone number must be 12 characters")
                .bind(Booking::getPhoneNumber, Booking::setPhoneNumber);
        binder.forField(email)
                .withValidator(email -> email.contains("@"), "Email must contain @")
                .bind(Booking::getEmail, Booking::setEmail);
        binder.forField(name)
                .withValidator(name -> name.length() >= 3, "Name must be at least 3 characters")
                .bind(Booking::getName, Booking::setName);
        binder.forField(nationality)
                .withValidator(Objects::nonNull,"Nationality must be selected")
                .bind(Booking::getNationality, Booking::setNationality);

        var form  = new FormLayout(
                name,
                email,
                nationality,
                passportNumber,
                arrivalDate,
                departureDate,
                roomType,
                roomNumber,
                bedType,
                numberOfBeds,
                lockNumber,
                bookingChannel,
                amountPaid


        );
        form.setWidth("80%");


        add(
                new H2("New Booking"),
                new HorizontalLayout(
                        new VerticalLayout(
                                form,
                                new HorizontalLayout(
                                        new Button("Save", event -> {
                                            var booking = new Booking();

                                            if(binder.writeBeanIfValid(booking)){
                                                binder.writeBeanIfValid(booking);
                                                bookingService.add(booking);
                                                Notification.show("Booking saved successfully", 3000, Notification.Position.MIDDLE);

                                                binder.readBean(new Booking());

                                            }
                                            else {
                                                Notification.show(" Booking not saved", 3000, Notification.Position.MIDDLE);
                                            }

                                        }),
                                        new Button("Cancel", event -> getUI().ifPresent(ui -> ui.navigate("/booking")))

                                )

                        )


                )
        );


    }
}
