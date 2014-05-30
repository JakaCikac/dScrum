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
import si.fri.tpo.gwt.client.form.addedit.AdminUserDataEditForm;
import si.fri.tpo.gwt.client.form.addedit.ProjectDataEditForm;
import si.fri.tpo.gwt.client.form.registration.ProjectRegistrationForm;
import si.fri.tpo.gwt.client.form.registration.UserRegistrationForm;
import si.fri.tpo.gwt.client.form.registration.UserStoryRegistrationForm;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

//TODO: merge cnavpanel into admin nav panel!
public class AdminNavPanel implements IsWidget {
    private DScrumServiceAsync service;
    private ContentPanel center;
    private ContentPanel west;
    private ContentPanel east, north, south;

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
        panel.setBodyBorder(false);
        panel.setHeadingText("Administrator menu");
        panel.setPosition(1,1);
        createAdminNavPanel();
        return panel;
    }

    private void createAdminNavPanel() {

        VBoxLayoutContainer lcwest = new VBoxLayoutContainer();
        //lcwest.addStyleName("x-toolbar-mark");
        lcwest.setPadding(new Padding(5));
        lcwest.setVBoxLayoutAlign(VBoxLayoutContainer.VBoxLayoutAlign.STRETCH);

        panel.setWidget(lcwest);

        BoxLayoutContainer.BoxLayoutData vBoxData = new BoxLayoutContainer.BoxLayoutData(new Margins(5, 5, 5, 5));
        vBoxData.setFlex(1);

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
        lcwest.add(users);

        final TextButton userEditing = new TextButton("User Editing");
        userEditing.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                AdminUserDataEditForm audf = new AdminUserDataEditForm(service, center, north, south, east, west);
                center.clear();
                center.add(audf.asWidget());
            }
        });
        lcwest.add(userEditing);

        final TextButton projectManagement = new TextButton("Project Management");
        projectManagement.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                ProjectRegistrationForm pgf = new ProjectRegistrationForm(service, center, west, east, north, south);
                center.clear();
                center.add(pgf.asWidget());
            }
        });
        lcwest.add(projectManagement);

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

        if(SessionInfo.projectDTO != null && isProductOwner()) {
            final TextButton userStoryManagement = new TextButton("User Story Management");
            userStoryManagement.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    if (SessionInfo.projectDTO == null) {
                        Info.display("No project selected", "Please select project from the list on the left.");
                    } else {
                        UserStoryRegistrationForm usrf = new UserStoryRegistrationForm(service, center, west, east, north, south);
                        center.clear();
                        center.add(usrf.asWidget());
                    }
                }
            });
            lcwest.add(userStoryManagement);
        }

        ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east, north, south);
        west.setHeadingText("Project list");
        west.add(psf.asWidget());

        panel.setWidth(PANEL_WIDTH);

    }

    private boolean isProductOwner() {
        if (SessionInfo.projectDTO.getTeamTeamId().getProductOwnerId() == SessionInfo.userDTO.getUserId()){
            return true;
        }
        return false;
    }
}