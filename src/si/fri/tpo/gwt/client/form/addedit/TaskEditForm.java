package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
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
import si.fri.tpo.gwt.client.dto.TaskDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.List;

/**
 * Created by anze on 18. 05. 14.
 */
public class TaskEditForm implements IsWidget, Editor<TaskDTO> {

    interface ComboBoxTemplates extends XTemplates {

        @XTemplate("<img width=\"16\" height=\"11\" src=\"{imageUri}\"> {name}")
        SafeHtml country(SafeUri imageUri, String name);

        @XTemplates.XTemplate("<div qtip=\"{slogan}\" qtitle=\"State Slogan\">{name}</div>")
        SafeHtml state(String slogan, String name);
    }

    private ContentPanel center, west, east, north, south;
    private DScrumServiceAsync service;

    private VerticalPanel vp;
    private TaskDTO taskDTO;

    private TextArea description;
    private TextButton submitButton;
    private ListStore<UserDTO> teamMembers;
    private UserDTO assignedDeveloper;
    private UserStoryDTO userStoryDTO;
    private IntegerField estimateTime;

    private ComboBox<UserDTO> combo;

    public TaskEditForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, TaskDTO taskDTO) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.taskDTO = taskDTO;
    }

    @Override
    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);
            createTasksForm();
        }
        return vp;
    }

    private void createTasksForm() {
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
        List<UserDTO> members = SessionInfo.projectDTO.getTeamTeamId().getUserList();
        setMembers(members);

        combo = new ComboBox<UserDTO>(teamMembers,getUsernameLabelValue() );

        combo.setEmptyText("Assign to developer..");
        combo.setWidth(150);
        combo.setTypeAhead(true);
        combo.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        p.add(new FieldLabel(combo, "Assign to"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        estimateTime = new IntegerField();
        p.add(new FieldLabel(estimateTime, " Est. time (h) *"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        if(taskDTO.getDescription() != null){
            description.setValue(taskDTO.getDescription());
        }
        estimateTime.setValue(taskDTO.getTimeRemaining());

        if(taskDTO.getPreassignedUserName() != null){
            for(UserDTO userDTO : members) {
                if(userDTO.getUsername().equals(taskDTO.getPreassignedUserName())){
                    combo.setValue(userDTO);
                }
            }
        }

        submitButton = new TextButton("Update");
        submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if ( !description.getValue().equals("") ) {
                    taskDTO.setDescription(description.getValue());
                } else {
                    Info.display("Description (name) not entered.", "Please enter the description.");
                    return;
                }

                if ( combo.getValue() != null ) {
                    taskDTO.setPreassignedUserName(combo.getValue().getUsername());
                } else {
                    taskDTO.setPreassignedUserName(null);
                }
                if (estimateTime.getValue() != null) {
                    //taskDTO.setEstimatedTime(estimateTime.getValue());
                    taskDTO.setTimeRemaining(estimateTime.getValue());
                } else {
                    Info.display("Estimated time not entered.", "Please enter the estimated time (Integer value).");
                    return;
                }
                if(taskDTO.getPreassignedUserName() == null) {
                    taskDTO.setStatus("Not assigned");
                } else taskDTO.setStatus("Need to confirm");

                performUpdateTask(taskDTO);
            }
        });
        panel.addButton(submitButton);
        vp.add(panel);

    }

    private void performUpdateTask(TaskDTO p) {
        AsyncCallback<Pair<Boolean, String>> updateTask = new AsyncCallback<Pair<Boolean, String>>() {
            @Override
            public void onSuccess(Pair<Boolean, String> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing task updating!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error updating task!", result.getSecond());
                    amb2.show();
                }
                else {
                    MessageBox amb3 = new MessageBox("Message update task", result.getSecond());
                    amb3.show();
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.updateTask(p, updateTask);
    }

    public void setMembers(List<UserDTO> userList) {
        teamMembers.replaceAll(userList);
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

