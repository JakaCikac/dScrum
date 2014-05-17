package si.fri.tpo.gwt.client.form.addedit;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 17/05/14.
 */
public class AcceptEditTasksDialog extends Dialog {

    private DScrumServiceAsync service;
    private ContentPanel center, west, east, north, south;
    private UserStoryDTO usDTO;

    public AcceptEditTasksDialog(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south, UserStoryDTO usDTO) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
        this.usDTO = usDTO;

        // Layout
        setBodyBorder(false);
        setHeadingText("Accept or edit tasks");

        setWidth(670);
        setHeight(455);
        setHideOnButtonClick(true);

        FlowLayoutContainer layout = new FlowLayoutContainer();
        add(layout);
        AcceptEditTasksForm aetf = new AcceptEditTasksForm(this.service, this.center, this.west, this.east, this.usDTO, this);
        layout.add(aetf.asWidget());
    }
}