package com.ecommerce.engine.view.user;

import com.ecommerce.engine.model.FilterType;
import com.ecommerce.engine.model.SearchRequest;
import com.ecommerce.engine.view.template.FilterDivComponent;
import com.ecommerce.engine.view.template.FormatDatePicker;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.Collection;
import java.util.List;

public class UserFilter implements FilterDivComponent {

    TextField username = new TextField();
    EmailField email = new EmailField();
    IntegerField age = new IntegerField();
    DatePicker dateOfBirth = new FormatDatePicker();

    @Override
    public Collection<Component> getComponents() {
        return List.of(
                getFieldWithFilterType(username, "Username"),
                getFieldWithFilterType(email, "Email"),
                getFieldWithFilterType(age, "Age"),
                getFieldWithFilterType(dateOfBirth, "Date of birth"));
    }

    @Override
    public List<SearchRequest.Filter> createSearchFilters() {
        var idFilter = new SearchRequest.Filter("username", FilterType.LIKE, username.getValue());
        var emailFilter = new SearchRequest.Filter("email", FilterType.LIKE, email.getValue());
        var ageFilter = new SearchRequest.Filter("age", FilterType.EQUAL, age.getValue());
        var dateOfBirthFilter = new SearchRequest.Filter("dateOfBirth", FilterType.EQUAL, dateOfBirth.getValue());

        return List.of(idFilter, emailFilter, ageFilter, dateOfBirthFilter);
    }

    private HorizontalLayout getFieldWithFilterType(Component component, String label) {
        Select<FilterType> comboBox = new Select<>();
        comboBox.setItems(FilterType.values());
        comboBox.setValue(FilterType.EQUAL);

        return new HorizontalLayout(new Span(label), comboBox, component);
    }

}
