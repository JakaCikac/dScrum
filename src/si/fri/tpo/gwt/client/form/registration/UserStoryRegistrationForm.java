package si.fri.tpo.gwt.client.form.registration;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
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
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.*;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by nanorax on 26/04/14.
 */
public class UserStoryRegistrationForm implements IsWidget, Editor<UserStoryDTO> {

    private VerticalPanel vp;
    private HorizontalPanel hp;
    private List<AcceptanceTestDTO> acceptanceTestDTOList;

    interface Driver extends SimpleBeanEditorDriver<UserStoryDTO, UserStoryRegistrationForm> {}

    private Driver driver = GWT.create(Driver.class);
    private FramedPanel panel;
    private List<AcceptanceTestDTO> acceptanceTests;

    protected Grid<AcceptanceTestDTO> grid;

    private List<AcceptanceTestDTO> acceptanceTestList;
    private ContentPanel center, west, east;
    private DScrumServiceAsync service;
    private UserStoryDTO storyDTO;
    static private int acceptanceTestCount;
    private ListStore<AcceptanceTestDTO> store;
    private ListStoreEditor<AcceptanceTestDTO> editStore;
    private AcceptanceTestDTO tempDTO;
    private boolean firstTimeEdit = true;

    private TextField userStoryName;
    private TextArea content;
    private IntegerField businessValue;

    private Radio couldHave;
    private Radio shouldHave;
    private Radio mustHave;
    private Radio wontHave;
    private ToggleGroup toggle;

    private FlowPanel container;

    private ProjectDTO projectDTO;

