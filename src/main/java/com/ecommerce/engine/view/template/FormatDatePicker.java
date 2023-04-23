package com.ecommerce.engine.view.template;

import com.vaadin.flow.component.datepicker.DatePicker;

import java.time.LocalDate;
import java.util.Locale;

public class FormatDatePicker extends DatePicker {
    public FormatDatePicker() {
        super();
        setI18n();
    }

    public FormatDatePicker(LocalDate initialDate) {
        super(initialDate);
        setI18n();
    }

    public FormatDatePicker(String label) {
        super(label);
        setI18n();
    }

    public FormatDatePicker(String label, LocalDate initialDate) {
        super(label, initialDate);
        setI18n();
    }

    public FormatDatePicker(ValueChangeListener<ComponentValueChangeEvent<DatePicker, LocalDate>> listener) {
        super(listener);
        setI18n();
    }

    public FormatDatePicker(String label, ValueChangeListener<ComponentValueChangeEvent<DatePicker, LocalDate>> listener) {
        super(label, listener);
        setI18n();
    }

    public FormatDatePicker(LocalDate initialDate, ValueChangeListener<ComponentValueChangeEvent<DatePicker, LocalDate>> listener) {
        super(initialDate, listener);
        setI18n();
    }

    public FormatDatePicker(String label, LocalDate initialDate, ValueChangeListener<ComponentValueChangeEvent<DatePicker, LocalDate>> listener) {
        super(label, initialDate, listener);
        setI18n();
    }

    public FormatDatePicker(LocalDate initialDate, Locale locale) {
        super(initialDate, locale);
        setI18n();
    }

    private void setI18n() {
        setI18n(new DatePicker.DatePickerI18n().setDateFormat("yyyy-MM-dd"));
    }
}
