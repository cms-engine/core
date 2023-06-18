package com.ecommerce.engine.view;

import com.ecommerce.engine.view.user.UserList;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.HashMap;
import java.util.Map;

public class MainLayout extends AppLayout {

    public MainLayout() {
        DrawerToggle toggle = new DrawerToggle();

        Span title = new Span("Engine");

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

        RouteTabs routeTabs = new RouteTabs();
        routeTabs.add(VaadinIcon.DASHBOARD.create(), new RouterLink("Dashboard", DashboardView.class));
        routeTabs.add(VaadinIcon.USERS.create(), new RouterLink("Users", UserList.class));

        Tab dashboard = new Tab(VaadinIcon.DASHBOARD.create(), new RouterLink("Dashboard", DashboardView.class));
        Tab orders = new Tab(VaadinIcon.LIST.create(), new Span("Orders"));
        orders.setEnabled(false);
        Tab users = new Tab(VaadinIcon.USERS.create(), new RouterLink("Users", UserList.class));

        HighlightConditions.sameLocation();

        Tabs tabs = new Tabs(dashboard, orders, users);

        routeTabs.setOrientation(Tabs.Orientation.VERTICAL);

        addToDrawer(routeTabs);
        addToNavbar(toggle, banner);

        //setPrimarySection(Section.DRAWER);
    }

    private static class RouteTabs extends Tabs implements BeforeEnterObserver {
        private final Map<RouterLink, Tab> routerLinkTabMap = new HashMap<>();

        public void add(Icon icon, RouterLink routerLink) {
            routerLink.setHighlightCondition(HighlightConditions.sameLocation());
            routerLink.setHighlightAction(
                    (link, shouldHighlight) -> {
                        if (shouldHighlight) setSelectedTab(routerLinkTabMap.get(routerLink));
                    }
            );
            routerLinkTabMap.put(routerLink, new Tab(icon, routerLink));
            add(routerLinkTabMap.get(routerLink));
        }

        @Override
        public void beforeEnter(BeforeEnterEvent event) {
            // In case no tabs will match
            setSelectedTab(null);
        }
    }
}
