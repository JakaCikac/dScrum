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
import si.fri.tpo.gwt.client.form.addedit.*;
import si.fri.tpo.gwt.client.form.registration.ProjectRegistrationForm;
import si.fri.tpo.gwt.client.form.registration.SprintRegistrationForm;
import si.fri.tpo.gwt.client.form.registration.UserRegistrationForm;
import si.fri.tpo.gwt.client.form.registration.UserStoryRegistrationForm;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

//TODO: merge cnavpanel into admin nav panel!
public class GodNavPanel implements IsWidget {
    private DScrumServiceAsync service;
    private ContentPanel center;
    private ContentPanel west;
    private ContentPanel east;
    private FlowLayoutContainer con;

    public GodNavPanel(ContentPanel center, ContentPanel west, ContentPanel east, DScrumServiceAsync service) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
    }

    private ContentPanel panel;
    public Widget asWidget() {
        panel = new ContentPanel();
        con = new FlowLayoutContainer();
        panel.add(con);
        panel.setHeadingText("Admin / Scrum Master menu");
        panel.setBodyBorder(false);
        createAdminNavPanel();
        return panel;
    }

    private void createAdminNavPanel() {

        ContentPanel cp;

        cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setBodyStyleName("pad-text");

        final TextButton users = new TextButton("User Management");
        users.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                // TODO: Append registration form to center panel
                UserRegistrationForm rgf = new UserRegistrationForm(service, center);
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

        final TextButton userDataEditB = new TextButton("Edit me");
        userDataEditB.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                UserDataEditForm udef = new UserDataEditForm(service, center);
                center.clear();
                center.add(udef.asWidget());
            }
        });
        cp.add(userDataEditB);
        con.add(cp);

        cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setBodyStyleName("pad-text");

        final TextButton userEditing = new TextButton("Users Editing");
        userEditing.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                AdminUserDataEditForm audf = new AdminUserDataEditForm(service, center);
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

        final TextButton projectManagement = new TextButton("Project Management");
        projectManagement.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                ProjectRegistrationForm pgf = new ProjectRegistrationForm(service, center, west, east);
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

        final TextButton projectEditing = new TextButton("Project Editing");
        projectEditing.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (SessionInfo.projectDTO == null) {
                    Info.display("No project selected", "Please select project from the list on the left." );
                } else {
                    ProjecDataEditForm pdef = new ProjecDataEditForm(service, center, west, east);
                    center.clear();
                    center.add(pdef.asWidget());
                }
            }
        });
        cp.add(projectEditing);
        con.add(cp);

        cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setBodyStyleName("pad-text");

        final TextButton sprintRegistrationB = new TextButton("Sprint Management");
        sprintRegistrationB.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (SessionInfo.projectDTO == null) {
                    Info.display("No project selected", "Please select project from the list on the left.");
                } else {
                    SprintRegistrationForm srf = new SprintRegistrationForm(service, center, west, east);
                    center.clear();
                    center.add(srf.asWidget());
                }
            }
        });
        cp.add(sprintRegistrationB);
        con.add(cp);

        cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setBodyStyleName("pad-text");

        final TextButton sprintDataEditB = new TextButton("Sprint Edit");
        sprintDataEditB.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (SessionInfo.projectDTO == null) {
                    Info.display("No project selected", "Please select project from the list on the left.");
                } else {
                    SprintDataEditForm sdef = new SprintDataEditForm(service, center, west, east);
                    center.clear();
                    center.add(sdef.asWidget());
                }
            }
        });
        cp.add(sprintDataEditB);
        con.add(cp);

        cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setBodyStyleName("pad-text");

        final TextButton userStoryManagement = new TextButton("User Story Management");
        userStoryManagement.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (SessionInfo.projectDTO == null) {
                    Info.display("No project selected", "Please select project from the list on the left." );
                } else {
                    UserStoryRegistrationForm usrf = new UserStoryRegistrationForm(service, center, west, east);
                    center.clear();
                    center.add(usrf.asWidget());
                }
            }
        });
        cp.add(userStoryManagement);
        con.add(cp);

        cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setBodyStyleName("pad-text");

        final TextButton addStoryToSprintManagement = new TextButton("Add User Story to Sprint");
        addStoryToSprintManagement.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (SessionInfo.projectDTO == null) {
                    Info.display("No project selected", "Please select project from the list on the left." );
                } else {
                    AddStoryToSprintForm astsf = new AddStoryToSprintForm(service, center, west, east);
                    center.clear();
                    center.add(astsf.asWidget());
                }
            }
        });
        cp.add(addStoryToSprintManagement);
        con.add(cp);

        // user story editing
        cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setBodyStyleName("pad-text");

        final TextButton userStoryEditing = new TextButton("User Story Editing");
        userStoryEditing.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (SessionInfo.projectDTO == null) {
                    Info.display("No project selected", "Please select project from the list on the left." );
                } else {
                    //UserStoryEditForm usef = new UserStoryEditForm(service, center, west, east);
                    center.clear();
                    //center.add(usef.asWidget());
                }
            }
        });
        cp.add(userStoryEditing);
        con.add(cp);

        ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east);
        west.setHeadingText("Project list");
        west.add(psf.asWidget());

        panel.setWidth(160);

    }
}