package si.fri.tpo.gwt.client.form.navigation;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import si.fri.tpo.gwt.client.form.addedit.UserDataEditForm;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 06/04/14.
 * Modified by t13as
 */
public class UserNavPanel implements IsWidget{
    private DScrumServiceAsync service;
    private ContentPanel center;
    private FlowLayoutContainer con;
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
        //con = new FlowLayoutContainer();
        //panel.add(con);
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

        ContentPanel cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setBodyStyleName("pad-text");

        TextButton userDataEditB = new TextButton("User Edit");
        userDataEditB.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                UserDataEditForm udef = new UserDataEditForm(service, center, north, south, east, west);
                center.clear();
                center.add(udef.asWidget());
            }
        });
        cp.add(userDataEditB);
        con.add(cp);

        ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east, north, south);
        west.setHeadingText("Project list");
        west.add(psf.asWidget());

        panel.setWidth(PANEL_WIDTH);
    }

}