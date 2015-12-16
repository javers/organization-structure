package org.javers.organization.structure.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import org.javers.organization.structure.domain.Person;
import org.javers.organization.structure.infrastructure.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("valo")
public class MainView extends UI {

    private OrganizationTree organizationTree;
    private PersonForm personForm;

    @Autowired
    public MainView(Controller controller, DataInitializer dataInitializer) {
        dataInitializer.populate();
        organizationTree = new OrganizationTree(controller);
        personForm = new PersonForm(controller);
        controller.injectView(this);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        GridLayout mainLayout = new GridLayout(2, 2);
        mainLayout.setSizeFull();
        setContent(mainLayout);
        Label label = new Label("JaVers Organization Structure");
        label.setStyleName("h2");
        HorizontalLayout v = new HorizontalLayout(label);
        v.setMargin(new MarginInfo(false, false, false, true));

        mainLayout.addComponent(v, 0, 0, 1, 0);
        mainLayout.addComponent(organizationTree, 0, 1);
        mainLayout.addComponent(personForm, 1, 1);
        mainLayout.setRowExpandRatio(0, 0.1f);
        mainLayout.setRowExpandRatio(1, 0.9f);
        mainLayout.setColumnExpandRatio(0, 0.3f);
        mainLayout.setColumnExpandRatio(1, 0.7f);
    }

    public void selectPersonOnForm(Person person) {
        personForm.selectPerson(person);
    }

    public void clearForm() {
        personForm.clearForm();

    }
}
