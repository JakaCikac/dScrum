package si.fri.tpo.gwt.client.form.registration;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.validator.MaxDateValidator;
import com.sencha.gxt.widget.core.client.form.validator.MaxNumberValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinDateValidator;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.TeamDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;
import si.fri.tpo.gwt.server.jpa.Project;
import si.fri.tpo.gwt.server.jpa.Team;
import si.fri.tpo.gwt.server.jpa.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by t13as on 07-Apr-14.
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
        final Date start= startDate.getValue();
        start.setDate(startDate.getValue().getDate()+1);
        finishDate.setValue(start);
        finishDate.setMinValue(startDate.getValue());
        finishDate.addValidator(new MinDateValidator(startDate.getValue()));
        p.add(new FieldLabel(finishDate, "Finish Date"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        velocity = new IntegerField();
        velocity.setAllowBlank(false);
        velocity.setAllowNegative(false);
        velocity.addValidator(new MaxNumberValidator<Integer>(100));
        p.add(new FieldLabel(velocity, "Velocity"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        submitButton = new TextButton("Create Sprint");
        submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                // Get Sprint Start Date
                // Get Sprint Finish Date
                // Get Sprint Velocity
                final SprintDTO sprintDTO = new SprintDTO();
                sprintDTO.setStartDate(startDate.getValue());
                sprintDTO.setEndDate(finishDate.getValue());
                sprintDTO.setVelocity(Integer.parseInt(velocity.getText()));
                if (startDate.getValue().after(new Date())) {
                    sprintDTO.setStatus("In progress");
                } else {
                    sprintDTO.setStatus("Waiting");
                }
                ProjectDTO projectDTO = SessionInfo.projectDTO;
                List<SprintDTO> sprintDTOList = projectDTO.getSprintList();
                if(sprintDTOList == null){
                    sprintDTO.setSeqNumber(1);
                } else {
                    sprintDTO.setSeqNumber(sprintDTOList.size()+1);
                }
                sprintDTO.setProject(projectDTO);

                performSaveSprint(sprintDTO);

            }
        });
        panel.addButton(submitButton);

        vp.add(panel);
    }

    private void performSaveSprint(SprintDTO sprintDTO) {

        AsyncCallback<Pair<Boolean, String>> saveSprint = new AsyncCallback<Pair<Boolean, String>>() {
            @Override
            public void onSuccess(Pair<Boolean, String> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing sprint saving!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error saving Sprint!", result.getSecond());
                    amb2.show();
                }
                else {
                    AlertMessageBox amb3 = new AlertMessageBox("Message save Sprint", result.getSecond());
                    amb3.show();
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        System.out.println("Calling saveSprint");
        // TODO: project name duplication
        service.saveSprint(sprintDTO, saveSprint);
    }
}
