package si.fri.tpo.gwt.client;


import com.sencha.gxt.core.client.util.Format;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;
import si.fri.tpo.gwt.client.verification.PassHash;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;

/**
 * Created by nanorax on 4/4/14.
 */
public class LoginPanel  extends FormPanel implements IsWidget {

    private Grid grid;
    private DScrum dscrum;
    private DScrumServiceAsync service;
    private PasswordField password;
    private TextField username;
    private ContentPanel south;
    private ContentPanel north;
    private ContentPanel east;
    private ContentPanel west;
    private ContentPanel center;

    public LoginPanel(DScrum dscrum, ContentPanel center, ContentPanel north, ContentPanel south, ContentPanel east, ContentPanel west, DScrumServiceAsync service) {
        this.dscrum = dscrum;
        this.service = service;
        this.south = south;
        this.east = east;
        this.west = west;
        this.north = north;
        this.center = center;
    }

    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);
            createLoginForm();
        }
        return vp;
    }

    private VerticalPanel vp;

    private void createLoginForm() {
        FramedPanel loginForm = new FramedPanel();
        loginForm.setHeadingText("dScrum login form");
        loginForm.setWidth(350);

        FieldSet fieldSet = new FieldSet();
        fieldSet.setHeadingText("User Information");
        fieldSet.setCollapsible(false);
        loginForm.add(fieldSet, new MarginData(10));

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        fieldSet.add(p);

        username = new TextField();
        username.setAllowBlank(false);
        p.add(new FieldLabel(username, "Username"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        username.setText("Holden");

        password = new PasswordField();
        password.setAllowBlank(false);
        password.setText("vili");
        p.add(new FieldLabel(password, "Password"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        final TextButton loginButton = new TextButton("Login");
        loginButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                String plainPassword = password.getText();
                final String passwordHash = PassHash.getMD5Password(password.getText());
                validateResult(username.getText(), passwordHash);
            }
        });
        loginForm.addButton(loginButton);
        vp.add(loginForm);
    }

    private void validateResult(String username, String passwordHash) {
        final DialogHideHandler hideHandler = new DialogHideHandler() {
            @Override
            public void onDialogHide(DialogHideEvent event) {
                String msg = Format.substitute("The '{0}' button was pressed", event.getHideButton());
                //Info.display("MessageBox", msg);
            }
        };
        //todo verify username & password in database
        AsyncCallback<Pair<UserDTO, String>> callback = new AsyncCallback<Pair<UserDTO, String>>() {

            @Override
            public void onSuccess(Pair<UserDTO, String> result) {
                if (result.getFirst() != null) {
                    // open the navigation for resulted User
                    openNavigationContainer(result.getFirst());
                } else {
                    AlertMessageBox d = new AlertMessageBox("Wrong login credentials!", result.getSecond());
                    d.addDialogHideHandler(hideHandler);
                    d.show();
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };

        service.performUserLogin(username, passwordHash, callback);
    }

    private void openNavigationContainer(UserDTO userDTO) {
        // check if user is admin and open appropriate navigation
        checkUserRole(userDTO.isAdmin(), userDTO);
    }

    private void checkUserRole(boolean isAdmin, UserDTO userDTO) {
        SessionInfo.userDTO = userDTO;
        // Check if user is administrator and display appropriate message
        if (isAdmin) {
            String message = "Welcome to dScrum admin " +
                    userDTO.getFirstName() + " " + userDTO.getLastName();

            // open appropriate navigation panel and main form
            AdminNavPanel adminNav = new AdminNavPanel(service);

            fillNavigationMainAndHeader(adminNav.asWidget(), null);//new UserSearchForm(service), message);
        } else {
            // if user is not an admin, display user message

            String message = "Welcome to dScrum  user " +
                    userDTO.getFirstName() + " " + userDTO.getLastName();

            // open appropriate navigation panel and main form
              UserNavPanel userNavPanel = new UserNavPanel(service, center);
              fillNavigationMainAndHeader(userNavPanel.asWidget(), null); // TODO: dodaj se main form


        }
    }

    private void fillNavigationMainAndHeader(Widget navigationPanel, Widget mainPanel) {

        vp.clear();
        east.add(navigationPanel);
        final TextButton logoutButton = new TextButton("Logout");
        logoutButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                // TODO: kill session?!
                // clear the current panel
                vp.clear();
                south.clear();
                east.clear();
                // display new login panel
                vp.add(new LoginPanel(dscrum, center, north, south, east, west, service).asWidget());
            }
        });
        south.add(logoutButton);
    }
}






