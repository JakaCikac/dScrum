package si.fri.tpo.gwt.client.form.addedit;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 02/05/14.
 */
public class UserStoryEditDialog extends Dialog {

    private DScrumServiceAsync service;
    private ContentPanel center, west, east, north, south;
    private UserStoryDTO usDTO;

    public UserStoryEditDialog(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south, UserStoryDTO usDTO) {
            this.service = service;
            this.center = center;
            this.west = west;
            this.east = east;
            this.north = north;
            this.south = south;
            this.usDTO = usDTO;

            // Layout
            setBodyBorder(false);
            setHeadingText("User story edit dialog");

            setWidth(501);
            setHeight(615);
            setHideOnButtonClick(true);

            FlowLayoutContainer layout = new FlowLayoutContainer();
            add(layout);
            UserStoryEditForm usef = new UserStoryEditForm(this.service, this.center, this.west, this.east, this.usDTO, this);
            layout.add(usef.asWidget());
        }
}


