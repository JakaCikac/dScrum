package si.fri.tpo.gwt.client;

import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.search.UserSearchForm;
import si.fri.tpo.gwt.client.session.SessionInfo;
import si.fri.tpo.gwt.client.verification.PassHash;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

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
                // MD5 password
                final String passwordHash = PassHash.getMD5Password(passwordTB.getText());
                validateResult(usernameTB.getText(), passwordHash);

                //System.out.println("plain: " + plainPassword + " hashed: " + passwordHash);
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

     private void openNavigationContainer(UserDTO userDTO) {
        System.out.println("Opening navigation container.");
        mainContainer.remove(this);
        System.out.println("Removing this from main container.");
        //navigationContainer.remove(0);
        System.out.println("Removing 0 from navigation container.");
         // check if user is admin and open appropriate navigation
        System.out.println("Check user role.");
        checkUserRole(userDTO.isAdmin(), userDTO);
    }

    private void checkUserRole(boolean isAdmin, UserDTO userDTO) {
        SessionInfo.userDTO = userDTO;
        System.out.println("Is admin? " + userDTO.isAdmin() + " with username: " + userDTO.getFirstName());
        // Check if user is administrator and display appropriate message
        if (isAdmin) {
            System.out.println("I know the user is admin, now trying to get his name.");
            String message = "Welcome to dScrum admin " +
                    userDTO.getFirstName() + " " + userDTO.getLastName();


            // open appropriate navigation panel and main form
            fillNavigationMainAndHeader(new AdminNavPanel(mainContainer, service),
                    new UserSearchForm(service), message);
        } else {
            // if user is not an admin, display user message

            String message = "Welcome to dScrum user " +
                    userDTO.getFirstName() + " " + userDTO.getLastName();

            // open appropriate navigation panel and main form
            /* fillNavigationMainAndHeader(new StudentNavigationPanel(mainContainer, service),
                    new StudentGradeRecordForm(service), headerMessage); */

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
        usernameTB.setText("t13as");
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
