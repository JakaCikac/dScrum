package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.gwt.server.jpa.CommentPK;
import si.fri.tpo.gwt.server.jpa.Discussion;
import si.fri.tpo.gwt.server.jpa.User;

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

    public CommentPK getCommentPK() {
        return get("commentPK");
    }

    public void setCommentPK(CommentPK commentPK) {
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

    public User getUser() {
        return get("user");
    }

    public void setUser(User user) {
        set("user", user);
    }

    public Discussion getDiscussion() {
        return get("discussion");
    }

    public void setDiscussion(Discussion discussion) {
        set("discussion", discussion);
    }
}
