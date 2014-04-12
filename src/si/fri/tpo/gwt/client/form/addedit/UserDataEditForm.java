package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 07/04/14.
 */
public class UserDataEditForm implements IsWidget{
//TODO: Something :)
    private DScrumServiceAsync service;
    private VerticalPanel vp;
    private TextField username;
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private PasswordField password;
    private PasswordField repassword;

    public UserDataEditForm(DScrumServiceAsync service) {
        this.service = service;
    }

    @Override
    public Widget asWidget() {
        if ( vp == null){
            vp = new VerticalPanel();
            vp.setSpacing(10);
            createUserEditForm();
        }
        return vp;
    }

    private void createUserEditForm() {
        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("User Edit Form");
        panel.setWidth(350);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        panel.add(p);

        username = new TextField();
        username.setAllowBlank(false);
        p.add(new FieldLabel(username, "Username"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        firstName = new TextField();
        firstName.setAllowBlank(true);
        p.add(new FieldLabel(firstName, "First name"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        lastName = new TextField();
        lastName.setAllowBlank(true);
        p.add(new FieldLabel(lastName, "Last name"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        email = new TextField();
        email.setAllowBlank(false);
        p.add(new FieldLabel(email, "E-mail"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        password = new PasswordField();
        password.setAllowBlank(true);
        p.add(new FieldLabel(password, "Change Password"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        repassword = new PasswordField();
        /*if(password.getValue().length()==0) {
            repassword.setAllowBlank(true);
        }else{
            repassword.setAllowBlank(false);
        }*/
        p.add(new FieldLabel(repassword, "Repeat Password"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        final TextButton saveButton = new TextButton("Save changes");

        panel.addButton(saveButton);
        vp.add(panel);
    }

}
