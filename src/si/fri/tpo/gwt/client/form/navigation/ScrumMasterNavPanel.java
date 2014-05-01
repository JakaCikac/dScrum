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
import si.fri.tpo.gwt.client.form.addedit.AddStoryToSprintForm;
import si.fri.tpo.gwt.client.form.addedit.ProjecDataEditForm;
import si.fri.tpo.gwt.client.form.addedit.SprintDataEditForm;
import si.fri.tpo.gwt.client.form.addedit.UserDataEditForm;
import si.fri.tpo.gwt.client.form.registration.SprintRegistrationForm;
import si.fri.tpo.gwt.client.form.registration.UserStoryRegistrationForm;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

//TODO: merge cnavpanel into admin nav panel!
public class ScrumMasterNavPanel implements IsWidget {
    private DScrumServiceAsync service;
    private ContentPanel center;
    private ContentPanel west;
    private ContentPanel east;
    private FlowLayoutContainer con;

    public ScrumMasterNavPanel(ContentPanel center, ContentPanel west, ContentPanel east, DScrumServiceAsync service) {
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
        panel.setHeadingText("Scrum Master menu");
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

        TextButton userDataEditB = new TextButton("User Edit");
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

        TextButton sprintRegistrationB = new TextButton("Sprint Management");
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

        TextButton sprintDataEditB = new TextButton("Sprint Edit");
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

        ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east);
        west.setHeadingText("Project list");
        west.add(psf.asWidget());

        panel.setWidth(160);

    }
}