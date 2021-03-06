package si.fri.tpo.gwt.client.form.navigation;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.form.addedit.UserDataEditForm;
import si.fri.tpo.gwt.client.form.registration.UserStoryRegistrationForm;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

/**
 * Created by nanorax on 06/04/14.
 * Modified by Anze
 */
public class UserNavPanel implements IsWidget{
    private DScrumServiceAsync service;
    private ContentPanel center;
    private ContentPanel west;
    private ContentPanel east, north, south;
    private ContentPanel panel;

    private static final int PANEL_WIDTH = 230;
    private static final int PANEL_HEIGHT = 400;

    public UserNavPanel(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south) {
        this.center = center;
        this.service = service;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
    }

    public Widget asWidget() {
        panel = new ContentPanel();
        panel.setHeadingText("User menu");
        panel.setBodyBorder(false);

        BorderLayoutContainer border = new BorderLayoutContainer();
        panel.setWidget(border);

        createUserNavPanel();
        return panel;
    }

    private void createUserNavPanel() {
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