package si.fri.tpo.gwt.client.form.registration;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.*;
import si.fri.tpo.gwt.client.form.addedit.AcceptanceTestDataEditForm;
import si.fri.tpo.gwt.client.form.addedit.AcceptanceTestDataEditor;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by nanorax on 26/04/14.
 */
public class UserStoryRegistrationForm implements IsWidget {

    private DScrumServiceAsync service;
    private ContentPanel center, west, east;
    private VerticalPanel vp;
    private HorizontalPanel hp;
    private List<AcceptanceTestDTO> acceptanceTestDTOList;
    private AcceptanceTestDataEditForm atdeaf;

    private TextField userStoryName;
    private TextArea content;
    private IntegerField businessValue;

    private Radio couldHave;
    private Radio shouldHave;
    private Radio mustHave;
    private Radio wontHave;
    private ToggleGroup toggle;

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
        toggle.addValueChangeHandler(new ValueChangeHandler<HasValue<Boolean>>() {
            @Override
            public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event) {
                ToggleGroup group = (ToggleGroup) event.getSource();
                Radio mustHave = (Radio) group.getValue();
            }
        });

        atdeaf = new AcceptanceTestDataEditForm(service, center, west, east);
        p.add(atdeaf.asWidget());

        submitButton = new TextButton("Create");
        submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                final UserStoryDTO userStoryDTO = new UserStoryDTO();

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

                if (toggle.getValue() == null){
                    errorMessage("Priority is not selected", "Please select user story priority!");
                    return;
                }
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

                // 1. Asinhron klic, ki ti shrani VSE acceptance teste
                // 2. V metodi onSuccess klices sele ostalo za shranjevanje
                // 3. Service, ki ti shrani seznam acceptanceTestov, ti vrne seznam AccTestId-jev
                // 4. te idje uporabis da nafilas user story in ga shranis
                // 5. ko service za user story shrani user story ti vrne id user storyja, da ga lahko das k projektu

                setAcceptanceTestDTOList(atdeaf.getAcceptanceTestList());
                performSaveAcceptanceTestAndUserStory(acceptanceTestDTOList, userStoryDTO);
            }
        });
        panel.addButton(submitButton);
        vp.add(panel);

    }

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
        System.out.println("Calling saveAcceptanceTestList");
        service.saveAcceptanceTestList(acceptanceTestDTOList, saveAcceptanceTestList);
    }

    private void errorMessage(String s, String s1) {
        AlertMessageBox amb = new AlertMessageBox(s, s1);
        amb.show();
    }

    public List<AcceptanceTestDTO> getAcceptanceTestDTOList() {
        return acceptanceTestDTOList;
    }

    public void setAcceptanceTestDTOList(List<AcceptanceTestDTO> acceptanceTestDTOList) {
        this.acceptanceTestDTOList = acceptanceTestDTOList;
    }



}
