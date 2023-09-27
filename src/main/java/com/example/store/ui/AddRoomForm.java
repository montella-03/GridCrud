package com.example.store.ui;

import com.example.store.Backend.enumerations.RoomType;
import com.example.store.Backend.enumerations.Status;
import com.example.store.Backend.rooms.Room;
import com.example.store.Backend.rooms.RoomService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "manager/rooms/add-room",layout = MainLayout.class)
@RolesAllowed({"USER","MANAGER"})
public class AddRoomForm extends VerticalLayout {
    private final RoomService roomService;
    private TextField roomNumber = new TextField("Room Number");
    private IntegerField roomPrice = new IntegerField("Room Price");
    private IntegerField roomCapacity = new IntegerField("Room Capacity");
    private TextField roomDescription = new TextField("Room Description");
    private ComboBox<RoomType> roomType = new ComboBox<>("Room Type");
    private ComboBox<Status> roomStatus = new ComboBox<>("Room Status");


    public AddRoomForm(RoomService roomService){
        this.roomService = roomService;
        roomType.setItems(RoomType.values());
        roomStatus.setItems(Status.values());
        var binder = new Binder<>(Room.class);
        binder.bindInstanceFields(this);
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
