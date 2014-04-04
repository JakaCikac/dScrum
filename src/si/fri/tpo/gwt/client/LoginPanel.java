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
import si.fri.tpo.gwt.client.form.navigation.AdminNaviPanel;
import si.fri.tpo.gwt.client.form.search.UserSearchForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.session.SessionInfo;

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
    private DScrumServiceAsync service;

    public LoginPanel(DScrum dscrum, RootPanel navigationContainer, RootPanel mainContainer, RootPanel headerContainer, DScrumServiceAsync service) {
        this.dscrum = dscrum;
        this.navigationContainer = navigationContainer;
        this.mainContainer = mainContainer;
        this.headerContainer = headerContainer;
        this.service = service;

        initComponents();

        loginButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

                String plainPassword = passwordTB.getText();
                // Hashed password (SHA?)
                // Passwordi, ki jih shranimo v bazo morajo biti poheshani na enak nacin
                String passwordHash = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
                validateResult(usernameTB.getText(), passwordHash);
            }
        });
    }

     private void validateResult(String username, String passwordHash) {
        //todo verify username & password in database
        AsyncCallback<Pair<UserDTO, String>> callback = new AsyncCallback<Pair<UserDTO, String>>() {

            @Override
            public void onSuccess(Pair<UserDTO, String> result) {
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

        service.performUserLogin(username, passwordHash, callback);
    } 

     private void openNavigationContainer(UserDTO UserDTO) {
        mainContainer.remove(this);
        navigationContainer.remove(0);
         // check if user is admin and open appropriate navigation
        checkUserRole(UserDTO.getIsAdmin(), UserDTO);
    }

    private void checkUserRole(byte isAdmin, UserDTO userDTO) {
        SessionInfo.userDTO = userDTO;
        // Check if user is administrator and display appropriate message
        if (isAdmin == 1) {

            String message = "Welcome to dScrum admin " +
                    userDTO.getFirstName() + " " + userDTO.getLastName();

            // open appropriate navigation panel and main form
            fillNavigationMainAndHeader(new AdminNaviPanel(mainContainer, service),
                    new UserSearchForm(service), message);
        } else {
            // if user is not an admin, display user message

            String message = "Welcome to dScrum user " +
                    userDTO.getFirstName() + " " + userDTO.getLastName();

        }
    }

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