    private TextButton submitButton;

    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);
            createUserStoryForm();
        }
        return vp;
    }

    public UserStoryRegistrationForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.acceptanceTestCount = 0;
    }

    private void createUserStoryForm() {
        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("User Story Creation Form");
        panel.setWidth(470);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        panel.add(p);

        userStoryName = new TextField();
        userStoryName.setAllowBlank(false);
        userStoryName.setEmptyText("GoodNight story");
        p.add(new FieldLabel(userStoryName, "User Story Name *"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        content = new TextArea();
        content.setAllowBlank(false);
        content.addValidator(new MinLengthValidator(10));
        p.add(new FieldLabel(content, "Content *"), new VerticalLayoutContainer.VerticalLayoutData(1, 100));

        businessValue = new IntegerField();
        businessValue.setAllowBlank(false);
        businessValue.setAllowNegative(false);
        p.add(new FieldLabel(businessValue, "Business Value *"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

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

        hp = new HorizontalPanel();
        hp.add(mustHave);
        hp.add(shouldHave);
        hp.add(couldHave);
        hp.add(wontHave);
        p.add(new FieldLabel(hp, "Priority *"));

        toggle = new ToggleGroup();
        toggle.add(mustHave);
        toggle.add(shouldHave);
        toggle.add(couldHave);
        toggle.add(wontHave);

        // create store and grid for acceptance tests
        container = new FlowPanel();
        RowNumberer<AcceptanceTestDTO> numberer = new RowNumberer<AcceptanceTestDTO>();
        ColumnConfig<AcceptanceTestDTO, String> accTestContent = new ColumnConfig<AcceptanceTestDTO, String>(getContentValue(), 300, "Content");
        List<ColumnConfig<AcceptanceTestDTO, ?>> l = new ArrayList<ColumnConfig<AcceptanceTestDTO, ?>>();
        l.add(numberer);
        l.add(accTestContent);

        ColumnModel<AcceptanceTestDTO> cm = new ColumnModel<AcceptanceTestDTO>(l);
        store = new ListStore<AcceptanceTestDTO>(getModelKeyProvider());
        editStore = new ListStoreEditor<AcceptanceTestDTO>(store);

        grid = new Grid<AcceptanceTestDTO>(store, cm);
        grid.getView().setAutoExpandColumn(accTestContent);
        grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
        grid.setBorders(true);
        grid.getView().setForceFit(true);

        GridInlineEditing<AcceptanceTestDTO> inlineEditor = new GridInlineEditing<AcceptanceTestDTO>(grid);
        inlineEditor.addEditor(accTestContent, new TextField());

        grid.setWidth(382);
        grid.setHeight(200);

        FieldLabel accTestContainer = new FieldLabel();
        accTestContainer.setText("Acceptance tests");
        accTestContainer.setLabelAlign(FormPanel.LabelAlign.TOP);
        accTestContainer.setWidget(grid);
        container.add(accTestContainer);

        TextButton deleteBtn = new TextButton("Delete selected row", new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                store.remove(grid.getSelectionModel().getSelectedItem());
            }
        });


        TextButton createBtn = new TextButton("Create new test", new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                AcceptanceTestDTO newRow = new AcceptanceTestDTO();
                newRow.setAcceptanceTestId(acceptanceTestCount);
                newRow.setContent("New acceptance test.");
                store.add(newRow);
                grid.getSelectionModel().select(acceptanceTestCount, true);
                acceptanceTestCount++;
            }
        });

        ButtonBar buttons = new ButtonBar();
        buttons.add(deleteBtn);
        buttons.add(createBtn);
        container.add(buttons);
        p.add(container);

        driver.initialize(this);

        submitButton = new TextButton("Create");
        submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {

                UserStoryDTO userStoryDTO = new UserStoryDTO();

                if (userStoryName.getValue() == null){
                    errorMessage("Empty User Story Name", "Please enter user story name!");
                    return;
                }
                userStoryDTO.setName(userStoryName.getValue());

                if (content.getValue() == null){
                    errorMessage("Empty Content", "Please enter user story content!");
                    return;
                }
                userStoryDTO.setContent(content.getValue());

                if (businessValue.getValue() == null){
                    errorMessage("Empty Business Value", "Please enter business value!");
                    return;
                }
                userStoryDTO.setBusinessValue(businessValue.getValue());

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
                if (userStoryDTO.getAcceptanceTestList() != null) {
                    System.out.println("Ni null! " + userStoryDTO.getAcceptanceTestList().size());
                } else System.out.println("List je se vedno null modelj");

                //userStoryDTO.setAcceptanceTestList(acceptanceTestDTOList);
                /*if (userStoryDTO.getAcceptanceTestList() != null) {
                   System.out.println("Acceptance list in userStoryDTO is not null and has  " + userStoryDTO.getAcceptanceTestList().size() + " # of objects:");
                } else System.out.println("AcctestDTO list is null in userStoryDTO."); */

                userStoryDTO = driver.flush();
                acceptanceTestDTOList = getAcceptanceTestDTOList();
                if (userStoryDTO.getAcceptanceTestList() != null) {
                    System.out.println("Ni null! " + userStoryDTO.getAcceptanceTestList().size());
                } else System.out.println("List je se vedno null modelj");

                if (driver.hasErrors()) {
                    System.out.println("Driver errors: " + driver.getErrors().toString());
                    new MessageBox("Driver failed completely with numerous errors.").show();
                }

                performSaveAcceptanceTestAndUserStory(acceptanceTestDTOList, userStoryDTO);
            }
        });
        panel.addButton(submitButton);
        vp.add(panel);

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
                                center.clear();
                                west.clear();
                                SessionInfo.projectDTO = null;
                                ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east);
                                west.add(psf.asWidget());
                            }
                        }
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
                    };
                    //TODO: Save UserStory to database
                    System.out.println("Calling saveProject");
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

    private void errorMessage(String s, String s1) {
        AlertMessageBox amb = new AlertMessageBox(s, s1);
        amb.show();
    }

    public List<AcceptanceTestDTO> getAcceptanceTestDTOList() {
           List<AcceptanceTestDTO> returnTests = new ArrayList<AcceptanceTestDTO>();

            for (AcceptanceTestDTO accTestDTO : store.getAll()) {
                AcceptanceTestDTO temp = new AcceptanceTestDTO();
                temp.setAcceptanceTestId(accTestDTO.getAcceptanceTestId());
                System.out.println("accTestDTO content form listStore: " + accTestDTO.getContent());
                temp.setContent(accTestDTO.getContent());
                returnTests.add(temp);
            }
            return returnTests;
    }

    public void setAcceptanceTestDTOList(List<AcceptanceTestDTO> acceptanceTestDTOList) {
        this.acceptanceTestDTOList = acceptanceTestDTOList;
    }

    // return the model key provider for the list store
    private ModelKeyProvider<AcceptanceTestDTO> getModelKeyProvider() {
        ModelKeyProvider<AcceptanceTestDTO> mkp = new ModelKeyProvider<AcceptanceTestDTO>() {
            @Override
            public String getKey(AcceptanceTestDTO item) {
                return item.getAcceptanceTestId().toString();
            }
        };
        return mkp;
    }

    private ValueProvider<AcceptanceTestDTO, String> getContentValue() {
        ValueProvider<AcceptanceTestDTO, String> vpc = new ValueProvider<AcceptanceTestDTO, String>() {
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
        return vpc;
    }

    public void setAcceptanceTestList(List<AcceptanceTestDTO> acceptanceTestList) {
        this.acceptanceTestList = acceptanceTestList;
    }

    public List<AcceptanceTestDTO> getAcceptanceTestList() {
        List<AcceptanceTestDTO> acceptanceTestDTOList = new ArrayList<AcceptanceTestDTO>();
        for (AcceptanceTestDTO acceptanceTestDTO : store.getAll()){
            AcceptanceTestDTO temp = new AcceptanceTestDTO();
            temp.setContent(acceptanceTestDTO.getContent());
            acceptanceTestDTOList.add(temp);
        }
        return acceptanceTestDTOList;
    }

    public void setTempDTO(AcceptanceTestDTO tempDTO) {
        this.tempDTO = tempDTO;
    }

}
