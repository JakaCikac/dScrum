package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.gwt.server.jpa.DiscussionPK;
import si.fri.tpo.gwt.server.jpa.Project;
import si.fri.tpo.gwt.server.jpa.User;

import java.util.Date;
import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class DiscussionDTO extends BaseModelData {

    //protected DiscussionPK discussionPK;
    //private String content;
    //private Date createtime;
    //private Project project;
    //private User user;
    //private List<Comment> commentList;

    public DiscussionDTO() {
    }

    public DiscussionPK getDiscussionPK() {
        return get("discussionPK");
    }

    public void setDiscussionPK(DiscussionPK discussionPK) {
        set("discussionPK", discussionPK);
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

    public Project getProject() {
        return get("project");
    }

    public void setProject(Project project) {
        set("project", project);
    }

    public User getUser() {
        return get("user");
    }

    public void setUser(User user) {
        set("user", user);
    }

    public List<CommentDTO> getCommentList() {
        return get("commentList");
    }

    public void setCommentList(List<CommentDTO> commentList) {
        set("commentList", commentList);
    }
}
