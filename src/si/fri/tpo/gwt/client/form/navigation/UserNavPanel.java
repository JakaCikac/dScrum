package si.fri.tpo.gwt.client.form.navigation;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.legacy.client.data.BaseModelData;
import com.sencha.gxt.legacy.client.data.ModelData;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 06/04/14.
 * Modified by t13as
 */
public class UserNavPanel implements IsWidget{
    private DScrumServiceAsync service;

    public UserNavPanel(DScrumServiceAsync service) {
        this.service = service;
    }

    private ContentPanel panel;
    public Widget asWidget() {
        panel = new ContentPanel();
        AccordionLayoutContainer con = new AccordionLayoutContainer();
        con.setExpandMode(AccordionLayoutContainer.ExpandMode.SINGLE_FILL);
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
        cp.setExpanded(true);
        cp.setBodyStyleName("pad-text");

        TextButton userDataEditB = new TextButton("Edit user");
        userDataEditB.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                System.out.println("Registered click on edit profile");
                //UserDataEditForm udef = new UserDataEditForm(service);
                //RootPanel.get().add(udef);
            }
        });
        cp.add(userDataEditB);

        panel.add(cp);
        panel.setWidth(270);

    }

    private ModelData newItem(String text, String iconStyle) {
        ModelData m = new BaseModelData();
        m.set("name", text);
        m.set("icon", iconStyle);
        return m;
    }

}