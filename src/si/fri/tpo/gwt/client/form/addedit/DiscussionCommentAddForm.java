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
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextArea;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.CommentDTO;
import si.fri.tpo.gwt.client.dto.DiscussionDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.Date;

/**
 * Created by t13db on 22.5.2014.
 */
public class DiscussionCommentAddForm implements IsWidget {

    private DScrumServiceAsync service;
    private ContentPanel center, west, east;
    private VerticalPanel vp;

    private TextArea contents;
    private TextButton submitButton;
    private CommentDTO commentDTO;
    private DiscussionDTO discussionDTO;

    public DiscussionCommentAddForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, DiscussionDTO discussionDTO) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.discussionDTO = discussionDTO;
    }

    @Override
    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);
            createCommentForm();
        }
        return vp;
    }

    private void createCommentForm() {
        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("Comment Form");
        panel.setWidth(320);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        panel.add(p);

        contents = new TextArea();

        p.add(new FieldLabel(contents, "Comment"), new VerticalLayoutContainer.VerticalLayoutData(-1, 180));

        submitButton = new TextButton("Create");
        contents.setValue(commentDTO.getContent());

        submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                commentDTO.setContent(contents.getValue());
                commentDTO.setCreatetime(new Date());
                commentDTO.setUser(SessionInfo.userDTO);
                commentDTO.setDiscussion(discussionDTO);
                performSaveDiscussionComment();

            }
        });
        panel.addButton(submitButton);
        vp.add(panel);
    }

    private void performSaveDiscussionComment() {
        AsyncCallback<Pair<Boolean, Integer>> saveDiscussionComment = new AsyncCallback<Pair<Boolean, Integer>>() {
            @Override
            public void onSuccess(Pair<Boolean, Integer> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing comment saving!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error saving comment!", result.getSecond() + "");
                    amb2.show();
                }
                else {
                    //TODO: add comment id to discusion
                    AsyncCallback<Pair<Boolean, String>> updateDiscussion = new AsyncCallback<Pair<Boolean, String>>() {
                        @Override
                        public void onSuccess(Pair<Boolean, String> result) {
                            if (result == null) {
                                AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing discussion updating!");
                                amb2.show();
                            }
                            else if (!result.getFirst()) {
                                AlertMessageBox amb2 = new AlertMessageBox("Error updating discussion!", result.getSecond());
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
                    service.updateDiscussion(discussionDTO, updateDiscussion);
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        //service.saveDiscussionComment(commentDTO, saveDiscussionComment);
    }
}
