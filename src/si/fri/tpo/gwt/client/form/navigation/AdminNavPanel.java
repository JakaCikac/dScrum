package si.fri.tpo.gwt.client.form.navigation;

/**
 * Created by nanorax on 04/04/14.
 */

import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.legacy.client.data.ModelData;
import com.sencha.gxt.legacy.client.data.BaseModelData;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import si.fri.tpo.gwt.client.form.addedit.AdminUserDataEditForm;
import si.fri.tpo.gwt.client.form.registration.ProjectRegistrationForm;
import si.fri.tpo.gwt.client.form.registration.UserRegistrationForm;
import si.fri.tpo.gwt.client.form.search.SingleUserSearch;
import si.fri.tpo.gwt.client.form.search.SingleUserSearchDialog;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

//TODO: merge cnavpanel into admin nav panel!
public class AdminNavPanel implements IsWidget {
    private DScrumServiceAsync service;
    private ContentPanel center;
    private FlowLayoutContainer con;

    public AdminNavPanel(ContentPanel center, DScrumServiceAsync service) {
        this.service = service;
        this.center = center;
    }

    private ContentPanel panel;
    public Widget asWidget() {
        panel = new ContentPanel();
        con = new FlowLayoutContainer();
        panel.add(con);
        panel.setHeadingText("Administrator menu");
        panel.setBodyBorder(false);
        createAdminNavPanel();
        return panel;
    }

    private void createAdminNavPanel() {

        ContentPanel cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setBodyStyleName("pad-text");

        final TextButton users = new TextButton("User Management");
        users.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                // TODO: Append registration form to center panel
                UserRegistrationForm rgf = new UserRegistrationForm(service);
                center.clear();
                center.add(rgf.asWidget());
            }
        });
        cp.add(users);
        con.add(cp);

        cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setBodyStyleName("pad-text");

        final TextButton projectManagement = new TextButton("Project Management");
        projectManagement.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                ProjectRegistrationForm pgf = new ProjectRegistrationForm(service);
                center.clear();
                center.add(pgf.asWidget());
            }
        });
        cp.add(projectManagement);
        con.add(cp);

        cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setBodyStyleName("pad-text");

        final TextButton userEditing = new TextButton("User Editing");
        userEditing.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                AdminUserDataEditForm audf = new AdminUserDataEditForm(service);
                center.clear();
                center.add(audf.asWidget());
            }
        });
        cp.add(userEditing);
        con.add(cp);

        panel.setWidth(160);

    }

    private ModelData newItem(String text, String iconStyle) {
        ModelData m = new BaseModelData();
        m.set("name", text);
        m.set("icon", iconStyle);
        return m;
    }
}