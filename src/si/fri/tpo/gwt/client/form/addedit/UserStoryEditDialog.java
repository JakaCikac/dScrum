package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

import java.util.Date;

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
            System.out.println("usDTO name: " + usDTO.getName());
            System.out.println("this.usDTO name: " + this.usDTO.getName());

            // Layout
            setBodyBorder(false);
            setHeadingText("User story edit dialog");

            setWidth(400);
            setHeight(225);
            setHideOnButtonClick(true);

            FlowLayoutContainer layout = new FlowLayoutContainer();
            add(layout);
            UserStoryEditForm usef = new UserStoryEditForm(this.service, this.center, this.west, this.east, this.usDTO);
            layout.add(usef.asWidget());
        }
}


