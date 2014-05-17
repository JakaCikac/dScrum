package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.ModuleTabPanel;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.cell.core.client.TextButtonCell;
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
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.AcceptanceTestDTO;
import si.fri.tpo.gwt.client.dto.TaskDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 17/05/14.
 */
public class AcceptEditTasksForm implements IsWidget, Editor<TaskDTO> {

    interface Driver extends SimpleBeanEditorDriver<TaskDTO, AcceptEditTasksForm> {}
    private Driver driver = GWT.create(Driver.class);

    private ContentPanel center, west, east, north, south;
    private DScrumServiceAsync service;
    private AcceptEditTasksDialog aetd;

    private VerticalPanel verticalPanel;
    private FlowPanel container;
    private ListStore<TaskDTO> store;
    private ListStoreEditor<TaskDTO> editStore;
    private Grid<TaskDTO> grid;
    private UserStoryDTO selectedUserStoryDTO;

    public void setSelectedUserStoryDTO(UserStoryDTO selectedUserStoryDTO) {
        this.selectedUserStoryDTO = selectedUserStoryDTO;
    }

    public AcceptEditTasksForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, UserStoryDTO usDTO, AcceptEditTasksDialog aetd) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
       // this.acceptanceTestCount = 0;
        this.selectedUserStoryDTO = usDTO;
        this.aetd = aetd;
        setSelectedUserStoryDTO(this.selectedUserStoryDTO);
    }

    @Override
    public Widget asWidget() {
        if (verticalPanel == null) {
            verticalPanel = new VerticalPanel();
            verticalPanel.setSpacing(10);
            createTasksForm();
        }
        return verticalPanel;
    }

    private void createTasksForm() {
        FramedPanel panel = new FramedPanel();
        panel.setHeaderVisible(false);
        panel.setWidth(660);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        panel.add(p);

        // create store and grid for acceptance tests
        container = new FlowPanel();
        RowNumberer<TaskDTO> numberer = new RowNumberer<TaskDTO>();
        ColumnConfig<TaskDTO, String> taskContent = new ColumnConfig<TaskDTO, String>(getContentValue(), 200, "Description");
        ColumnConfig<TaskDTO, String> taskStatus = new ColumnConfig<TaskDTO, String>(getStatusValue(), 60, "Status");
        ColumnConfig<TaskDTO, String> taskPreassigned = new ColumnConfig<TaskDTO, String>(getPreassignedUserNameValue(), 80, "Preassigned");
        ColumnConfig<TaskDTO, String> taskAssigned = new ColumnConfig<TaskDTO, String>(getAssignedUserNameValue(), 70, "Assigned");
        ColumnConfig<TaskDTO, String> acceptButtonColumn = new ColumnConfig<TaskDTO, String>(getAcceptValue(), 70, "Accept Task");
        ColumnConfig<TaskDTO, String> releaseButtonColumn = new ColumnConfig<TaskDTO, String>(getReleaseValue(), 70, "Release Task");

        final TextButtonCell acceptButton = new TextButtonCell();
        acceptButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                Cell.Context c = event.getContext();
                int row = c.getIndex();
                TaskDTO p = store.get(row);
                // shrani accept v bazo, refresh griduslus
                if((p.getPreassignedUserName() == null || p.getPreassignedUserName() == SessionInfo.userDTO.getUsername())
                        && p.getUserUserId() == null){
                    p.setUserUserId(SessionInfo.userDTO);
                    p.setStatus("Assigned");
                    performUpdateTask(p);
                    store.update(p);
                } else {
                    if ( p.getPreassignedUserName() == SessionInfo.userDTO.getUsername() ) {
                        AlertMessageBox amb2 = new AlertMessageBox("Task already assigned!", "This task is already preassigned to you.");
                        amb2.show();
                    }
                    else if (p.getUserUserId() != null && p.getUserUserId().getUsername() == SessionInfo.userDTO.getUsername()) {
                        AlertMessageBox amb2 = new AlertMessageBox("Task already assigned!", "This task is already assigned to you.");
                        amb2.show();
                    } else {
                        AlertMessageBox amb2 = new AlertMessageBox("Task already assigned!", "This task is already assigned or preassigned. Ask the user to release the task, if you wish to accept it.");
                        amb2.show();
                    }
                }
            }
        });
        acceptButtonColumn.setCell(acceptButton);

        TextButtonCell releaseButton = new TextButtonCell();
        releaseButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                Cell.Context c = event.getContext();
                int row = c.getIndex();
                TaskDTO p = store.get(row);
                // shrani v bazo, refresh girduslus
                if(p.getUserUserId() != null && p.getUserUserId().getUsername().equals(SessionInfo.userDTO.getUsername())){
                    p.setUserUserId(null);
                    p.setPreassignedUserName(null);
                    p.setStatus("Not assigned");
                    performUpdateTask(p);
                    store.update(p);
                } else {
                    if (p.getUserUserId() != null && !p.getUserUserId().getUsername().equals(SessionInfo.userDTO.getUsername())){
                        AlertMessageBox amb2 = new AlertMessageBox("Task assigned!", "This task is not assigned to you.");
                        amb2.show();
                    } else {
                        AlertMessageBox amb2 = new AlertMessageBox("Task not assigned!", "This task is not assigned to anyone.");
                        amb2.show();
                    }
                }
            }
        });
        releaseButtonColumn.setCell(releaseButton);

        List<ColumnConfig<TaskDTO, ?>> l = new ArrayList<ColumnConfig<TaskDTO, ?>>();
        l.add(numberer);
        l.add(taskContent);
        l.add(taskStatus);
        l.add(taskPreassigned);
        l.add(taskAssigned);
        l.add(acceptButtonColumn);
        l.add(releaseButtonColumn);

        ColumnModel<TaskDTO> cm = new ColumnModel<TaskDTO>(l);
        store = new ListStore<TaskDTO>(getModelKeyProvider());
        editStore = new ListStoreEditor<TaskDTO>(store);
        store.addAll(selectedUserStoryDTO.getTaskList());

        grid = new com.sencha.gxt.widget.core.client.grid.Grid<TaskDTO>(store, cm);
        grid.getView().setAutoExpandColumn(taskContent);
        grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
        grid.setBorders(true);
        grid.getView().setForceFit(true);

        GridInlineEditing<TaskDTO> inlineEditor = new GridInlineEditing<TaskDTO>(grid);
        inlineEditor.addEditor(taskContent, new TextField());

        grid.setWidth(650);
        grid.setHeight(300);

        FieldLabel taskContainer = new FieldLabel();
        taskContainer.setText("Tasks");
        taskContainer.setLabelAlign(FormPanel.LabelAlign.TOP);
        taskContainer.setWidget(grid);
        container.add(taskContainer);

        TextButton deleteBtn = new TextButton("Delete selected task", new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (grid.getSelectionModel().getSelectedItem().getUserUserId() != null) {
                    Info.display("No go deleto", "Can't delete task because it's assigned!");
                } else {
                    store.remove(grid.getSelectionModel().getSelectedItem());
                }
            }
        });

        ButtonBar buttons = new ButtonBar();
        buttons.add(deleteBtn);
        container.add(buttons);
        p.add(container);

        driver.initialize(this);

        verticalPanel.add(p);

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

    // return the model key provider for the list store
    private ModelKeyProvider<TaskDTO> getModelKeyProvider() {
        ModelKeyProvider<TaskDTO> mkp = new ModelKeyProvider<TaskDTO>() {
            @Override
            public String getKey(TaskDTO item) {
                return item.getTaskPK().getTaskId() + "";
            }
        };
        return mkp;
    }

    private ValueProvider<TaskDTO, String> getContentValue() {
        ValueProvider<TaskDTO, String> vpc = new ValueProvider<TaskDTO, String>() {
            @Override
            public String getValue(TaskDTO object) {
                return object.getDescription();
            }
            @Override
            public void setValue(TaskDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpc;
    }

    private ValueProvider<TaskDTO, String> getStatusValue() {
        ValueProvider<TaskDTO, String> vpc = new ValueProvider<TaskDTO, String>() {
            @Override
            public String getValue(TaskDTO object) {
                return object.getStatus();
            }
            @Override
            public void setValue(TaskDTO object, String value) {
            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpc;
    }

    private ValueProvider<TaskDTO, String> getPreassignedUserNameValue() {
        ValueProvider<TaskDTO, String> vpc = new ValueProvider<TaskDTO, String>() {
            @Override
            public String getValue(TaskDTO object) {
                if (object.getPreassignedUserName() != null) {
                    return object.getPreassignedUserName();
                } else return "/";
            }
            @Override
            public void setValue(TaskDTO object, String value) {
            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpc;
    }

    private ValueProvider<TaskDTO, String> getAssignedUserNameValue() {
        ValueProvider<TaskDTO, String> vpc = new ValueProvider<TaskDTO, String>() {
            @Override
            public String getValue(TaskDTO object) {
                if (object.getUserUserId() != null) {
                    return object.getUserUserId().getUsername();
                } else return "/";
            }
            @Override
            public void setValue(TaskDTO object, String value) {
            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpc;
    }

    private ValueProvider<TaskDTO, String> getAcceptValue() {
        ValueProvider<TaskDTO, String> vpc = new ValueProvider<TaskDTO, String>() {
            @Override
            public String getValue(TaskDTO object) {
                return "Accept Task";
            }
            @Override
            public void setValue(TaskDTO object, String value) {
            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpc;
    }

    private ValueProvider<TaskDTO, String> getReleaseValue() {
        ValueProvider<TaskDTO, String> vpc = new ValueProvider<TaskDTO, String>() {
            @Override
            public String getValue(TaskDTO object) {
                return "Release Task";
            }
            @Override
            public void setValue(TaskDTO object, String value) {
            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpc;
    }

}
