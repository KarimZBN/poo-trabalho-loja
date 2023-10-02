package com.example.application.views.main.component.customField;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class PhoneField extends CustomField<Phone>{
    private final Select code = new Select();
    private final TextField number = new TextField();

    public PhoneField(String label) {
        this.setLabel(label);
        List<String> codes = List.of("47", "31", "21", "54", "48", "76", "45", "46", "11");
        code.setItems(codes);
        code.setLabel("Código");
        number.setLabel("Número");
        code.setPlaceholder("Selecione");
        code.setPrefixComponent(new Icon(VaadinIcon.PHONE));
        code.setWidth("100px");

        add(code, number);
    }

    @Override
    protected Phone generateModelValue() {
        return new Phone((String) code.getValue(), number.getValue());
    }

    @Override
    protected void setPresentationValue(Phone value) {
        if (value != null) {
            code.setValue(value.getCode());
            number.setValue(value.getNumber());
        }
    }
}
