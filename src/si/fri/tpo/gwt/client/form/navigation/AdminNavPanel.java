package si.fri.tpo.gwt.client.form.navigation;

/**
 * Created by nanorax on 04/04/14.
 */

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.form.addedit.AdminUserDataEditForm;
import si.fri.tpo.gwt.client.form.addedit.ProjectDataEditForm;
import si.fri.tpo.gwt.client.form.registration.ProjectRegistrationForm;
import si.fri.tpo.gwt.client.form.registration.UserRegistrationForm;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

//TODO: merge cnavpanel into admin nav panel!
public class AdminNavPanel implements IsWidget {
    private DScrumServiceAsync service;
    private ContentPanel center;
    private ContentPanel west;
    private ContentPanel east, north, south;
    private FlowLayoutContainer con;

    private static final int PANEL_WIDTH = 230;
    private static final int PANEL_HEIGHT = 400;

    public AdminNavPanel(ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south, DScrumServiceAsync service) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
    }

    private ContentPanel panel;
    public Widget asWidget() {
        panel = new ContentPanel();
        con = new FlowLayoutContainer();
        panel.add(con);
        panel.setBodyBorder(false);
        panel.setHeadingText("Administrator menu");
        panel.setPosition(1,1);
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
                UserRegistrationForm rgf = new UserRegistrationForm(service, center, north, south, east, west);
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
                ProjectRegistrationForm pgf = new ProjectRegistrationForm(service, center, west, east, north, south);
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
                AdminUserDataEditForm audf = new AdminUserDataEditForm(service, center, north, south, east, west);
                center.clear();
                center.add(audf.asWidget());
            }
        });
        cp.add(userEditing);
        con.add(cp);

        cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setBodyStyleName("pad-text");

        final TextButton projectEditing = new TextButton("Project Editing");
        projectEditing.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (SessionInfo.projectDTO == null) {
                    Info.display("No project selected", "Please select project from the list on the left." );
                } else {
                    ProjectDataEditForm pdef = new ProjectDataEditForm(service, center, west, east, north, south);
                    center.clear();
                    center.add(pdef.asWidget());
                }
            }
        });
        cp.add(projectEditing);
        con.add(cp);

        ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east, north, south);
        west.setHeadingText("Project list");
        west.add(psf.asWidget());

        panel.setWidth(PANEL_WIDTH);

    }
}