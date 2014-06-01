package si.fri.tpo.gwt.client.form.registration;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.validator.MaxNumberValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinDateValidator;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.SprintPKDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.form.home.NorthForm;
import si.fri.tpo.gwt.client.form.home.UserHomeForm;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by t13as on 07-Apr-14.
 */
public class SprintRegistrationForm implements IsWidget {

    private DScrumServiceAsync service;
    private ContentPanel center, west, east, north, south;
    private VerticalPanel vp;
    private ListBox lb;
    private ArrayList<UserDTO> al;

    private DateField startDate;
    private DateField finishDate;
    private IntegerField velocity;

    private SubmitButton submitButton;

    public SprintRegistrationForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south)  {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
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

    @Deprecated
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
        final Date start= new Date();
        start.setDate(start.getDate()+1);
        finishDate.setValue(start);
        finishDate.setMinValue(startDate.getValue());
        finishDate.addValidator(new MinDateValidator(startDate.getValue()));
        p.add(new FieldLabel(finishDate, "Finish Date"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        velocity = new IntegerField();
        velocity.setAllowBlank(false);
        velocity.setAllowNegative(false);
        velocity.addValidator(new MaxNumberValidator<Integer>(100));
        p.add(new FieldLabel(velocity, "Velocity"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        submitButton = new SubmitButton("Create Sprint");
        submitButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // Get Sprint Start Date
                // Get Sprint Finish Date
                // Get Sprint Velocity
                final SprintDTO sprintDTO = new SprintDTO();
                //TODO: SEQUENCE NUMBER NOT WORKING!!!

                List<SprintDTO> sprintDTOList = SessionInfo.projectDTO.getSprintList();

                /* ------------------------------- VALIDATORS --------------------------------- */
                Date today = new Date(); // Get today's date.
                Date yesterday = new Date();
                yesterday.setDate(today.getDate()-1);
                // Check if sprint is in past.
                if (startDate.getValue().before(yesterday)) {
                    AlertMessageBox d = new AlertMessageBox("Sprint in past", "Sprints cannot be entered in the past.");
                    d.show();
                    return;
                }

                // Check if sprint ends before it started.
                if (finishDate.getValue().before(startDate.getValue()) || finishDate.getValue().equals(startDate.getValue())) {
                    AlertMessageBox d = new AlertMessageBox("Wrong Finish Date", "Finish date must be after start date.");
                    d.show();
                    return;
                }

                // Check if two sprints overlap.
                for (SprintDTO sprintDT : sprintDTOList) {
                    if (startDate.getValue().before(sprintDT.getEndDate()) && finishDate.getValue().after(sprintDT.getStartDate())) {
                        AlertMessageBox d = new AlertMessageBox("Wrong date range", "Sprint already exists in selected date range.");
                        d.show();
                        return;
                    }
                    if (startDate.getValue().equals(sprintDT.getEndDate()) || finishDate.getValue().equals(sprintDT.getStartDate())) {
                        AlertMessageBox d = new AlertMessageBox("Wrong date range", "Next sprint cannot begin the same day previous sprint ended and vice versa.");
                        d.show();
                        return;
                    }
                }

                // If all date validation tests have been passed, enter the dates into DTO.
                sprintDTO.setStartDate(startDate.getValue());
                sprintDTO.setEndDate(finishDate.getValue());

                // Check if velocity is entered.
                if (velocity.getText().equals("")) {
                    AlertMessageBox d = new AlertMessageBox("Velocity empty", "Please enter sprint velocity!");
                    d.show();
                    return;
                }

                int sprintVelocity = Integer.parseInt(velocity.getText()); // Convert sprint velocity to integer.

                // Check if velocity is greater than 0.
                if (sprintVelocity < 1) {
                    AlertMessageBox d = new AlertMessageBox("Velocity zero", "Please enter sprint velocity that is greater than zero!");
                    d.show();
                    return;
                }

                // If velocity validators have been passed enter sprint velocity into DTO.
                sprintDTO.setVelocity(sprintVelocity);

                /* ----------------------------- END VALIDATORS ------------------------------- */

                // Sets sprint status according to its date boundaries.
                if (startDate.getValue().before(today)) {
                    sprintDTO.setStatus("In progress");
                } else {
                    sprintDTO.setStatus("Waiting");
                }

                //System.out.println("Number of sprint2: " + sprintDTOList.size());
                if (sprintDTOList == null){
                    sprintDTO.setSeqNumber(1);
                } else {
                    sprintDTO.setSeqNumber(sprintDTOList.size()+1);
                }
                sprintDTO.setProject(SessionInfo.projectDTO);
                for (SprintDTO sprintDTO1 : sprintDTOList){
                    System.out.println("Sprint id: " + sprintDTO1.getSprintPK().getSprintId());
                }

                performSaveSprint(sprintDTO);
            }
        });
        panel.addButton(submitButton);

        vp.add(panel);
    }

    private void performSaveSprint(final SprintDTO sprintDTO) {

        AsyncCallback<Pair<Boolean, Integer>> saveSprint = new AsyncCallback<Pair<Boolean, Integer>>() {
            @Override
            public void onSuccess(Pair<Boolean, Integer> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing sprint saving!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error saving Sprint!", result.getSecond().toString());
                    amb2.show();
                } else if (result.getSecond() == -1){
                    AlertMessageBox amb2 = new AlertMessageBox("Error getting SprintID!", result.getSecond().toString());
                    amb2.show();
                } else {
                    SprintPKDTO sprintPKDTO = new SprintPKDTO();
                    sprintPKDTO.setSprintId(result.getSecond());
                    sprintPKDTO.setProjectProjectId(SessionInfo.projectDTO.getProjectId());
                    sprintDTO.setSprintPK(sprintPKDTO);
                    AsyncCallback<Pair<Boolean, String>> updateSprint = new AsyncCallback<Pair<Boolean, String>>() {
                        @Override
                        public void onSuccess(Pair<Boolean, String> result) {
                            if (result == null) {
                                AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing sprint updating!");
                                amb2.show();
                            }
                            else if (!result.getFirst()) {
                                AlertMessageBox amb2 = new AlertMessageBox("Error updating Sprint!", result.getSecond());
                                amb2.show();
                            }
                            else {
                                SessionInfo.projectDTO = null;
                                north.clear();
                                west.clear();
                                east.clear();
                                center.clear();
                                NorthForm nf = new NorthForm(service, center, north, south, east, west);
                                north.add(nf.asWidget());
                                if (SessionInfo.userDTO.isAdmin()) {
                                    AdminNavPanel adminNavPanel = new AdminNavPanel(center, west, east, north, south, service);
                                    east.add(adminNavPanel.asWidget());
                                } else {
                                    UserNavPanel userNavPanel = new UserNavPanel(service, center, west, east, north, south);
                                    east.add(userNavPanel.asWidget());
                                }
                                ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east, north, south);
                                west.add(psf.asWidget());
                                UserHomeForm userHomeForm = new UserHomeForm(service, center, west, east, north, south);
                                center.add(userHomeForm.asWidget());
                            }
                        }
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
                    };
                    System.out.println("Calling updateSprint");
                    // TODO: project name duplication
                    service.updateSprint(sprintDTO, updateSprint);
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        System.out.println("Calling saveSprint");
        service.saveSprint(sprintDTO, saveSprint);
    }

}
