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

    public SprintDataEditForm(DScrumServiceAsync service)  {
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
        panel.setWidth(400);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        panel.add(p);

        //RowNumberer<SprintDTO> numberer = new RowNumberer<SprintDTO>();
        ColumnConfig<SprintDTO, Integer> seqNumberCol = new ColumnConfig<SprintDTO, Integer>(getSeqNumberValue(), 80, "Seq. Number");
        ColumnConfig<SprintDTO, String> startDateCol = new ColumnConfig<SprintDTO, String>(getStartDateValue(), 80, "Start Date");
        ColumnConfig<SprintDTO, String> endDateCol = new ColumnConfig<SprintDTO, String>(getEndDateValue(), 80, "Finish Date");
        ColumnConfig<SprintDTO, Integer> velocityCol = new ColumnConfig<SprintDTO, Integer>(getVelocityValue(), 50, "Velocity");
        ColumnConfig<SprintDTO, String> statusCol = new ColumnConfig<SprintDTO, String>(getStatusValue(), 60, "Status");

        List<ColumnConfig<SprintDTO, ?>> l = new ArrayList<ColumnConfig<SprintDTO, ?>>();
        //l.add(numberer);
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

        //numberer.initPlugin(grid);

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
                } else if(sprintDTO.getStartDate().before(new Date())){
                    sprintDTO = null;
                    emptyForm();
                    Info.display("Sprint in progress", "This Sprint is in progress.");
                } else fillForm();
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
        submitButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // Get Sprint Start Date
                // Get Sprint Finish Date
                // Get Sprint Velocity
                final SprintDTO sprintDTO = getSprintDTO();

                ProjectDTO projectDTO = SessionInfo.projectDTO;
                List<SprintDTO> sprintDTOList = projectDTO.getSprintList();
                /*if(sprintDTOList == null){
                    sprintDTO.setSeqNumber(1);
                } else {
                    sprintDTO.setSeqNumber(sprintDTOList.size()+1);
                }*/
                sprintDTO.setProject(projectDTO);

                for (SprintDTO sprintDT : sprintDTOList) {
                    if (startDate.getValue().before(sprintDT.getEndDate())) {
                        AlertMessageBox d = new AlertMessageBox("Wrong Start Date", "Sprint v tem časovnem obdobju že obstaja.");
                        d.show();
                        return;
                    } else {
                        sprintDTO.setStartDate(startDate.getValue());
                        break;
                    }
                }

                if (finishDate.getValue().before(startDate.getValue())) {
                    AlertMessageBox d = new AlertMessageBox("Wrong Finish Date", "Finish Date must be after Start Date.");
                    d.show();
                    return;
                } else {
                    sprintDTO.setEndDate(finishDate.getValue());
                }

                if (velocity.getText().equals("")){
                    AlertMessageBox d = new AlertMessageBox("Velocity empty", "Please enter sprint velocity!");
                    d.show();
                    return;
                } else {
                    sprintDTO.setVelocity(Integer.parseInt(velocity.getText()));
                }

                sprintDTO.setStatus(status.getValue());

                performUpdateSprint(sprintDTO);
            }
        });
        panel.addButton(submitButton);

        deleteButton = new Button("Delete");
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
