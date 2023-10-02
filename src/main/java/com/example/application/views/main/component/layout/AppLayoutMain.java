package com.example.application.views.main.component.layout;

import com.example.application.views.main.component.crud.cliente.CrudToolBarCliente;
import com.example.application.views.main.component.crud.produto.CrudToolBarProduto;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
@PageTitle("Controle de clientes e produtos")
@Route("")
public class AppLayoutMain extends AppLayout{

    private static CrudToolBarProduto crudProduto;
    private static CrudToolBarCliente crudCliente;


    public AppLayoutMain() {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("Dashboard");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tabs tabs = getTabs();

        addToDrawer(tabs);
        addToNavbar(toggle, title);

        setPrimarySection(Section.DRAWER);
        tabs.addSelectedChangeListener(selectedChangeEvent ->
                {
                   String index = selectedChangeEvent.getSelectedTab().getLabel();
                   if (index.equals("Clientes")) {
                       if (getContent()!=null) {
                           getContent().removeFromParent();
                           setContent(new CrudToolBarCliente());
                       } else {
                           setContent(new CrudToolBarCliente());
                       }
                   } else if (index.equals("Produtos")) {
                       if (getContent()!=null) {
                           getContent().removeFromParent();
                           setContent(new CrudToolBarProduto());
                       } else {
                            setContent(new CrudToolBarProduto());
                       }
                   } else {
                       if (getContent()!=null) {
                           getContent().removeFromParent();
                       }
                   }
                });
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        Tab dash = createTab(VaadinIcon.DASHBOARD, "Dashboard");
        Tab cliente = createTab(VaadinIcon.USER, "Clientes");
        Tab produto = createTab(VaadinIcon.PACKAGE, "Produtos");
        tabs.add(dash, cliente, produto);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private Tab createTab(VaadinIcon viewIcon, String viewName) {
        Icon icon = viewIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(icon, new Span(viewName));
        link.setTabIndex(-1);

        Tab tab = new Tab(link);
        tab.setLabel(viewName);

        return tab;
    }
}
