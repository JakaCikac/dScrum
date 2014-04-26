package si.fri.tpo.gwt.client.form.registration;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 26/04/14.
 */
public class TaskRegistrationForm implements IsWidget {

    private DScrumServiceAsync service;
    private ContentPanel center, west, east;
    private VerticalPanel vp;

    private TextArea description;
    private Radio waiting;

    private ProjectDTO projectDTO;

    private TextButton submitButton;

    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);
            createTaskForm();
        }
        return vp;
    }

    public TaskRegistrationForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
    }

    private void createTaskForm() {
        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("Task Creation Form");
        panel.setWidth(320);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        panel.add(p);

        description = new TextArea();
        description.setAllowBlank(false);
        description.addValidator(new MinLengthValidator(10));
        p.add(new FieldLabel(description, "Description *"), new VerticalLayoutContainer.VerticalLayoutData(1, 100));

        submitButton = new TextButton("Create");
        submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {

            }
        });
        panel.addButton(submitButton);
        vp.add(panel);
    }

}
