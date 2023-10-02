package com.example.application.views.main.component.crud.produto;

import com.example.application.data.entity.ClienteDTO;
import com.example.application.data.entity.ProdutoDTO;
import com.example.application.views.main.component.customField.PhoneField;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Route("Produtos")
public class CrudToolBarProduto extends Div {
    private Crud<ProdutoDTO> crud;
    static ProdutoDataProvider dataProvider = new ProdutoDataProvider();

    private String ID = "id";
    private String NOME = "nome";
    private String PRECO = "preco";
    private String EDIT_COLUMN = "vaadin-crud-edit-column";

    public CrudToolBarProduto() {
        crud = new Crud<>(ProdutoDTO.class, createEditor());

        setupGrid();
        setupDataProvider();
        setupToolbar();

        add(crud);
    }

    private CrudEditor<ProdutoDTO> createEditor() {
        TextField nome = new TextField("Nome");
        NumberField preco = new NumberField("Preço");
        preco.setLabel("Valor");
        Div realPreffix = new Div();
        realPreffix.setText("R$");
        preco.setPrefixComponent(realPreffix);

        FormLayout formLayout = new FormLayout(nome,preco);

        Binder<ProdutoDTO> binder = new Binder<>(ProdutoDTO.class);
        binder.forField(nome).asRequired().bind(ProdutoDTO::getNome, ProdutoDTO::setNome);
        binder.forField(preco).asRequired().bind(ProdutoDTO::getPreco,ProdutoDTO::setPreco);


        return new BinderCrudEditor<>(binder, formLayout);
    }

    private void setupGrid() {
        Grid<ProdutoDTO> grid = crud.getGrid();

        List<String> visibleColumns = Arrays.asList(ID,NOME,PRECO,EDIT_COLUMN);
        grid.getColumns().forEach(column -> {
            String key = column.getKey();
            if (!visibleColumns.contains(key)) {
                grid.removeColumn(column);
            }
        });
        try {
            grid.setColumnOrder(grid.getColumnByKey(ID),grid.getColumnByKey(NOME), grid.getColumnByKey(PRECO), grid.getColumnByKey(EDIT_COLUMN));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupDataProvider() {
        dataProvider = new ProdutoDataProvider();
        crud.setDataProvider(dataProvider);
        crud.addDeleteListener(
                deleteEvent -> {
                    try {
                        dataProvider.delete(deleteEvent.getItem());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
        crud.addSaveListener(
                saveEvent -> {
                    try {
                        dataProvider.persist(saveEvent.getItem());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void setupToolbar() {
        Html total = new Html("<span>Total de: <b>" + dataProvider.database.size()
                + "</b> Produtos</span>");

        Button button = new Button("Novo produto", VaadinIcon.PLUS.create());
        button.addClickListener(event -> {
            crud.edit(new ProdutoDTO(), Crud.EditMode.NEW_ITEM);
        });
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        crud.setNewButton(button);

        HorizontalLayout toolbar = new HorizontalLayout(total);
        toolbar.setAlignItems(FlexComponent.Alignment.CENTER);
        toolbar.setFlexGrow(1, toolbar);
        toolbar.setSpacing(false);
        crud.setToolbar(toolbar);
    }
}



