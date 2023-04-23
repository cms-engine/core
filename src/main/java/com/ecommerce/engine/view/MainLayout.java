package com.ecommerce.engine.view;

import com.ecommerce.engine.view.user.UserList;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;

public class MainLayout extends AppLayout {

    public MainLayout() {
        DrawerToggle toggle = new DrawerToggle();

        Label title = new Label("Engine");

        UI.getCurrent().getElement().getThemeList().add(Lumo.DARK);

        Button modeButton = new Button("Dark/Light", click -> {
            ThemeList themeList = UI.getCurrent().getElement().getThemeList(); // (1)

            if (themeList.contains(Lumo.DARK)) { // (2)
                themeList.remove(Lumo.DARK);
            } else {
                themeList.add(Lumo.DARK);
            }
        });

        HorizontalLayout leftLayout = new HorizontalLayout(title);
        HorizontalLayout rightLayout = new HorizontalLayout(modeButton);
        leftLayout.setWidthFull();
        leftLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        rightLayout.setWidthFull();
        rightLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        rightLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        HorizontalLayout banner = new HorizontalLayout(leftLayout, rightLayout);
        banner.setWidthFull();

        Tab dashboard = new Tab(VaadinIcon.DASHBOARD.create(), new RouterLink("Dashboard", DashboardView.class));
        Tab orders = new Tab(VaadinIcon.LIST.create(), new Span("Orders"));
        orders.setEnabled(false);
        Tab users = new Tab(VaadinIcon.USERS.create(), new RouterLink("Users", UserList.class));

        Tabs tabs = new Tabs(dashboard, orders, users);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        addToDrawer(tabs);
        addToNavbar(toggle, banner);

        //setPrimarySection(Section.DRAWER);
    }
}
