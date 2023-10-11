package com.example.store.ui;

import com.example.store.Backend.rooms.Room;
import com.example.store.Backend.rooms.RoomService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route(value = "/rooms",layout = MainLayout.class)
@RolesAllowed({"USER","MANAGER","ADMIN"})
public class AllRooms extends VerticalLayout {

    public AllRooms(RoomService roomService){
        GridCrud<Room> grid = new GridCrud<>(Room.class,roomService);
        grid.getGrid().setColumns("roomType","roomNumber","roomDescription","roomPrice","roomStatus","roomCapacity");
        grid.getCrudFormFactory().setUseBeanValidation(true);
        grid.getCrudFormFactory().setVisibleProperties("roomType","roomNumber","roomDescription","roomPrice","roomStatus","roomCapacity");
        grid.setAddOperationVisible(false);
        grid.setUpdateOperationVisible(false);
        grid.setDeleteOperationVisible(false);

        grid.addClassNames("shadow-lg","p-2");

        String text = "All rooms that might be available for booking. Please contact us for more information.";


        add(
                new H2("All Rooms"),
                new Text(text),
                grid
        );

    }

}
