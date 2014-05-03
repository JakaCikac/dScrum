package si.fri.tpo.gwt.client.form.registration;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.form.addedit.UserStoryEditForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 02/05/14.
 */
public class TaskRegistrationDialog extends Dialog {

    private DScrumServiceAsync service;
    private ContentPanel center, west, east;
    private UserStoryDTO usDTO;

    public TaskRegistrationDialog(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, UserStoryDTO usDTO) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.usDTO = usDTO;

        // Layout
        setBodyBorder(false);
        setHeadingText("Task registration form");

        setWidth(400);
        setHeight(225);
        setHideOnButtonClick(true);

        FlowLayoutContainer layout = new FlowLayoutContainer();
        add(layout);
        TaskRegistrationForm trf = new TaskRegistrationForm(this.service, this.center, this.west, this.east, this.usDTO);
        layout.add(trf.asWidget());
    }
}