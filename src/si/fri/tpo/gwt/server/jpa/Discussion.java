/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.gwt.server.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "discussion")
@NamedQueries({
    @NamedQuery(name = "Discussion.findAll", query = "SELECT d FROM Discussion d"),
    @NamedQuery(name = "Discussion.findByDiscussionId", query = "SELECT d FROM Discussion d WHERE d.discussionPK.discussionId = :discussionId"),
    @NamedQuery(name = "Discussion.findByContent", query = "SELECT d FROM Discussion d WHERE d.content = :content"),
    @NamedQuery(name = "Discussion.findByCreatetime", query = "SELECT d FROM Discussion d WHERE d.createtime = :createtime"),
    @NamedQuery(name = "Discussion.findByUserUserId", query = "SELECT d FROM Discussion d WHERE d.discussionPK.userUserId = :userUserId"),
    @NamedQuery(name = "Discussion.findByProjectProjectId", query = "SELECT d FROM Discussion d WHERE d.discussionPK.projectProjectId = :projectProjectId")})
public class Discussion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DiscussionPK discussionPK;
    @Column(name = "content")
    private String content;
    @Basic(optional = false)
    @Column(name = "createtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;
    @JoinColumn(name = "project_project_id", referencedColumnName = "project_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Project project;
    @JoinColumn(name = "user_user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discussion")
    private List<Comment> commentList;

    public Discussion() {
    }

    public Discussion(DiscussionPK discussionPK) {
        this.discussionPK = discussionPK;
    }

    public Discussion(DiscussionPK discussionPK, Date createtime) {
        this.discussionPK = discussionPK;
        this.createtime = createtime;
    }

    public Discussion(int discussionId, int userUserId, int projectProjectId) {
        this.discussionPK = new DiscussionPK(discussionId, userUserId, projectProjectId);
    }

    public DiscussionPK getDiscussionPK() {
        return discussionPK;
    }

    public void setDiscussionPK(DiscussionPK discussionPK) {
        this.discussionPK = discussionPK;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (discussionPK != null ? discussionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Discussion)) {
            return false;
        }
        Discussion other = (Discussion) object;
        if ((this.discussionPK == null && other.discussionPK != null) || (this.discussionPK != null && !this.discussionPK.equals(other.discussionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.gwt.server.jpa.Discussion[ discussionPK=" + discussionPK + " ]";
    }
    
}
