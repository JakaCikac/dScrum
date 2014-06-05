package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.IdentityValueProvider;
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
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
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
 * Created by anze on 30. 04. 14.
 */
public class AddStoryToSprintForm implements IsWidget{

    private DScrumServiceAsync service;
    private ContentPanel center, west, east, north, south;
    private VerticalPanel vp;
    private ColumnModel<SprintDTO> cm;
    private Grid<SprintDTO> grid;
    private ListStore<SprintDTO> store;
    private SprintDTO sprintDTO;
    private ListStore<UserStoryDTO> storeUS;
    private Grid<UserStoryDTO> gridUS;
    private ProjectDTO projectDTO;
    private CheckBoxSelectionModel<UserStoryDTO> smUS;

    private SubmitButton submitButton;

    public AddStoryToSprintForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south)  {
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
            createStoryToSprintForm();
        }
        return vp;
    }

    private void createStoryToSprintForm() {

        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("Sprint Creation Form");
        panel.setWidth(550);
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
        projectDTO = SessionInfo.projectDTO;
        store.addAll(projectDTO.getSprintList());

        grid = new com.sencha.gxt.widget.core.client.grid.Grid<SprintDTO>(store, cm);
        grid.setBorders(false);
        grid.getView().setStripeRows(true);
        grid.getView().setColumnLines(true);
        grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);

        ContentPanel panel1 = new ContentPanel();
        panel1.setHeaderVisible(true);
        panel1.setHeadingText("Select Sprint");
        panel1.setSize("500", "200");

        panel1.setWidget(grid);
        p.add(panel1);
        grid.addRowClickHandler(new RowClickEvent.RowClickHandler() {
            @Override
            public void onRowClick(RowClickEvent event) {
                sprintDTO = grid.getSelectionModel().getSelectedItem();
                if (sprintDTO.getEndDate().before(new Date())){
                    storeUS.clear();
                    sprintDTO = null;
                    submitButton.setEnabled(false);
                    Info.display("Sprint finished", "This Sprint has already completed.");
                } else if(sprintDTO.getStartDate().before(new Date())){
                    storeUS.clear();
                    sprintDTO = null;
                    submitButton.setEnabled(false);
                    Info.display("Sprint in progress", "This Sprint is in progress.");
                } else {
                    setStoreUS();
                    submitButton.setEnabled(true);
                }
            }
        });

        IdentityValueProvider<UserStoryDTO> identityUS = new IdentityValueProvider<UserStoryDTO>();
        smUS = new CheckBoxSelectionModel<UserStoryDTO>(identityUS);

        ColumnConfig<UserStoryDTO, String> nameCol = new ColumnConfig<UserStoryDTO, String>(getNameValue(), 100, "Name");
        ColumnConfig<UserStoryDTO, String> contentCol = new ColumnConfig<UserStoryDTO, String>(getContentValue(), 100, "Content");
        ColumnConfig<UserStoryDTO, Integer> businessValueCol = new ColumnConfig<UserStoryDTO, Integer>(getBusinessValue(), 75, "Business value");
        ColumnConfig<UserStoryDTO, String> statusUSCol = new ColumnConfig<UserStoryDTO, String>(getStatusUSValue(), 100, "Status");
        ColumnConfig<UserStoryDTO, Double> estimamteTimeCol = new ColumnConfig<UserStoryDTO, Double>(getEstimateTimeValue(), 100, "Estimate time");

        List<ColumnConfig<UserStoryDTO, ?>> lUS = new ArrayList<ColumnConfig<UserStoryDTO, ?>>();
        lUS.add(smUS.getColumn());
        lUS.add(nameCol);
        lUS.add(contentCol);
        lUS.add(businessValueCol);
        lUS.add(statusUSCol);
        lUS.add(estimamteTimeCol);
        ColumnModel<UserStoryDTO> cmUS = new ColumnModel<UserStoryDTO>(lUS);

        storeUS = new ListStore<UserStoryDTO>(getModelKeyProviderUS());

        ContentPanel panel2 = new ContentPanel();
        panel2.setHeadingText("Select User Story for Sprint");
        panel2.setPixelSize(500, 320);
        panel2.addStyleName("margin-10");

        gridUS = new Grid<UserStoryDTO>(storeUS, cmUS);
        gridUS.setSelectionModel(smUS);
        gridUS.getView().setAutoExpandColumn(nameCol);
        gridUS.setBorders(false);
        gridUS.getView().setStripeRows(true);
        gridUS.getView().setColumnLines(true);

        panel2.setWidget(gridUS);
        p.add(panel2);

        submitButton = new SubmitButton("Add to Sprint");
        submitButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                for (UserStoryDTO userStoryDTO : smUS.getSelection()){
                    userStoryDTO.setSprint(sprintDTO);
                    performSaveUserStory(userStoryDTO);
                }
                sprintDTO.setUserStoryList(smUS.getSelection());
                performSaveSprint(sprintDTO);
            }
        });
        submitButton.setEnabled(false);
        panel.addButton(submitButton);
        vp.add(panel);
    }

    private void performSaveSprint(SprintDTO sprintDTO) {
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
        service.updateSprint(sprintDTO, updateSprint);
    }

    private void performSaveUserStory(UserStoryDTO userStoryDTO) {
        AsyncCallback<Pair<Boolean, String>> updateUserStory = new AsyncCallback<Pair<Boolean, String>>() {
            @Override
            public void onSuccess(Pair<Boolean, String> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing user story updating!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error updating user story!", result.getSecond());
                    amb2.show();
                }
                else {
                    MessageBox amb3 = new MessageBox("Message update User Story", result.getSecond());
                    //amb3.show();
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.updateUserStory(userStoryDTO, updateUserStory);
    }

    private void setStoreUS() {
        List<UserStoryDTO> userStoryDTOList = projectDTO.getUserStoryList();
        boolean fill = false;
        if (userStoryDTOList == null) {
            System.out.println("userStoryDTOList je null");
            storeUS.clear();
        } else {
            for (UserStoryDTO userStoryDTO : userStoryDTOList){
                if (!userStoryDTO.getStatus().equals("Finished") && userStoryDTO.getEstimateTime() != null) {
                    if (userStoryDTO.getSprint() == null) {
                        storeUS.add(userStoryDTO);
                        fill = true;
                    } else if (userStoryDTO.getSprint().getSprintPK().getSprintId() == sprintDTO.getSprintPK().getSprintId()) {
                        storeUS.add(userStoryDTO);
                        smUS.select(userStoryDTO, true);
                        fill = true;
                    }
                }
            }
        }
        if (!fill) storeUS.clear();
    }

    // ValueProviders for UserStory //
    private ModelKeyProvider<UserStoryDTO> getModelKeyProviderUS() {
        ModelKeyProvider<UserStoryDTO> mkp = new ModelKeyProvider<UserStoryDTO>() {
            @Override
            public String getKey(UserStoryDTO item) {
                return item.getStoryId().toString();
            }
        };
        return mkp;
    }

    private ValueProvider<UserStoryDTO, String> getNameValue() {
        ValueProvider<UserStoryDTO, String> vpn = new ValueProvider<UserStoryDTO, String>() {
            @Override
            public String getValue(UserStoryDTO object) {
                return object.getName();
            }
            @Override
            public void setValue(UserStoryDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpn;
    }

    private ValueProvider<UserStoryDTO, String> getContentValue() {
        ValueProvider<UserStoryDTO, String> vpc = new ValueProvider<UserStoryDTO, String>() {
            @Override
            public String getValue(UserStoryDTO object) {
                return object.getContent();
            }
            @Override
            public void setValue(UserStoryDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpc;
    }

    private ValueProvider<UserStoryDTO, Integer> getBusinessValue() {
        ValueProvider<UserStoryDTO, Integer> vpb = new ValueProvider<UserStoryDTO, Integer>() {
            @Override
            public Integer getValue(UserStoryDTO object) {
                return object.getBusinessValue();
            }
            @Override
            public void setValue(UserStoryDTO object, Integer value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpb;
    }

    private ValueProvider<UserStoryDTO, String> getStatusUSValue() {
        ValueProvider<UserStoryDTO, String> vps = new ValueProvider<UserStoryDTO, String>() {
            @Override
            public String getValue(UserStoryDTO object) {
                return object.getStatus();
            }
            @Override
            public void setValue(UserStoryDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vps;
    }

    private ValueProvider<UserStoryDTO, Double> getEstimateTimeValue() {
        ValueProvider<UserStoryDTO, Double> vps = new ValueProvider<UserStoryDTO, Double>() {
            @Override
            public Double getValue(UserStoryDTO object) {
                return object.getEstimateTime();
            }
            @Override
            public void setValue(UserStoryDTO object, Double value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vps;
    }
    // ValueProviders for UserStory //

    // ValueProviders for Sprint //
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

    @Deprecated
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

    @Deprecated
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
    // ValueProviders for Sprint //
}
