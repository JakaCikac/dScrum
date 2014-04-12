package si.fri.tpo.gwt.client.form.registration;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.validator.MaxDateValidator;
import com.sencha.gxt.widget.core.client.form.validator.MaxNumberValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinDateValidator;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 07-Apr-14.
 */
public class SprintRegistrationForm implements IsWidget {

    private DScrumServiceAsync service;
    private VerticalPanel vp;
    private ListBox lb;
    private ArrayList<UserDTO> al;

    private DateField startDate;
    private DateField finishDate;
    private IntegerField velocity;

    private TextButton submitButton;

    public SprintRegistrationForm(DScrumServiceAsync service)  {
        this.service = service;
    }

    @Override
    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);
            createSprintForm();
        }
        return vp;
    }

    private void createSprintForm() {
        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("Sprint Creation Form");
        panel.setWidth(350);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        panel.add(p);

        startDate = new DateField();
        startDate.setValue(new Date());
        p.add(new FieldLabel(startDate, "Start Date"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        finishDate = new DateField();
        Date start= startDate.getValue();
        start.setDate(startDate.getValue().getDate()+1);
        finishDate.setValue(start);
        finishDate.setMinValue(startDate.getValue());
        finishDate.addValidator(new MinDateValidator(startDate.getValue()));
        p.add(new FieldLabel(finishDate, "Finish Date"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        startDate.setMaxValue(finishDate.getValue());
        startDate.addValidator(new MaxDateValidator(finishDate.getValue()));

        velocity = new IntegerField();
        velocity.setAllowBlank(false);
        velocity.setAllowNegative(false);
        velocity.addValidator(new MaxNumberValidator<Integer>(100));
        p.add(new FieldLabel(velocity, "Velocity"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        submitButton = new TextButton("Create Sprint");
        submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                // TODO: add sprint to database
            }
        });
        panel.addButton(submitButton);


        vp.add(panel);
    }
}
