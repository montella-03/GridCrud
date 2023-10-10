package com.example.store.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@RolesAllowed({"USER","MANAGER","ADMIN"})
public class MainLayout extends AppLayout {


    public MainLayout() {
        createHeader();
        createDrawer();
        //<theme-editor-local-classname>
        addClassName("main-layout-app-layout-1");
    }

    private void createHeader() {
        H1 logo = new H1("Workshop Suites");

        logo.addClassNames("text-xl", "font-bold", "text-gray-800", "p-2","m-m","font-serif");

        var leaf = new Div();
        leaf.addClassName("leaf");


        var avatar = new Avatar(loggedInUser());

        avatar.addClassNames("flex-end","mr-8","mt-2","h-8","w-8");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(),leaf,logo,avatar);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        header.expand(logo);

        header.setWidthFull();

        header.addClassNames("py-1","px-m");

        addToNavbar(header);
    }

    private void createDrawer() {

        var verticalLayout = new VerticalLayout();

        verticalLayout.addClassNames("mt-m","p-2");

        if(checkUser()){
            verticalLayout.add(createManager());
        }
        verticalLayout.add(createHome());
        verticalLayout.add(createBooking());
        verticalLayout.add(createCalendar());
        verticalLayout.add(createExpenditure());
        verticalLayout.add(createAnalytics());
        verticalLayout.add(createLogout());
        addToDrawer(verticalLayout);



    }


    private HorizontalLayout createManager() {
        RouterLink dashView = new RouterLink("MANAGER", ManagerView.class);
        dashView.setHighlightCondition(HighlightConditions.sameLocation());
        dashView.addClassNames("font-italic");
        Icon icon = new Icon(VaadinIcon.SCALE_UNBALANCE);
        icon.addClassNames("mr-2","text-green-500","ml-2");
        return new HorizontalLayout(icon,dashView);
    }

    private HorizontalLayout createHome() {
        RouterLink mainView = new RouterLink("HOME", HomeView.class);
        mainView.addClassNames("font-italic");
        mainView.setHighlightCondition(HighlightConditions.sameLocation());
        Icon icon = new Icon(VaadinIcon.HOME);
        icon.addClassNames("mr-2","text-green-500","ml-2");
        return new HorizontalLayout(icon,mainView);

    }

    private HorizontalLayout createBooking(){
        RouterLink bookingView = new RouterLink("BOOKING", BookingView.class);
        bookingView.setHighlightCondition(HighlightConditions.sameLocation());
        bookingView.addClassNames("font-serif");
        Icon icon = new Icon(VaadinIcon.BED);
        icon.addClassNames("mr-2","text-green-500","ml-2");
        return new HorizontalLayout(icon,bookingView);
    }
    private HorizontalLayout createCalendar(){
        RouterLink calendarView = new RouterLink("CALENDAR", CalendarView.class);
        calendarView.setHighlightCondition(HighlightConditions.sameLocation());
        calendarView.addClassNames("font-serif");
        Icon icon = new Icon(VaadinIcon.CALENDAR);
        icon.addClassNames("mr-2","text-green-500","ml-2");
        return new HorizontalLayout(icon,calendarView);
    }
    private HorizontalLayout createExpenditure(){
        RouterLink expenditureView = new RouterLink("EXPENDITURE", ExpenditureView.class);
        expenditureView.setHighlightCondition(HighlightConditions.sameLocation());
        expenditureView.addClassNames("font-serif");
        Icon icon = new Icon(VaadinIcon.MONEY_EXCHANGE);
        icon.addClassNames("mr-2","text-green-500","ml-2");
        return new HorizontalLayout(icon,expenditureView);
    }
    private HorizontalLayout createAnalytics(){
        RouterLink analyticsView = new RouterLink("ANALYTICS", AnalyticsView.class);
        analyticsView.setHighlightCondition(HighlightConditions.sameLocation());
        analyticsView.addClassNames("font-serif");
        Icon icon = new Icon(VaadinIcon.CHART);
        icon.addClassNames("mr-2","text-green-500","ml-2");
        return new HorizontalLayout(icon,analyticsView);
    }

    private HorizontalLayout createLogout(){
        Button logout = new Button("LOGOUT",new Icon(VaadinIcon.SIGN_OUT));
        logout.addClassName("m-20");
        logout.addClickListener(buttonClickEvent -> {
            SecurityContextHolder.clearContext();
            logout.getUI().ifPresent(ui -> ui.navigate("login"));
        });
        return new HorizontalLayout(logout);
    }
    private String loggedInUser() {
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
    private boolean checkUser(){
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userDetails.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_MANAGER")
                || a.getAuthority().equals("ROLE_ADMIN"));
    }
}
