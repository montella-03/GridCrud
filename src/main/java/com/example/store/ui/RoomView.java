package com.example.store.ui;

import com.example.store.Backend.rooms.Room;
import com.example.store.Backend.rooms.RoomService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route(value = "/manager/rooms",layout = MainLayout.class)
@RolesAllowed({"USER","MANAGER","ADMIN"})
public class RoomView extends VerticalLayout {

    public RoomView(RoomService roomService){
        GridCrud<Room> grid = new GridCrud<>(Room.class,roomService);
        grid.getGrid().setColumns("roomType","roomNumber","roomDescription","roomPrice","roomStatus","roomCapacity");
        grid.getCrudFormFactory().setUseBeanValidation(true);
        grid.getCrudFormFactory().setVisibleProperties("roomType","roomNumber","roomDescription","roomPrice","roomStatus","roomCapacity");
        grid.setAddOperationVisible(false);
        grid.addClassNames("shadow-lg","p-2");
        grid.getCrudLayout().addToolbarComponent(addRoomLink());

        add(
                new H2("All Rooms"),
                grid
        );
    }

    private Component addRoomLink() {
        var addRoom = new Button("Add Room",e->getUI().ifPresent(ui -> ui.navigate("manager/rooms/add-room")));
        addRoom.addClassNames("btn","btn-primary","gap-6");
        return addRoom;
    }
}
