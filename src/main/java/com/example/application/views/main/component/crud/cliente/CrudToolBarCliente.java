package com.example.application.views.main.component.crud.cliente;

import com.example.application.data.entity.ClienteDTO;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Route("Clientes")
public class CrudToolBarCliente extends Div {
    private Crud<ClienteDTO> crud;
    static ClienteDataProvider dataProvider = new ClienteDataProvider();

    private String ID = "id";
    private String NOME = "nome";
    private String DATA_NASCIMENTO = "dataNascimento";
    private String CPF = "cpf";
    private String TELEFONE = "telefone";
    private String EMAIL = "email";
    private String CEP = "cep";
    private String BAIRRO = "bairro";
    private String LOGRADOURO = "logradouro";
    private String NUMERO = "numero";
    private String COMPLEMENTO = "complemento";
    private String CIDADE = "cidade";
    private String ESTADO = "estado";
    private String EDIT_COLUMN = "vaadin-crud-edit-column";

    public CrudToolBarCliente() {
        crud = new Crud<>(ClienteDTO.class, createEditor());

        setupGrid();
        setupDataProvider();
        setupToolbar();

        add(crud);
    }

    private CrudEditor<ClienteDTO> createEditor() {
        TextField nome = new TextField("Nome");
        DatePicker dataNascimento = new DatePicker("Data de Nascimento");
        TextField cpf = new TextField("CPF");
        PhoneField telefone = new PhoneField("Telefone");
        EmailField emailField = new EmailField("E-mail");
        emailField.setClearButtonVisible(true);
        emailField.setErrorMessage("Informe um email valido!");
        TextField cep = new TextField("Cep");
        TextField bairro = new TextField("Bairro");
        TextField logradouro = new TextField("Logradouro");
        IntegerField numero = new IntegerField("Número");
        TextField complemento = new TextField("Complemento");
        TextField cidade = new TextField("Cidade");
        TextField estado = new TextField("Estado");

        FormLayout formLayout = new FormLayout(nome, dataNascimento, cpf, telefone, emailField, cep, bairro, logradouro,
                numero, complemento, cidade, estado);

        Binder<ClienteDTO> binder = new Binder<>(ClienteDTO.class);
        binder.forField(nome).asRequired().bind(ClienteDTO::getNome, ClienteDTO::setNome);
        binder.forField(dataNascimento).asRequired().bind(ClienteDTO::getDataNascimento, ClienteDTO::setDataNascimento);
        binder.forField(cpf).asRequired().bind(ClienteDTO::getCpf, ClienteDTO::setCpf);
        binder.forField(telefone).asRequired().bind(ClienteDTO::getTelefone, ClienteDTO::setTelefone);
        binder.forField(emailField).asRequired().bind(ClienteDTO::getEmail, ClienteDTO::setEmail);
        binder.forField(cep).asRequired().bind(ClienteDTO::getCep, ClienteDTO::setCep);
        binder.forField(bairro).asRequired().bind(ClienteDTO::getBairro, ClienteDTO::setBairro);
        binder.forField(logradouro).asRequired().bind(ClienteDTO::getLogradouro, ClienteDTO::setLogradouro);
        binder.forField(numero).asRequired().bind(ClienteDTO::getNumero, ClienteDTO::setNumero);
        binder.forField(complemento).asRequired().bind(ClienteDTO::getComplemento, ClienteDTO::setComplemento);
        binder.forField(cidade).asRequired().bind(ClienteDTO::getCidade, ClienteDTO::setCidade);
        binder.forField(estado).asRequired().bind(ClienteDTO::getEstado, ClienteDTO::setEstado);

        return new BinderCrudEditor<>(binder, formLayout);
    }

    private void setupGrid() {
        Grid<ClienteDTO> grid = crud.getGrid();

        // Only show these columns (all columns shown by default):
        List<String> visibleColumns = Arrays.asList(ID,NOME, DATA_NASCIMENTO, CPF,TELEFONE, EMAIL, CEP, BAIRRO, LOGRADOURO,
                NUMERO, COMPLEMENTO, CIDADE, ESTADO, EDIT_COLUMN);
        grid.getColumns().forEach(column -> {
            String key = column.getKey();
            if (!visibleColumns.contains(key)) {
                grid.removeColumn(column);
            }
        });
        try {
            // Reorder the columns (alphabetical by default)
            grid.setColumnOrder(grid.getColumnByKey(ID),grid.getColumnByKey(NOME), grid.getColumnByKey(DATA_NASCIMENTO),
                    grid.getColumnByKey(CPF),grid.getColumnByKey(TELEFONE), grid.getColumnByKey(EMAIL),
                    grid.getColumnByKey(CEP), grid.getColumnByKey(BAIRRO), grid.getColumnByKey(LOGRADOURO),
                    grid.getColumnByKey(NUMERO), grid.getColumnByKey(COMPLEMENTO), grid.getColumnByKey(CIDADE),
                    grid.getColumnByKey(ESTADO), grid.getColumnByKey(EDIT_COLUMN));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupDataProvider() {
        dataProvider = new ClienteDataProvider();
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
                + "</b> Clientes</span>");

        Button button = new Button("Novo Cliente", VaadinIcon.PLUS.create());
        button.addClickListener(event -> {
            crud.edit(new ClienteDTO(), Crud.EditMode.NEW_ITEM);
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



