package com.ecommerce.engine.view.user;

import com.ecommerce.engine.model.FilterType;
import com.ecommerce.engine.model.SearchRequest;
import com.ecommerce.engine.repository.entity.User;
import com.ecommerce.engine.view.template.FilterForm;
import com.ecommerce.engine.view.template.FormatDatePicker;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;

import java.util.List;

public class UserFilter extends FilterForm<User> {

    TextField username = new TextField("Username");
    EmailField email = new EmailField("Email");
    IntegerField ageGraterThen = new IntegerField("Age >=");
    IntegerField ageLessThen = new IntegerField("Age <=");
    DatePicker dateOfBirth = new FormatDatePicker("Date of birth");

    public UserFilter(ConfigurableFilterDataProvider<User, Void, List<SearchRequest.Filter>> dataProvider) {
        super(dataProvider);
        addComponents(username, email, new HorizontalLayout(ageGraterThen, ageLessThen), dateOfBirth);
    }

    @Override
    public List<SearchRequest.Filter> createSearchFilters() {
        var idFilter = new SearchRequest.Filter("username", FilterType.LIKE, username.getValue());
        var emailFilter = new SearchRequest.Filter("email", FilterType.LIKE, email.getValue());
        var ageGraterThenFilter = new SearchRequest.Filter("age", FilterType.GRATER_THAN_OR_EQUAL, ageGraterThen.getValue());
        var ageLessThenFilter = new SearchRequest.Filter("age", FilterType.LESS_THAN_OR_EQUAL, ageLessThen.getValue());
        var dateOfBirthFilter = new SearchRequest.Filter("dateOfBirth", FilterType.EQUAL, dateOfBirth.getValue());

        return List.of(idFilter, emailFilter, ageGraterThenFilter, ageLessThenFilter, dateOfBirthFilter);
    }

}
