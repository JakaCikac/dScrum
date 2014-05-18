package si.fri.tpo.gwt.client.form.search;

import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.form.home.UserHomeForm;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

/**
 * Created by nanorax on 12/04/14.
 */
public class SingleUserSearchDialog extends Dialog {

    private DScrumServiceAsync service;
    private ContentPanel center, west, east, north, south;
    private SingleUserSearch sus;
    private SingleUserSearchCallback callback;

    public SingleUserSearchDialog(DScrumServiceAsync service, SingleUserSearchCallback callback, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south) {
        this.service = service;
        this.callback = callback;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;


        setBodyBorder(false);
        setHeadingText("User Search Dialog");
        setWidth(500);
        setHeight(325);
        setHideOnButtonClick(true);

        ClearPanels();

        BorderLayoutContainer layout = new BorderLayoutContainer();
        add(layout);

        ContentPanel panel;
        // Layout - center
        panel = new ContentPanel();
        panel.setHeadingText("Center");
        sus = new SingleUserSearch(service);
        panel.add(sus.asWidget());
        layout.setCenterWidget(panel);
        getButton(PredefinedButton.OK).setText("Select");
        getButton(PredefinedButton.OK).addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                final UserDTO userDTO = sus.getDTO();
                getCallback().userSearchCallback(userDTO);
            }
        });
        center.disable();
        east.disable();
        west.disable();
        north.disable();
        south.disable();
    }

    public SingleUserSearchCallback getCallback() {
        return callback;
    }

    private void ClearPanels(){
        addHideHandler(new HideEvent.HideHandler() {
            @Override
            public void onHide(HideEvent event) {
                center.enable();
                east.enable();
                west.enable();
                north.enable();
                south.enable();
            }
        });
    }
}

