package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.validator.MaxNumberValidator;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by anze on 20. 04. 14.
 */
public class SprintDataEditForm implements IsWidget {

    private DScrumServiceAsync service;
    private ContentPanel center, west, east, north, south;
    private VerticalPanel vp;
    private ListBox lb;
    private ArrayList<UserDTO> al;
    private ColumnModel<SprintDTO> cm;
    private Grid<SprintDTO> grid;
    private ListStore<SprintDTO> store;
    private SprintDTO sprintDTO;

    private IntegerField seqNumber;
    private DateField startDate;
    private DateField finishDate;
    private IntegerField velocity;
    private TextField status;

    private SubmitButton submitButton;
    private Button deleteButton;

    public SprintDataEditForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south)  {
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

    private void createSprintForm() {
        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("Sprint Creation Form");
        panel.setWidth(400);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        panel.add(p);

        ColumnConfig<SprintDTO, Integer> seqNumberCol = new ColumnConfig<SprintDTO, Integer>(getSeqNumberValue(), 80, "Seq. Number");
        ColumnConfig<SprintDTO, String> startDateCol = new ColumnConfig<SprintDTO, String>(getStartDateValue(), 80, "Start Date");
        ColumnConfig<SprintDTO, String> endDateCol = new ColumnConfig<SprintDTO, String>(getEndDateValue(), 80, "Finish Date");
        ColumnConfig<SprintDTO, Integer> velocityCol = new ColumnConfig<SprintDTO, Integer>(getVelocityValue(), 50, "Velocity");
        ColumnConfig<SprintDTO, String> statusCol = new ColumnConfig<SprintDTO, String>(getStatusValue(), 60, "Status");

        List<ColumnConfig<SprintDTO, ?>> l = new ArrayList<ColumnConfig<SprintDTO, ?>>();
        l.add(seqNumberCol);
        l.add(startDateCol);
        l.add(endDateCol);
        l.add(velocityCol);
        l.add(statusCol);
        cm = new ColumnModel<SprintDTO>(l);

        // initialize list store with model key provider (username)
        store = new ListStore<SprintDTO>(getModelKeyProvider());
        // retrieve user List and add data to list store
        ProjectDTO projectDTO = SessionInfo.projectDTO;
        store.addAll(projectDTO.getSprintList());

        grid = new com.sencha.gxt.widget.core.client.grid.Grid<SprintDTO>(store, cm);
        grid.setBorders(false);
        grid.getView().setStripeRows(true);
        grid.getView().setColumnLines(true);
        grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);

        ContentPanel panel1 = new ContentPanel();
        panel1.setHeaderVisible(false);
        //panel1.setPixelSize(200, 200);
        panel1.setSize("350", "100");

        panel1.setWidget(grid);
        p.add(panel1);
        grid.addRowClickHandler(new RowClickEvent.RowClickHandler() {
            @Override
            public void onRowClick(RowClickEvent event) {
                sprintDTO = grid.getSelectionModel().getSelectedItem();
                if (sprintDTO.getEndDate().before(new Date())){
                    sprintDTO = null;
                    emptyForm();
                    Info.display("Sprint finished", "This Sprint has already completed.");
                    submitButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                } else if(sprintDTO.getStartDate().before(new Date())){
                    sprintDTO = null;
                    emptyForm();
                    Info.display("Sprint in progress", "This Sprint is in progress.");
                    submitButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                } else {
                    fillForm();
                    submitButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                }
            }
        });

        seqNumber = new IntegerField();
        seqNumber.setEnabled(false);
        p.add(new FieldLabel(seqNumber, "Seq. Number"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        startDate = new DateField();
        p.add(new FieldLabel(startDate, "Start Date"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        startDate.addValueChangeHandler(new ValueChangeHandler<Date>() {
            @Override
            public void onValueChange(ValueChangeEvent<Date> event) {
                checkBoth();
            }
        });

        finishDate = new DateField();
        p.add(new FieldLabel(finishDate, "Finish Date"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        finishDate.addValueChangeHandler(new ValueChangeHandler<Date>() {
            @Override
            public void onValueChange(ValueChangeEvent<Date> event) {
                checkBoth();
            }
        });

        velocity = new IntegerField();
        velocity.setAllowBlank(false);
        velocity.setAllowNegative(false);
        velocity.addValidator(new MaxNumberValidator<Integer>(100));
        p.add(new FieldLabel(velocity, "Velocity"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        status = new TextField();
        status.setEnabled(false);
        p.add(new FieldLabel(status, "Status"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        submitButton = new SubmitButton("Update Sprint");
        submitButton.setEnabled(false);
        submitButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // Get Sprint Start Date
                // Get Sprint Finish Date
                // Get Sprint Velocity
                final SprintDTO sprintDTO = getSprintDTO();

                ProjectDTO projectDTO = SessionInfo.projectDTO;
                List<SprintDTO> sprintDTOList = projectDTO.getSprintList();

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
                    // Must exclude sprint in question.
                    if (sprintDTO.getSprintPK().getSprintId() != sprintDT.getSprintPK().getSprintId()) {
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

                sprintDTO.setStatus(status.getValue());
                sprintDTO.setProject(projectDTO);

                performUpdateSprint(sprintDTO);
            }
        });
        panel.addButton(submitButton);

        deleteButton = new Button("Delete");
        deleteButton.setEnabled(false);
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                //TODO: Delete sprint??
                final SprintDTO sprintDTO = getSprintDTO();
                ProjectDTO projectDTO = SessionInfo.projectDTO;
                List<SprintDTO> sprintDTOList = projectDTO.getSprintList();
                sprintDTO.setProject(projectDTO);
                performDeleteSprint(sprintDTO);
            }
        });
        panel.addButton(deleteButton);

        vp.add(panel);
    }

    private void performDeleteSprint(SprintDTO sprintDTO) {
        AsyncCallback<Pair<Boolean, String>> deleteSprint = new AsyncCallback<Pair<Boolean, String>>() {
            @Override
            public void onSuccess(Pair<Boolean, String> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing sprint deleting!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error deleting Sprint!", result.getSecond());
                    amb2.show();
                }
                else {
                    MessageBox amb3 = new MessageBox("Message delete Sprint", result.getSecond());
                    amb3.show();
                    center.clear();
                    west.clear();
                    east.clear();
                    SessionInfo.projectDTO = null;
                    if (SessionInfo.userDTO.isAdmin()){
                        AdminNavPanel adminNavPanel = new AdminNavPanel(center, west, east, north, south, service);
                        east.add(adminNavPanel.asWidget());
                    } else {
                        UserNavPanel userNavPanel = new UserNavPanel(service, center, west, east, north, south);
                        east.add(userNavPanel.asWidget());
                    }
                    ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east, north, south);
                    west.add(psf.asWidget());
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        //System.out.println("Calling updateSprint");
        // TODO: project name duplication
        service.deleteSprint(sprintDTO, deleteSprint);
    }

    private void emptyForm() {
        seqNumber.setText("");
        startDate.setValue(new Date());
        finishDate.setValue(new Date());
        velocity.setText("");
        status.setText("");
    }

    private void checkBoth() {
        if (startDate.getValue().after(new Date())){
            status.setValue("Waiting");
        } else if (startDate.getValue().before(new Date()) && finishDate.getValue().after(new Date())){
            status.setValue("In progress");
        } else if (finishDate.getValue().before(new Date())){
            status.setValue("Completed");
        }
    }

    private void fillForm() {
        seqNumber.setValue(sprintDTO.getSeqNumber());
        startDate.setValue(sprintDTO.getStartDate());
        finishDate.setValue(sprintDTO.getEndDate());
        velocity.setValue(sprintDTO.getVelocity());
        status.setValue(sprintDTO.getStatus());
    }

    // return the model key provider for the list store
    private ModelKeyProvider<SprintDTO> getModelKeyProvider() {
        ModelKeyProvider<SprintDTO> mkp = new ModelKeyProvider<SprintDTO>() {
            @Override
            public String getKey(SprintDTO item) {
                return item.getSeqNumber().toString();
            }
        };
        return mkp;
    }

    private ValueProvider<SprintDTO, Integer> getSeqNumberValue() {
        ValueProvider<SprintDTO, Integer> vpsn = new ValueProvider<SprintDTO, Integer>() {
            @Override
            public Integer getValue(SprintDTO object) {
                return object.getSeqNumber();
            }
            @Override
            public void setValue(SprintDTO object, Integer value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpsn;
    }

    private ValueProvider<SprintDTO, String> getStartDateValue() {
        ValueProvider<SprintDTO, String> vpsd = new ValueProvider<SprintDTO, String>() {
            @Override
            public String getValue(SprintDTO object) {
                return DateTimeFormat.getShortDateFormat().format(object.getStartDate());
            }
            @Override
            public void setValue(SprintDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpsd;
    }

    private ValueProvider<SprintDTO, String> getEndDateValue() {
        ValueProvider<SprintDTO, String> vped = new ValueProvider<SprintDTO, String>() {
            @Override
            public String getValue(SprintDTO object) {
                return DateTimeFormat.getShortDateFormat().format(object.getEndDate());
            }
            @Override
            public void setValue(SprintDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vped;
    }

    private ValueProvider<SprintDTO, Integer> getVelocityValue() {
        ValueProvider<SprintDTO, Integer> vpv = new ValueProvider<SprintDTO, Integer>() {
            @Override
            public Integer getValue(SprintDTO object) {
                return object.getVelocity();
            }
            @Override
            public void setValue(SprintDTO object, Integer value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpv;
    }

    private ValueProvider<SprintDTO, String> getStatusValue() {
        ValueProvider<SprintDTO, String> vps = new ValueProvider<SprintDTO, String>() {
            @Override
            public String getValue(SprintDTO object) {
                return object.getStatus();
            }
            @Override
            public void setValue(SprintDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vps;
    }

    private void performUpdateSprint(SprintDTO sprintDTO) {

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
                    MessageBox amb3 = new MessageBox("Message update Sprint", result.getSecond());
                    amb3.show();
                    center.clear();
                    west.clear();
                    east.clear();
                    SessionInfo.projectDTO = null;
                    if (SessionInfo.userDTO.isAdmin()){
                        AdminNavPanel adminNavPanel = new AdminNavPanel(center, west, east, north, south, service);
                        east.add(adminNavPanel.asWidget());
                    } else {
                        UserNavPanel userNavPanel = new UserNavPanel(service, center, west, east, north, south);
                        east.add(userNavPanel.asWidget());
                    }
                    ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east, north, south);
                    west.add(psf.asWidget());
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        //System.out.println("Calling updateSprint");
        // TODO: project name duplication
        service.updateSprint(sprintDTO, updateSprint);
    }

    public SprintDTO getSprintDTO() {
        return sprintDTO;
    }
}
