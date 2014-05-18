package si.fri.tpo.gwt.client.form.navigation;

/**
 * Created by nanorax on 04/04/14.
 * Modified by Anze
 */

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.form.addedit.ProjectDataEditForm;
import si.fri.tpo.gwt.client.form.addedit.AddStoryToSprintForm;
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
    private ContentPanel east, north, south;

    private static final int PANEL_WIDTH = 230;
    private static final int PANEL_HEIGHT = 400;

    public ScrumMasterNavPanel(ContentPanel center, ContentPanel west, ContentPanel east,ContentPanel north, ContentPanel south, DScrumServiceAsync service) {
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
        panel.setHeadingText("Scrum Master menu");
        panel.setBodyBorder(false);
        createScrumMasterNavPanel();
        return panel;
    }

    private void createScrumMasterNavPanel() {

        VBoxLayoutContainer lcwest = new VBoxLayoutContainer();
        //lcwest.addStyleName("x-toolbar-mark");
        lcwest.setPadding(new Padding(5));
        lcwest.setVBoxLayoutAlign(VBoxLayoutContainer.VBoxLayoutAlign.STRETCH);

        panel.setWidget(lcwest);

        BoxLayoutContainer.BoxLayoutData vBoxData = new BoxLayoutContainer.BoxLayoutData(new Margins(5, 5, 5, 5));
        vBoxData.setFlex(1);

        TextButton userDataEditB = new TextButton("User Edit");
        userDataEditB.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                UserDataEditForm udef = new UserDataEditForm(service, center, north, south, east, west);
                center.clear();
                center.add(udef.asWidget());
            }
        });
        lcwest.add(userDataEditB);

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
        lcwest.add(projectEditing);

        TextButton sprintRegistrationB = new TextButton("Sprint Management");
        sprintRegistrationB.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (SessionInfo.projectDTO == null) {
                    Info.display("No project selected", "Please select project from the list on the left.");
                } else {
                    SprintRegistrationForm srf = new SprintRegistrationForm(service, center, west, east, north, south);
                    center.clear();
                    center.add(srf.asWidget());
                }
            }
        });
        lcwest.add(sprintRegistrationB);

        TextButton sprintDataEditB = new TextButton("Sprint Edit");
        sprintDataEditB.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (SessionInfo.projectDTO == null) {
                    Info.display("No project selected", "Please select project from the list on the left.");
                } else {
                    SprintDataEditForm sdef = new SprintDataEditForm(service, center, west, east, north, south);
                    center.clear();
                    center.add(sdef.asWidget());
                }
            }
        });
        lcwest.add(sprintDataEditB);

        final TextButton userStoryManagement = new TextButton("User Story Management");
        userStoryManagement.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (SessionInfo.projectDTO == null) {
                    Info.display("No project selected", "Please select project from the list on the left." );
                } else {
                    UserStoryRegistrationForm usrf = new UserStoryRegistrationForm(service, center, west, east, north, south);
                    center.clear();
                    center.add(usrf.asWidget());
                }
            }
        });
        lcwest.add(userStoryManagement);

        final TextButton addStoryToSprintManagement = new TextButton("Add User Story to Sprint");
        addStoryToSprintManagement.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (SessionInfo.projectDTO == null) {
                    Info.display("No project selected", "Please select project from the list on the left." );
                } else {
                    AddStoryToSprintForm astsf = new AddStoryToSprintForm(service, center, west, east, north, south);
                    center.clear();
                    center.add(astsf.asWidget());
                }
            }
        });
        lcwest.add(addStoryToSprintManagement);

        ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east, north, south);
        west.setHeadingText("Project list");
        west.add(psf.asWidget());

        panel.setWidth(PANEL_WIDTH);

    }
}