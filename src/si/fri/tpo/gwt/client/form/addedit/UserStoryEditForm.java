package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.validator.MaxNumberValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinNumberValidator;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.RowNumberer;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.AcceptanceTestDTO;
import si.fri.tpo.gwt.client.dto.PriorityDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.form.home.UserHomeForm;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by t13db on 30.4.2014.
 */
public class UserStoryEditForm implements IsWidget, Editor<UserStoryDTO> {

    private DScrumServiceAsync service;

    interface Driver extends SimpleBeanEditorDriver<UserStoryDTO, UserStoryEditForm> {}
    private Driver driver = GWT.create(Driver.class);

    // GUI components
    private VerticalPanel verticalPanel;
    private HorizontalPanel userStoryPriorityHorizontalPanel;
    private ContentPanel center, west, east, north, south;
    private FlowPanel userStoryContainer, acceptanceTestsContainer;

    private TextField userStoryName;
    private TextArea userStoryContent;
    private IntegerField userStoryBusinessValue;
    private Radio couldHave;
    private Radio shouldHave;
    private Radio mustHave;
    private Radio wontHave;
    private TextButton submitButton;
    private Grid<AcceptanceTestDTO> acceptanceTestGrid;
    private TextButton acceptanceTestDeleteButton;
    private TextButton acceptanceTestCreateButton;
    private DoubleField estimateTimeDoubleField;

    // vars
    static private int acceptanceTestCount;
    private ListStore<AcceptanceTestDTO> acceptanceTestStore;
    private ListStoreEditor<AcceptanceTestDTO> editAcceptanceTestStore;
    private List<AcceptanceTestDTO> acceptanceTestDTOList;
    private ToggleGroup userStoryPriorityToggleGroup;
    private UserStoryEditDialog used;

    public void setSelectedUserStoryDTO(UserStoryDTO selectedUserStoryDTO) {
        this.selectedUserStoryDTO = selectedUserStoryDTO;
    }

    private UserStoryDTO selectedUserStoryDTO;

