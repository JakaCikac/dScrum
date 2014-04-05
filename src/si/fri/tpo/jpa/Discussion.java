/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "DISCUSSION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Discussion.findAll", query = "SELECT d FROM Discussion d"),
    @NamedQuery(name = "Discussion.findByDiscussionId", query = "SELECT d FROM Discussion d WHERE d.discussionPK.discussionId = :discussionId"),
    @NamedQuery(name = "Discussion.findByContent", query = "SELECT d FROM Discussion d WHERE d.content = :content"),
    @NamedQuery(name = "Discussion.findByCreatetime", query = "SELECT d FROM Discussion d WHERE d.createtime = :createtime"),
    @NamedQuery(name = "Discussion.findByUSERuserid", query = "SELECT d FROM Discussion d WHERE d.discussionPK.uSERuserid = :uSERuserid"),
    @NamedQuery(name = "Discussion.findByPROJECTprojectid", query = "SELECT d FROM Discussion d WHERE d.discussionPK.pROJECTprojectid = :pROJECTprojectid")})
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
    @JoinColumn(name = "PROJECT_project_id", referencedColumnName = "project_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Project project;
    @JoinColumn(name = "USER_user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discussion")
    private Collection<Comment> commentCollection;

    public Discussion() {
    }

    public Discussion(DiscussionPK discussionPK) {
        this.discussionPK = discussionPK;
    }

    public Discussion(DiscussionPK discussionPK, Date createtime) {
        this.discussionPK = discussionPK;
        this.createtime = createtime;
    }

    public Discussion(int discussionId, int uSERuserid, int pROJECTprojectid) {
        this.discussionPK = new DiscussionPK(discussionId, uSERuserid, pROJECTprojectid);
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

    @XmlTransient
    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
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
        return "si.fri.tpo.jpa.Discussion[ discussionPK=" + discussionPK + " ]";
    }
    
}
