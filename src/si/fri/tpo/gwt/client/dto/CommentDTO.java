package si.fri.tpo.gwt.client.dto;

import com.sencha.gxt.legacy.client.data.BaseModelData;

import java.util.Date;

/**
 * Created by t13db on 5.4.2014.
 */
public class CommentDTO extends BaseModelData {

    //protected CommentPK commentPK;
    //private String content;
    //private Date createtime;
    //private User user;
    //private Discussion discussion;

    public CommentDTO() {
    }

    public CommentPKDTO getCommentPK() {
        return get("commentPK");
    }

    public void setCommentPK(CommentPKDTO commentPK) {
        set("commentPK", commentPK);
    }

    public String getContent() {
        return get("content");
    }

    public void setContent(String content) {
        set("content", content);
    }

    public Date getCreatetime() {
        return get("createtime");
    }

    public void setCreatetime(Date createtime) {
        set("createtime", createtime);
    }

    public UserDTO getUser() {
        return get("user");
    }

    public void setUser(UserDTO user) {
        set("user", user);
    }

    public DiscussionDTO getDiscussion() {
        return get("discussion");
    }

    public void setDiscussion(DiscussionDTO discussion) {
        set("discussion", discussion);
    }
}
