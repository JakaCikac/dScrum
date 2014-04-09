package si.fri.tpo.gwt.client.dto;

import com.sencha.gxt.legacy.client.data.BaseModelData;

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

    public DiscussionPKDTO getDiscussionPK() {
        return get("discussionPK");
    }

    public void setDiscussionPK(DiscussionPKDTO discussionPK) {
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

    public ProjectDTO getProject() {
        return get("project");
    }

    public void setProject(ProjectDTO project) {
        set("project", project);
    }

    public UserDTO getUser() {
        return get("user");
    }

    public void setUser(UserDTO user) {
        set("user", user);
    }

    public List<CommentDTO> getCommentList() {
        return get("commentList");
    }

    public void setCommentList(List<CommentDTO> commentList) {
        set("commentList", commentList);
    }
}
