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
    private UserDTO userDTO;

    public SingleUserSearchDialog(DScrumServiceAsync service) {
        this.service = service;


            setBodyBorder(false);
            setHeadingText("BorderLayout Dialog");
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
                    userDTO = sus.getDTO();
                }
            });
        }

    private UserDTO get(UserDTO userDTO) {
        return this.userDTO;
    }
}

