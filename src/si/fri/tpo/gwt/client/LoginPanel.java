package si.fri.tpo.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.components.BCrypt;

import java.io.Serializable;

/**
 * Created by nanorax on 4/4/14.
 */
public class LoginPanel extends FormPanel {

    private RootPanel mainContainer;
    private RootPanel navigationContainer;
    private RootPanel headerContainer;
    private Grid grid;
    private Button loginButton;
    private PasswordTextBox passwordTB;
    private TextBox usernameTB;
    private DScrum dscrum;

    public LoginPanel(DScrum dscrum, RootPanel navigationContainer, RootPanel mainContainer, RootPanel headerContainer) {
        this.dscrum = dscrum;
        this.navigationContainer = navigationContainer;
        this.mainContainer = mainContainer;
        this.headerContainer = headerContainer;
        //this.service = service;

        initComponents();

        loginButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

                String plainPassword = passwordTB.getText();
                // Hashed password (SHA?)
                // Passwordi, ki jih shranimo v bazo morajo biti poheshani na enak nacin
                String hashPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
                //validateResult(usernameTB.getText(), hashPassword);
            }
        });
    }

    /* private void validateResult(String username, String passwordMD5) {
        //todo verify username & password in database
        AsyncCallback<Pair<PersonDTO, String>> callback = new AsyncCallback<Pair<PersonDTO, String>>() {

            @Override
            public void onSuccess(Pair<PersonDTO, String> result) {
                if (result.getFirst() != null)
                    openNavigationContainer(result.getFirst());
                else
                    MessageBox.alert("Wrong login credentials", result.getSecond(), null);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };

        service.performUserLogin(username, passwordMD5, callback);
    } */

    /* private void openNavigationContainer(PersonDTO personDTO) {
        mainContainer.remove(this);
        navigationContainer.remove(0);
        openContainersByPersonRole(personDTO.getRole(), personDTO);
        //mainContainer.getElement().getStyle().setHeight(1000, Unit.PX);
    } */


   /* private void openContainersByPersonRole(final String role, final PersonDTO personDTO) {
        SessionInfo.personDTO = personDTO;
        if (role.equals(RoleConstaints.ADMINISTRATOR)) {
            String headerMessage = "Pozdravljeni na e-študentu, skrbnik: " +
                    personDTO.getName() + " " + personDTO.getSurname() +
                    (personDTO.getSurname2() != null ? " " + personDTO.getSurname2() + "" : "");
            fillNavigationMainAndHeader(new AdministratorNavigationPanel(mainContainer, service),
                    new StudentSearchEngineForm(service), headerMessage);
        } else if (role.equals(RoleConstaints.CLERK)) {
            String headerMessage = "Pozdravljeni na e-študentu, referent(ka): " +
                    personDTO.getName() + " " + personDTO.getSurname() +
                    (personDTO.getSurname2() != null ? " " + personDTO.getSurname2() + "" : "");
            fillNavigationMainAndHeader(new ClerkNavigationPanel(mainContainer, service),
                    new StudentSearchEngineForm(service), headerMessage);
        } else if (role.equals(RoleConstaints.PROFESSOR)) {
            String title = personDTO.getProfessorDTO() != null ? personDTO.getProfessorDTO().getTitle() : "";
            String headerMessage = "Pozdravljeni na e-študentu, profesor(ica): " + title + " " +
                    personDTO.getName() + " " + personDTO.getSurname() +
                    (personDTO.getSurname2() != null ? " " + personDTO.getSurname2() + "" : "");
            fillNavigationMainAndHeader(new ProfessorNavigationPanel(mainContainer, service),
                    new Label("Prva stran profesorja"), headerMessage);
        } else if (role.equals(RoleConstaints.REPRESENTATIVE_PERSON)) {
            //TODO
        } else if (role.equals(RoleConstaints.DEPUTY_DEAN)) {
            //TODO
        } else if (role.equals(RoleConstaints.STUDENT)) {
            String headerMessage = "Pozdravljeni na e-študentu, študent(ka): " +
                    personDTO.getName() + " " + personDTO.getSurname() +
                    (personDTO.getSurname2() != null ? " " + personDTO.getSurname2() + ", " : ", ") +
                    "vpis. št.: " + personDTO.getUsername();
            fillNavigationMainAndHeader(new StudentNavigationPanel(mainContainer, service),
                    new StudentGradeRecordForm(service), headerMessage);
        }
    }*/

    private void fillNavigationMainAndHeader(LayoutContainer navigationPanel1, Widget mainContainer1, String headerMessage) {
        navigationContainer.add(navigationPanel1, -1, -1);
        mainContainer.add(mainContainer1);
        headerContainer.clear();
        headerContainer.add(new Label(headerMessage));

        headerContainer.add(new com.extjs.gxt.ui.client.widget.button.Button("Logout", new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                navigationContainer.clear();
                headerContainer.clear();
                mainContainer.clear();

                navigationContainer = dscrum.initNavigationContainer();
                headerContainer = dscrum.initHeaderContainer();
                mainContainer = dscrum.initMainContainer(navigationContainer, headerContainer);
            }
        }));

    }

    public void initComponents() {
        setSize("100%", "100%");

        grid = new Grid(3, 3);
        setWidget(grid);
        grid.setSize("", "");

        Label lblNewLabel = new Label("Username:");
        lblNewLabel.setWordWrap(false);
        grid.setWidget(0, 0, lblNewLabel);

        usernameTB = new TextBox();
        usernameTB.setText("referentka1");
        grid.setWidget(0, 1, usernameTB);
        usernameTB.setWidth("100%");

        Label lblNewLabel_1 = new Label("Password:");
        grid.setWidget(1, 0, lblNewLabel_1);

        passwordTB = new PasswordTextBox();
        passwordTB.setText("password");
        grid.setWidget(1, 1, passwordTB);
        passwordTB.setWidth("100%");
        grid.getCellFormatter().setVerticalAlignment(2, 2,
                HasVerticalAlignment.ALIGN_TOP);
        grid.getCellFormatter().setHorizontalAlignment(0, 0,
                HasHorizontalAlignment.ALIGN_RIGHT);
        grid.getCellFormatter().setHorizontalAlignment(1, 0,
                HasHorizontalAlignment.ALIGN_RIGHT);

        loginButton = new Button("Login");
        grid.setWidget(2, 1, loginButton);
        grid.getCellFormatter().setHorizontalAlignment(2, 1,
                HasHorizontalAlignment.ALIGN_LEFT);

    }

}
