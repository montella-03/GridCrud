package com.example.store.ui;

import com.example.store.Backend.enumerations.RoomType;
import com.example.store.Backend.enumerations.Status;
import com.example.store.Backend.rooms.Room;
import com.example.store.Backend.rooms.RoomService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.Objects;

@Route(value = "manager/rooms/add-room",layout = MainLayout.class)
@RolesAllowed({"USER","MANAGER"})
public class AddRoomForm extends VerticalLayout {
    private final RoomService roomService;
    private TextField roomNumber = new TextField("Room Number");
    private NumberField roomPrice = new NumberField("Room Price");
    private IntegerField roomCapacity = new IntegerField("Room Capacity");
    private TextField roomDescription = new TextField("Room Description");
    private ComboBox<RoomType> roomType = new ComboBox<>("Room Type");
    private ComboBox<Status> roomStatus = new ComboBox<>("Room Status");


    public AddRoomForm(RoomService roomService){
        this.roomService = roomService;
        var prefix = new Div();
        prefix.setText("KES");
        roomPrice.setPrefixComponent(prefix);
        roomType.setItems(RoomType.values());
        roomStatus.setItems(Status.values());
        var binder = new Binder<>(Room.class);
        binder.bindInstanceFields(this);

        binder.forField(roomNumber)
                .withValidator(roomNumber -> roomNumber.length() >= 3, "Room number must be at least 3 characters long")
                .bind(Room::getRoomNumber, Room::setRoomNumber);
        binder.forField(roomPrice)
                .withValidator(roomPrice -> roomPrice > 0, "Room price must be greater than 0")
                .bind(Room::getRoomPrice, Room::setRoomPrice);
        binder.forField(roomCapacity)
                .withValidator(roomCapacity -> roomCapacity > 0, "Room capacity must be greater than 0")
                .bind(Room::getRoomCapacity, Room::setRoomCapacity);
        binder.forField(roomDescription)
                .withValidator(roomDescription -> roomDescription.length() >= 3, "Room description must be at least 3 characters long")
                .bind(Room::getRoomDescription, Room::setRoomDescription);
        binder.forField(roomType)
                .withValidator(Objects::nonNull, "Room type must be selected")
                .bind(Room::getRoomType, Room::setRoomType);
        binder.forField(roomStatus)
                .withValidator(Objects::nonNull, "Room status must be selected")
                .bind(Room::getRoomStatus, Room::setRoomStatus);
        var form = new FormLayout(
                roomNumber,
                roomPrice,
                roomCapacity,
                roomDescription,
                roomType,
                roomStatus);
        form.setWidth("60%");

        add(
                new H2("New Room"),
                new HorizontalLayout(
                        new VerticalLayout(
                                form,
                                new HorizontalLayout(
                                        new Button("Save", event -> {
                                            var room = new Room();
                                            if(binder.writeBeanIfValid(room)){
                                                roomService.add(room);
                                                Notification.show("Room saved", 3000, Notification.Position.MIDDLE);

                                                binder.readBean(new Room());

                                            }
                                            else {
                                                Notification.show(" Room not saved", 3000, Notification.Position.MIDDLE);
                                            }

                                        }),
                                        new Button("Cancel", event -> getUI().ifPresent(ui -> ui.navigate("manager/rooms")))

                                )

                        )


                )
        );

    }
}
