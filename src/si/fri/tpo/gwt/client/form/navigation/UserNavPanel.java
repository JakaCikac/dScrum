package si.fri.tpo.gwt.client.form.navigation;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.legacy.client.data.BaseModelData;
import com.sencha.gxt.legacy.client.data.ModelData;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import si.fri.tpo.gwt.client.form.addedit.UserDataEditForm;
import si.fri.tpo.gwt.client.form.registration.SprintRegistrationForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 06/04/14.
 * Modified by t13as
 */
public class UserNavPanel implements IsWidget{
    private DScrumServiceAsync service;
    private ContentPanel center;
    private FlowLayoutContainer con;

    public UserNavPanel(DScrumServiceAsync service, ContentPanel center) {
        this.center = center;
        this.service = service;
    }

    private ContentPanel panel;
    public Widget asWidget() {
        panel = new ContentPanel();
        con = new FlowLayoutContainer();
        panel.add(con);
        panel.setHeadingText("User menu");
        panel.setBodyBorder(false);
        createUserNavPanel();
        return panel;
    }

    private void createUserNavPanel() {

        ContentPanel cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setBodyStyleName("pad-text");

        TextButton userDataEditB = new TextButton("Edit user");
        userDataEditB.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                UserDataEditForm udef = new UserDataEditForm(service);
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

        TextButton sprintRegistrationB = new TextButton("Sprint Management");
        sprintRegistrationB.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                SprintRegistrationForm srf = new SprintRegistrationForm(service);
                center.clear();
                center.add(srf.asWidget());
            }
        });
        cp.add(sprintRegistrationB);
        con.add(cp);

        panel.setWidth(270);
    }

    private ModelData newItem(String text, String iconStyle) {
        ModelData m = new BaseModelData();
        m.set("name", text);
        m.set("icon", iconStyle);
        return m;
    }

}