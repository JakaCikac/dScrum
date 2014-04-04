package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.jpa.DiscussionPK;
import si.fri.tpo.jpa.Project;
import si.fri.tpo.jpa.User;

import java.util.Date;

/**
 * Created by t13db on 4.4.2014.
 */
public class DiscussionDTO extends BaseModelData {

    //private DiscussionPK id;
    //private String content;
    //private Date createtime;
    //private User user;
    //private Project project;

    public DiscussionDTO() {
    }

    public DiscussionPK getId() {
        return get("id");
    }

    public void setId(DiscussionPK id) {
        set("id", id);
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

    public Project getProject() {
        return get("project");
    }

    public void setProject(Project project) {
        set("project", project);
    }
}
