package si.fri.tpo.gwt.client.form.search;

import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 12/04/14.
 */
public class SingleUserSearchDialog extends Dialog {

    private DScrumServiceAsync service;
    private SingleUserSearch sus;
    private SingleUserSearchCallback callback;

    public SingleUserSearchDialog(DScrumServiceAsync service, SingleUserSearchCallback callback) {
        this.service = service;
        this.callback = callback;


            setBodyBorder(false);
            setHeadingText("User Search Dialog");
            setWidth(500);
            setHeight(325);
            setHideOnButtonClick(true);

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
        }

    public SingleUserSearchCallback getCallback() {
        return callback;
    }
}

