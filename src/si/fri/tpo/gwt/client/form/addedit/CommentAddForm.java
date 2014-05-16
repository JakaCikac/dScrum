package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by anze on 16. 05. 14.
 */
public class CommentAddForm implements IsWidget {
    private DScrumServiceAsync service;
    private ContentPanel center, west, east;
    private VerticalPanel vp;

    private TextArea description;
    private TextButton submitButton;
    private UserStoryDTO userStoryDTO;

    @Override
    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);
            createTaskForm();
        }
        return vp;
    }

    public CommentAddForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, UserStoryDTO userStoryDTO) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.userStoryDTO = userStoryDTO;
    }

    private void createTaskForm() {
        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("Comment Form");
        panel.setWidth(320);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        panel.add(p);

        description = new TextArea();

        p.add(new FieldLabel(description, "Notes"), new VerticalLayoutContainer.VerticalLayoutData(-1, 180));

        if(userStoryDTO.getComment() == null) {
            submitButton = new TextButton("Create");
        } else {
            submitButton = new TextButton("Update");
            description.setValue(userStoryDTO.getComment());
        }
        submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if(userStoryDTO.getComment() == null) {
                    userStoryDTO.setComment(description.getValue());
                    performSaveComment();
                } else {
                    userStoryDTO.setComment(description.getValue());
                    performUpdateComment();
                }
            }
        });
        panel.addButton(submitButton);
        vp.add(panel);
    }

    private void performSaveComment() {
        AsyncCallback<Pair<Boolean, String>> saveComment = new AsyncCallback<Pair<Boolean, String>>() {
            @Override
            public void onSuccess(Pair<Boolean, String> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing comment saving!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error saving comment!", result.getSecond());
                    amb2.show();
                }
                else {
                    MessageBox amb3 = new MessageBox("Message save comment", result.getSecond());
                    amb3.show();
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.saveComment(userStoryDTO, saveComment);
    }

    private void performUpdateComment() {
        AsyncCallback<Pair<Boolean, String>> updateComment = new AsyncCallback<Pair<Boolean, String>>() {
            @Override
            public void onSuccess(Pair<Boolean, String> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing comment updating!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error updating comment!", result.getSecond());
                    amb2.show();
                }
                else {
                    MessageBox amb3 = new MessageBox("Message update comment", result.getSecond());
                    amb3.show();
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.updateComment(userStoryDTO, updateComment);
    }
}
