package si.fri.tpo.gwt.client.form.registration;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.TaskDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.List;

/**
 * Created by nanorax on 26/04/14.
 */
public class TaskRegistrationForm implements IsWidget {

    interface ComboBoxTemplates extends XTemplates {

        @XTemplate("<img width=\"16\" height=\"11\" src=\"{imageUri}\"> {name}")
        SafeHtml country(SafeUri imageUri, String name);

        @XTemplates.XTemplate("<div qtip=\"{slogan}\" qtitle=\"State Slogan\">{name}</div>")
        SafeHtml state(String slogan, String name);
    }

    private DScrumServiceAsync service;
    private ContentPanel center, west, east;
    private VerticalPanel vp;

    private TextArea description;
    private Radio waiting;

    private TextButton submitButton;
    private ListStore<UserDTO> teamMembers;
    private TaskDTO taskDTO;
    private UserDTO assignedDeveloper;
    private UserStoryDTO userStoryDTO;
    private IntegerField estimateTime;

    @Override
    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);
            createTaskForm();
        }
        return vp;
    }

    public TaskRegistrationForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, UserStoryDTO userStoryDTO) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.userStoryDTO = userStoryDTO;
    }

    private void createTaskForm() {
        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("Task Creation Form");
        panel.setWidth(320);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        panel.add(p);

        description = new TextArea();
        description.setAllowBlank(false);
        description.addValidator(new MinLengthValidator(10));
        p.add(new FieldLabel(description, "Description *"), new VerticalLayoutContainer.VerticalLayoutData(1, 100));



        teamMembers = new ListStore<UserDTO>(getModelKeyProvider());
        // TODO: fill combo box with users working on project for story
        setMembers(SessionInfo.projectDTO.getTeamTeamId().getUserList());

        ComboBox<UserDTO> combo = new ComboBox<UserDTO>(teamMembers,getUsernameLabelValue() );
        addHandlersForEventObservation(combo, getUsernameLabelValue());

        combo.setEmptyText("Assign to developer..");
        combo.setWidth(150);
        combo.setTypeAhead(true);
        combo.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        p.add(combo);

        combo = new ComboBox<UserDTO>(teamMembers, getUsernameLabelValue(), new AbstractSafeHtmlRenderer<UserDTO>() {
            public SafeHtml render(UserDTO item) {
                final ComboBoxTemplates comboBoxTemplates = GWT.create(ComboBoxTemplates.class);
                return comboBoxTemplates.state(item.getFirstName(), item.getLastName());
            }
        });
        addHandlersForEventObservation(combo, getUsernameLabelValue());

        estimateTime = new IntegerField();
        p.add(new FieldLabel(estimateTime, " Est. time (h) *"), new VerticalLayoutContainer.VerticalLayoutData(1, 100));

        submitButton = new TextButton("Create");
        submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                taskDTO = new TaskDTO();

                if ( !description.getText().equals("") ) {
                    taskDTO.setDescription(description.getText());
                } else {
                    Info.display("Description (name) not entered.", "Please enter the description.");
                    return;
                }

                if ( assignedDeveloper != null ) {
                    taskDTO.setUserUserId(assignedDeveloper);
                } else {
                    Info.display("Developer not selected.", "Please select the developer.");
                    return;
                }

                if ( userStoryDTO != null) {
                    taskDTO.setUserStory(userStoryDTO);
                } else {
                    new AlertMessageBox("Null userStoryDTO", "An error occurred with the application, please restart!");
                    return;
                }

                if (estimateTime.getValue() != null) {
                    taskDTO.setEstimatedTime(estimateTime.getValue());
                    taskDTO.setTimeRemaining(estimateTime.getValue());
                } else {
                    Info.display("Estimated time not entered.", "Please enter the estimated time (Integer value).");
                    return;
                }

                taskDTO.setStatus("Assigned");

                performTaskSave();

            }
        });
        panel.addButton(submitButton);
        vp.add(panel);
    }

    private void performTaskSave() {
        AsyncCallback<Pair<Boolean, String>> saveTask = new AsyncCallback<Pair<Boolean, String>>() {
            @Override
            public void onSuccess(Pair<Boolean, String> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing project saving!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error saving project!", result.getSecond());
                    amb2.show();
                }
                else {
                    MessageBox amb3 = new MessageBox("Message save Project", result.getSecond());
                    amb3.show();
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.saveTask(taskDTO, userStoryDTO, saveTask);
    }

    public void setMembers(List<UserDTO> userList) {
        teamMembers.replaceAll(userList);
    }

    /**
     * Helper to add handlers to observe events that occur on each combobox
     */
    private <T> void addHandlersForEventObservation(ComboBox<T> combo, final LabelProvider<T> labelProvider) {
        combo.addValueChangeHandler(new ValueChangeHandler<T>() {
            @Override
            public void onValueChange(ValueChangeEvent<T> event) {
                Info.display("Value Changed", "New value: "
                        + (event.getValue() == null ? "nothing" : labelProvider.getLabel(event.getValue()) + "!"));
            }
        });
        combo.addSelectionHandler(new SelectionHandler<T>() {
            @Override
            public void onSelection(SelectionEvent<T> event) {
                Info.display("Developer selected", "You selected "
                        + (event.getSelectedItem() == null ? "nothing" : labelProvider.getLabel(event.getSelectedItem()) + "!"));
                assignedDeveloper = (UserDTO)event.getSelectedItem();
            }
        });
    }

    private ModelKeyProvider<UserDTO> getModelKeyProvider() {
        ModelKeyProvider<UserDTO> mkp = new ModelKeyProvider<UserDTO>() {
            @Override
            public String getKey(UserDTO item) {
                return item.getUsername();
            }
        };
        return mkp;
    }

    private LabelProvider<UserDTO> getUsernameLabelValue() {
        LabelProvider<UserDTO> lp = new LabelProvider<UserDTO>() {
            @Override
            public String getLabel(UserDTO item) {
                return item.getUsername();
            }
        };
        return lp;
    }
}