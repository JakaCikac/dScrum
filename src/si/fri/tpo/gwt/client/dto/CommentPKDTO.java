package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;

/**
 * Created by t13db on 5.4.2014.
 */
public class CommentPKDTO extends BaseModelData {

    //private int commentId;
    //private int discussionDiscussionId;
    //private int userUserId;

    public CommentPKDTO() {
    }

    public int getCommentId() {
        return get("commentId");
    }

    public void setCommentId(int commentId) {
        set("commentId", commentId);
    }

    public int getDiscussionDiscussionId() {
        return get("discussionDiscussionId");
    }

    public void setDiscussionDiscussionId(int discussionDiscussionId) {
        set("discussionDiscussionId", discussionDiscussionId);
    }

    public int getUserUserId() {
        return get("userUserId");
    }

    public void setUserUserId(int userUserId) {
        set("userUserId", userUserId);
    }
}