    public UserStoryEditForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, UserStoryDTO usDTO, UserStoryEditDialog used) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.acceptanceTestCount = 0;
        this.selectedUserStoryDTO = usDTO;
        this.used = used;
        setSelectedUserStoryDTO(this.selectedUserStoryDTO);
    }

    @Override
    public Widget asWidget() {
        if (verticalPanel == null) {
            verticalPanel = new VerticalPanel();
            verticalPanel.setSpacing(10);
            createUserStoryEditForm();
        }
        return verticalPanel;
    }

    private void createUserStoryEditForm() {

        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("User Story Edit Form");
        panel.setWidth(470);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
        panel.add(verticalLayoutContainer);

        // prepare user story selection grid
        userStoryContainer = new FlowPanel();

        verticalLayoutContainer.add(userStoryContainer);

        // prepare user story name field
        userStoryName = new TextField();
        userStoryName.setAllowBlank(false);
        verticalLayoutContainer.add(new FieldLabel(userStoryName, "User Story Name *"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        // prepare content field
        userStoryContent = new TextArea();
        userStoryContent.setAllowBlank(false);
        userStoryContent.addValidator(new MinLengthValidator(10));
        verticalLayoutContainer.add(new FieldLabel(userStoryContent, "Content *"), new VerticalLayoutContainer.VerticalLayoutData(1, 100));

        // prepare business value field
        userStoryBusinessValue = new IntegerField();
        userStoryBusinessValue.setAllowBlank(false);
        userStoryBusinessValue.setAllowNegative(false);
        verticalLayoutContainer.add(new FieldLabel(userStoryBusinessValue, "Business Value *"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        // prepare priority
        mustHave = new Radio();
        mustHave.setBoxLabel("Must Have");
        mustHave.setValue(true);
        mustHave.setName("1");
        shouldHave = new Radio();
        shouldHave.setBoxLabel("Should Have");
        shouldHave.setName("2");
        couldHave = new Radio();
        couldHave.setBoxLabel("Could Have");
        couldHave.setName("3");
        wontHave = new Radio();
        wontHave.setBoxLabel("Won't Have This Time");
        wontHave.setName("4");

        userStoryPriorityHorizontalPanel = new HorizontalPanel();
        userStoryPriorityHorizontalPanel.add(mustHave);
        userStoryPriorityHorizontalPanel.add(shouldHave);
        userStoryPriorityHorizontalPanel.add(couldHave);
        userStoryPriorityHorizontalPanel.add(wontHave);
        verticalLayoutContainer.add(new FieldLabel(userStoryPriorityHorizontalPanel, "Priority *"));

        userStoryPriorityToggleGroup = new ToggleGroup();
        userStoryPriorityToggleGroup.add(mustHave);
        userStoryPriorityToggleGroup.add(shouldHave);
        userStoryPriorityToggleGroup.add(couldHave);
        userStoryPriorityToggleGroup.add(wontHave);

        // prepare estimate time field
        estimateTimeDoubleField = new DoubleField();
        estimateTimeDoubleField.setAllowNegative(false);
        estimateTimeDoubleField.setAllowBlank(true);
        estimateTimeDoubleField.addValidator(new MinNumberValidator<Double>(0.0));
        estimateTimeDoubleField.addValidator(new MaxNumberValidator<Double>(365.0));
        verticalLayoutContainer.add(new FieldLabel(estimateTimeDoubleField, "Estimated time "), new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        estimateTimeDoubleField.setEnabled(false);

        // prepare acceptance tests grid
        acceptanceTestsContainer = new FlowPanel();

        RowNumberer<AcceptanceTestDTO> acceptanceTestRowNumberer = new RowNumberer<AcceptanceTestDTO>();
        ColumnConfig<AcceptanceTestDTO, String> acceptanceTestsColumnConfig = new ColumnConfig<AcceptanceTestDTO, String>(getAcceptanceTestContentValue(), 300, "Content");
        List<ColumnConfig<AcceptanceTestDTO, ?>> acceptanceTestsColumnConfigList = new ArrayList<ColumnConfig<AcceptanceTestDTO, ?>>();
        acceptanceTestsColumnConfigList.add(acceptanceTestRowNumberer);
        acceptanceTestsColumnConfigList.add(acceptanceTestsColumnConfig);

        ColumnModel<AcceptanceTestDTO> acceptanceTestColumnModel = new ColumnModel<AcceptanceTestDTO>(acceptanceTestsColumnConfigList);
        acceptanceTestStore = new ListStore<AcceptanceTestDTO>(getAcceptanceTestModelKeyProvider());
        editAcceptanceTestStore = new ListStoreEditor<AcceptanceTestDTO>(acceptanceTestStore);

        acceptanceTestGrid = new Grid<AcceptanceTestDTO>(acceptanceTestStore, acceptanceTestColumnModel);
        acceptanceTestGrid.getView().setAutoExpandColumn(acceptanceTestsColumnConfig);
        acceptanceTestGrid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
        acceptanceTestGrid.setBorders(true);
        acceptanceTestGrid.getView().setForceFit(true);

        GridInlineEditing<AcceptanceTestDTO> acceptanceTestGridInlineEditor = new GridInlineEditing<AcceptanceTestDTO>(acceptanceTestGrid);
        acceptanceTestGridInlineEditor.addEditor(acceptanceTestsColumnConfig, new TextField());

        acceptanceTestGrid.setWidth(382);
        acceptanceTestGrid.setHeight(200);

        FieldLabel accTestContainer = new FieldLabel();
        accTestContainer.setText("Acceptance tests");
        accTestContainer.setLabelAlign(FormPanel.LabelAlign.TOP);
        accTestContainer.setWidget(acceptanceTestGrid);
        acceptanceTestsContainer.add(accTestContainer);

        // add acceptance test buttons
        acceptanceTestDeleteButton = new TextButton("Delete selected row", new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                AsyncCallback<Pair<Boolean, String>> deleteAcceptanceTest = new AsyncCallback<Pair<Boolean, String>>() {
                    @Override
                    public void onSuccess(Pair<Boolean, String> result) {
                        if (result == null) {
                            AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing acceptance test destruction!");
                            amb2.show();
                        }
                        else if (!result.getFirst()) {
                            AlertMessageBox amb2 = new AlertMessageBox("Error destroying acceptance test!", result.getSecond());
                            amb2.show();
                        }
                        else {
                            MessageBox amb3 = new MessageBox("Message destroy acceptance test", result.getSecond());
                            amb3.show();
                        }
                    }
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }
                };
                service.deleteAcceptanceTest(acceptanceTestGrid.getSelectionModel().getSelectedItem(), deleteAcceptanceTest);
                acceptanceTestStore.remove(acceptanceTestGrid.getSelectionModel().getSelectedItem());
            }
        });

        acceptanceTestCreateButton = new TextButton("Create new test", new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                AcceptanceTestDTO acceptanceTestNewRow = new AcceptanceTestDTO();
                acceptanceTestNewRow.setAcceptanceTestId(acceptanceTestCount);
                acceptanceTestNewRow.setContent("New acceptance test.");
                acceptanceTestStore.add(acceptanceTestNewRow);
                acceptanceTestGrid.getSelectionModel().select(acceptanceTestCount, true);
                acceptanceTestCount++;
            }
        });

        ButtonBar acceptanceTestButtons = new ButtonBar();
        acceptanceTestButtons.add(acceptanceTestDeleteButton);
        acceptanceTestButtons.add(acceptanceTestCreateButton);
        acceptanceTestsContainer.add(acceptanceTestButtons);
        verticalLayoutContainer.add(acceptanceTestsContainer);

        if (selectedUserStoryDTO.getName() != null) {

            userStoryName.setValue(selectedUserStoryDTO.getName());
            userStoryContent.setValue(selectedUserStoryDTO.getContent());
            userStoryBusinessValue.setValue(selectedUserStoryDTO.getBusinessValue());
            if (selectedUserStoryDTO.getSprint() == null) {
                estimateTimeDoubleField.setEnabled(true);
                if (selectedUserStoryDTO.getEstimateTime() != null) {
                    estimateTimeDoubleField.setValue(selectedUserStoryDTO.getEstimateTime());
                }
            }
            int priorityID = selectedUserStoryDTO.getPriorityPriorityId().getPriorityId();
            switch (priorityID) {
                case 1:
                    mustHave.setValue(true);
                    shouldHave.setValue(false);
                    couldHave.setValue(false);
                    wontHave.setValue(false);
                    break;
                case 2:
                    mustHave.setValue(false);
                    shouldHave.setValue(true);
                    couldHave.setValue(false);
                    wontHave.setValue(false);
                    break;
                case 3:
                    mustHave.setValue(false);
                    shouldHave.setValue(false);
                    couldHave.setValue(true);
                    wontHave.setValue(false);
                    break;
                case 4:
                    mustHave.setValue(false);
                    shouldHave.setValue(false);
                    couldHave.setValue(false);
                    wontHave.setValue(true);
            }

            acceptanceTestStore.clear();
            acceptanceTestStore.addAll(selectedUserStoryDTO.getAcceptanceTestList());
            acceptanceTestCount = 1 + acceptanceTestStore.size();
        } else System.out.println("Jaka");


        // initialize driver
        driver.initialize(this);

        // add submit button
        submitButton = new TextButton("Update");
        submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {

                UserStoryDTO userStoryDTO = new UserStoryDTO();
                userStoryDTO.setStoryId(selectedUserStoryDTO.getStoryId());

                if (userStoryName.getValue() == null){
                    errorMessage("Empty User Story Name", "Please enter user story name!");
                    return;
                }
                userStoryDTO.setName(userStoryName.getValue());

                if (userStoryContent.getValue() == null){
                    errorMessage("Empty Content", "Please enter user story content!");
                    return;
                }
                userStoryDTO.setContent(userStoryContent.getValue());

                if (userStoryBusinessValue.getValue() == null){
                    errorMessage("Empty Business Value", "Please enter business value!");
                    return;
                }
                userStoryDTO.setBusinessValue(userStoryBusinessValue.getValue());

                PriorityDTO priorityDTO = new PriorityDTO();
                if (mustHave.getValue()){
                    priorityDTO.setPriorityId(1);
                    priorityDTO.setName("Must have");
                    userStoryDTO.setPriorityPriorityId(priorityDTO);
                } else if (shouldHave.getValue()){
                    priorityDTO.setPriorityId(2);
                    priorityDTO.setName("Should have");
                    userStoryDTO.setPriorityPriorityId(priorityDTO);
                } else if (couldHave.getValue()){
                    priorityDTO.setPriorityId(3);
                    priorityDTO.setName("Could have");
                    userStoryDTO.setPriorityPriorityId(priorityDTO);
                } else {
                    priorityDTO.setPriorityId(4);
                    priorityDTO.setName("Won't have this time");
                    userStoryDTO.setPriorityPriorityId(priorityDTO);
                }

                userStoryDTO.setStatus(selectedUserStoryDTO.getStatus());
                userStoryDTO.setProjectProjectId(SessionInfo.projectDTO);
                userStoryDTO.setSprint(selectedUserStoryDTO.getSprint());

                if (estimateTimeDoubleField.getValue() != null) {
                    double estimatedTime = estimateTimeDoubleField.getValue();
                    estimatedTime = Math.round(estimatedTime * 10.0) / 10.0;
                    userStoryDTO.setEstimateTime(estimatedTime);
                } else {
                    userStoryDTO.setEstimateTime(null);
                }
                driver.edit(userStoryDTO);

                userStoryDTO = driver.flush();
                acceptanceTestDTOList = getAcceptanceTestDTOList();

                if (driver.hasErrors()) {
                    System.out.println("Driver errors: " + driver.getErrors().toString());
                    new MessageBox("Driver failed completely with numerous errors.").show();
                }

                userStoryDTO.setAcceptanceTestList(acceptanceTestDTOList); // za vsak slucaj ce uno spodi ne dela
                performUpdateAcceptanceTestAndUserStory(acceptanceTestDTOList, userStoryDTO);
            }
        });
        panel.addButton(submitButton);
        setEnabledUserStoryGUIComponents(true);
        verticalPanel.add(panel);
    }

    // 1. Asinhron klic, ki ti shrani VSE acceptance teste
    // 2. V metodi onSuccess klices sele ostalo za shranjevanje
    // 3. Service, ki ti shrani seznam acceptanceTestov, ti vrne seznam AccTestId-jev
    // 4. te idje uporabis da nafilas user story in ga shranis
    // 5. ko service za user story shrani user story ti vrne id user storyja, da ga lahko das k projektu
    private void performUpdateAcceptanceTestAndUserStory(List<AcceptanceTestDTO> acceptanceTestDTOList, final UserStoryDTO userStoryDTO) {

        final List<AcceptanceTestDTO> acceptanceTestsForSaving = new ArrayList<AcceptanceTestDTO>();
        final List<AcceptanceTestDTO> acceptanceTestsForUpdating = new ArrayList<AcceptanceTestDTO>();

        for (AcceptanceTestDTO acceptanceTestDTO : acceptanceTestDTOList) {
            // ce ima story id null potem je ze v bazi in ga podaj metodi za updatanje, drugace ga podaj metodi za shranjevanje
            if (acceptanceTestDTO.getUserStoryStoryId() != null) {
                acceptanceTestsForUpdating.add(acceptanceTestDTO);
            } else {
                acceptanceTestsForSaving.add(acceptanceTestDTO);
            }
        }

        // workflow: v save acceptance tests notr vgnezdi updatanje, s tem da updatanju podas le shranjene teste, shranjevanju pa le nove

        AsyncCallback<Pair<Boolean, List<Integer>>> saveAcceptanceTestList = new AsyncCallback<Pair<Boolean, List<Integer>>>() {
            @Override
            public void onSuccess(Pair<Boolean, List<Integer>> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing acceptance test saving!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error saving acceptance test!", result.getSecond().toString());
                    amb2.show();
                } else if (result.getSecond() == null) {
                    errorMessage("Errorrrrrrrr", "Error while creating acceptance test in database!");
                } else {
                    if ( acceptanceTestsForSaving.size() == result.getSecond().size() ) {
                        ListIterator litr = result.getSecond().listIterator();
                        for (AcceptanceTestDTO acceptanceTestDTO : acceptanceTestsForSaving){
                            if (litr.hasNext()) {
                                acceptanceTestDTO.setAcceptanceTestId((Integer)litr.next());
                            } else {
                                errorMessage("Error saving acceptance test!", "There was an error while performing acceptance test saving!");
                            }
                        }
                    } else {
                        errorMessage("Error saving acceptance test!", "There was an error while performing acceptance test saving!");
                    }
                    // posodobi acceptance teste v bazi
                    AsyncCallback<Pair<Boolean, String>> updateAcceptanceTestList = new AsyncCallback<Pair<Boolean, String>>() {
                        @Override
                        public void onSuccess(Pair<Boolean, String> result) {

                            if (result == null) {
                                AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing acceptance test updating!");
                                amb2.show();
                            }
                            else if (!result.getFirst()) {
                                AlertMessageBox amb2 = new AlertMessageBox("Error updating acceptance test!", result.getSecond());
                                amb2.show();
                            } else {
                                // posodobi se user storyje
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
                                            SessionInfo.projectDTO = null;
                                            west.clear();
                                            east.clear();
                                            center.clear();
                                            MessageBox amb3 = new MessageBox("Message update User Story", result.getSecond());
                                            amb3.show();
                                            UserHomeForm userHomeForm = new UserHomeForm(service, center, west, east, north, south);
                                            center.add(userHomeForm.asWidget());
                                            if (SessionInfo.userDTO.isAdmin()) {
                                                AdminNavPanel adminNavPanel = new AdminNavPanel(center, west, east, north, south, service);
                                                east.add(adminNavPanel.asWidget());
                                            } else {
                                                UserNavPanel userNavPanel = new UserNavPanel(service, center, west, east, north, south);
                                                east.add(userNavPanel.asWidget());
                                            }
                                            ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east, north, south);
                                            west.add(psf.asWidget());
                                            used.hide();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Throwable caught) {
                                        Window.alert(caught.getMessage());
                                    }
                                };
                                service.updateUserStory(userStoryDTO, updateUserStory);
                            }
                        }
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
                    };
                    service.updateAcceptanceTestList(acceptanceTestsForUpdating, updateAcceptanceTestList);
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.saveAcceptanceTestList(acceptanceTestsForSaving, saveAcceptanceTestList);
    }

    private void setEnabledUserStoryGUIComponents(boolean enabled) {

        userStoryName.setEnabled(enabled);
        userStoryContent.setEnabled(enabled);
        userStoryBusinessValue.setEnabled(enabled);
        mustHave.setEnabled(enabled);
        shouldHave.setEnabled(enabled);
        couldHave.setEnabled(enabled);
        wontHave.setEnabled(enabled);
        acceptanceTestGrid.setEnabled(enabled);
        submitButton.setEnabled(enabled);
        acceptanceTestCreateButton.setEnabled(enabled);
        acceptanceTestDeleteButton.setEnabled(enabled);
    }

    private ValueProvider<AcceptanceTestDTO, String> getAcceptanceTestContentValue() {
        ValueProvider<AcceptanceTestDTO, String> acceptanceTestContentProvider = new ValueProvider<AcceptanceTestDTO, String>() {
            @Override
            public String getValue(AcceptanceTestDTO object) {
                return object.getContent();
            }
            @Override
            public void setValue(AcceptanceTestDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return acceptanceTestContentProvider;
    }

    // return the model key provider for the list store
    private ModelKeyProvider<AcceptanceTestDTO> getAcceptanceTestModelKeyProvider() {
        ModelKeyProvider<AcceptanceTestDTO> mkp = new ModelKeyProvider<AcceptanceTestDTO>() {
            @Override
            public String getKey(AcceptanceTestDTO item) {
                return item.getAcceptanceTestId().toString();
            }
        };
        return mkp;
    }

    private void errorMessage(String s, String s1) {
        AlertMessageBox amb = new AlertMessageBox(s, s1);
        amb.show();
    }

    public List<AcceptanceTestDTO> getAcceptanceTestDTOList() {
        List<AcceptanceTestDTO> returnTests = new ArrayList<AcceptanceTestDTO>();
        for (int i = 0; i < acceptanceTestStore.getAll().size(); i++) {
            acceptanceTestStore.get(i).setContent(acceptanceTestGrid.getView().getCell(i, 1).getInnerText());
        }
        for (AcceptanceTestDTO accTestDTO : acceptanceTestStore.getAll()) {
            AcceptanceTestDTO temp = new AcceptanceTestDTO();
            temp.setAcceptanceTestId(accTestDTO.getAcceptanceTestId());
            temp.setContent(accTestDTO.getContent());
            temp.setUserStoryStoryId(accTestDTO.getUserStoryStoryId());
            returnTests.add(temp);
        }
        return returnTests;
    }
}
