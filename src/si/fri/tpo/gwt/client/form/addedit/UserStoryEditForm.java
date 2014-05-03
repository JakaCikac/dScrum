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
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
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
    private Grid<UserStoryDTO> userStoryGrid;
    private TextButton submitButton;
    private Grid<AcceptanceTestDTO> acceptanceTestGrid;
    private TextButton acceptanceTestDeleteButton;
    private TextButton acceptanceTestCreateButton;

    // vars
    static private int acceptanceTestCount;
    private ListStore<AcceptanceTestDTO> acceptanceTestStore;
    private ListStore<UserStoryDTO> userStoryStore;
    private ColumnModel<UserStoryDTO> userStoryColumnModel;
    private ValueProvider<? super UserStoryDTO, String> priorityValue;
    private ListStoreEditor<AcceptanceTestDTO> editAcceptanceTestStore;
    private ListStoreEditor<UserStoryDTO> editUserStoryStore;
    private List<AcceptanceTestDTO> acceptanceTestDTOList;
    private ToggleGroup userStoryPriorityToggleGroup;

    public void setSelectedUserStoryDTO(UserStoryDTO selectedUserStoryDTO) {
        this.selectedUserStoryDTO = selectedUserStoryDTO;
    }

    private UserStoryDTO selectedUserStoryDTO;

    public UserStoryEditForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, UserStoryDTO usDTO) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.acceptanceTestCount = 0;
        System.out.println("usDTO edit name: " + usDTO.getName());
        this.selectedUserStoryDTO = usDTO;
        System.out.println("this.usDTO edit: " + this.selectedUserStoryDTO.getName());
        setSelectedUserStoryDTO(this.selectedUserStoryDTO);
    }

    @Override
    public Widget asWidget() {
        if (verticalPanel == null) {
            verticalPanel = new VerticalPanel();
            verticalPanel.setSpacing(10);
            if (selectedUserStoryDTO != null) {
                System.out.println("in as widget this.lalal: " + selectedUserStoryDTO.getName());
            } else System.out.println("Failed horribly.");
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
        } else System.out.println("Guufdfudf");


        // initialize driver
        driver.initialize(this);

        // add submit button
        submitButton = new TextButton("Update");
        submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {

                UserStoryDTO userStoryDTO = new UserStoryDTO();

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

                userStoryDTO.setStatus("Unfinished");
                userStoryDTO.setProjectProjectId(SessionInfo.projectDTO);
                driver.edit(userStoryDTO);

                userStoryDTO = driver.flush();
                acceptanceTestDTOList = getAcceptanceTestDTOList();

                if (driver.hasErrors()) {
                    System.out.println("Driver errors: " + driver.getErrors().toString());
                    new MessageBox("Driver failed completely with numerous errors.").show();
                }

                userStoryDTO.setAcceptanceTestList(acceptanceTestDTOList); // za vsak slucaj ce uno spodi ne dela
                performSaveAcceptanceTestAndUserStory(acceptanceTestDTOList, userStoryDTO);
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
    private void performSaveAcceptanceTestAndUserStory(List<AcceptanceTestDTO> acceptanceTestDTOList, final UserStoryDTO userStoryDTO) {
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
                    List<AcceptanceTestDTO> acceptanceTestDTOList = getAcceptanceTestDTOList();

                    if ( acceptanceTestDTOList.size() == result.getSecond().size()) {
                        ListIterator litr = result.getSecond().listIterator();
                        for (AcceptanceTestDTO acceptanceTestDTO : acceptanceTestDTOList){
                            if(litr.hasNext()) {
                                acceptanceTestDTO.setAcceptanceTestId((Integer)litr.next());
                            } else {
                                errorMessage("Error saving acceptance test!", "There was an error while performing acceptance test saving!");
                            }
                        }
                    } else {
                        errorMessage("Error saving acceptance test!", "There was an error while performing acceptance test saving!");
                    }
                    //TODO: Possible error (getter/setter)
                    userStoryDTO.setAcceptanceTestList(acceptanceTestDTOList);
                    AsyncCallback<Pair<Boolean, Integer>> saveUserStory = new AsyncCallback<Pair<Boolean, Integer>>() {
                        @Override
                        public void onSuccess(Pair<Boolean, Integer> result) {
                            if (result == null) {
                                AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing user story saving!");
                                amb2.show();
                            }
                            else if (!result.getFirst()) {
                                AlertMessageBox amb2 = new AlertMessageBox("Error saving user story!", result.getSecond().toString());
                                amb2.show();
                            }
                            else {
                                MessageBox amb3 = new MessageBox("Message save User Story", result.getSecond().toString());
                                amb3.show();
                                UserHomeForm userHomeForm = new UserHomeForm(service, center, west, east, north, south);
                                center.add(userHomeForm.asWidget());
                                west.clear();
                                SessionInfo.projectDTO = null;
                                ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east, north, south);
                                west.add(psf.asWidget());
                            }
                        }
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
                    };
                    service.saveUserStory(userStoryDTO, SessionInfo.projectDTO, saveUserStory);
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.saveAcceptanceTestList(acceptanceTestDTOList, saveAcceptanceTestList);
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

    private ValueProvider<UserStoryDTO, String> getUserStoryName() {
        ValueProvider<UserStoryDTO, String> userStoryNameValueProvider = new ValueProvider<UserStoryDTO, String>() {
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
        return userStoryNameValueProvider;
    }

    private ValueProvider<UserStoryDTO, Integer> getBusinessValue() {
        ValueProvider<UserStoryDTO, Integer> userStoryBusinessValueProvider = new ValueProvider<UserStoryDTO, Integer>() {
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
        return userStoryBusinessValueProvider;
    }

    private ValueProvider<UserStoryDTO, String> getPriorityValue() {
        ValueProvider<UserStoryDTO, String> userStoryPriorityValueProvider = new ValueProvider<UserStoryDTO, String>() {
            @Override
            public String getValue(UserStoryDTO object) {
                return object.getPriorityPriorityId().getName();
            }
            @Override
            public void setValue(UserStoryDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return userStoryPriorityValueProvider;
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

    private ModelKeyProvider<UserStoryDTO> getUserStoryModelKeyProvider() {
        ModelKeyProvider<UserStoryDTO> mkp = new ModelKeyProvider<UserStoryDTO>() {
            @Override
            public String getKey(UserStoryDTO item) {
                return item.getStoryId().toString();
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
            returnTests.add(temp);
        }
        return returnTests;
    }

    public List<UserStoryDTO> getUserStoryDTOList() {
        List<UserStoryDTO> returnTests = new ArrayList<UserStoryDTO>();
        for (int i = 0; i < userStoryStore.getAll().size(); i++) {
            userStoryStore.get(i).setContent(userStoryGrid.getView().getCell(i,1).getInnerText());
        }
        for (UserStoryDTO userStoryDTO : userStoryStore.getAll()) {

            UserStoryDTO temp = new UserStoryDTO();

            temp.setStoryId(userStoryDTO.getStoryId());
            temp.setBusinessValue(userStoryDTO.getBusinessValue());
            temp.setName(userStoryDTO.getName());
            temp.setPriorityPriorityId(userStoryDTO.getPriorityPriorityId());
            temp.setAcceptanceTestList(userStoryDTO.getAcceptanceTestList());
            temp.setContent(userStoryDTO.getContent());
            temp.setStatus(userStoryDTO.getStatus());
            temp.setEstimateTime(userStoryDTO.getEstimateTime());
            temp.setProjectProjectId(userStoryDTO.getProjectProjectId());
            temp.setSprint(userStoryDTO.getSprint());
            temp.setTaskList(userStoryDTO.getTaskList());

            returnTests.add(temp);
        }
        return returnTests;
    }
}
