/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.gwt.server.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "comment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c"),
    @NamedQuery(name = "Comment.findByCommentId", query = "SELECT c FROM Comment c WHERE c.commentPK.commentId = :commentId"),
    @NamedQuery(name = "Comment.findByContent", query = "SELECT c FROM Comment c WHERE c.content = :content"),
    @NamedQuery(name = "Comment.findByCreatetime", query = "SELECT c FROM Comment c WHERE c.createtime = :createtime"),
    @NamedQuery(name = "Comment.findByUserUserId", query = "SELECT c FROM Comment c WHERE c.commentPK.userUserId = :userUserId"),
    @NamedQuery(name = "Comment.findByDiscussionDiscussionId", query = "SELECT c FROM Comment c WHERE c.commentPK.discussionDiscussionId = :discussionDiscussionId"),
    @NamedQuery(name = "Comment.findByDiscussionUserUserId", query = "SELECT c FROM Comment c WHERE c.commentPK.discussionUserUserId = :discussionUserUserId"),
    @NamedQuery(name = "Comment.findByDiscussionProjectProjectId", query = "SELECT c FROM Comment c WHERE c.commentPK.discussionProjectProjectId = :discussionProjectProjectId")})
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CommentPK commentPK;
    @Basic(optional = false)
    @Column(name = "content")
    private String content;
    @Basic(optional = false)
    @Column(name = "createtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;
    @JoinColumns({
        @JoinColumn(name = "discussion_discussion_id", referencedColumnName = "discussion_id", insertable = false, updatable = false),
        @JoinColumn(name = "discussion_user_user_id", referencedColumnName = "user_user_id", insertable = false, updatable = false),
        @JoinColumn(name = "discussion_project_project_id", referencedColumnName = "project_project_id", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Discussion discussion;
    @JoinColumn(name = "user_user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Comment() {
    }

    public Comment(CommentPK commentPK) {
        this.commentPK = commentPK;
    }

    public Comment(CommentPK commentPK, String content, Date createtime) {
        this.commentPK = commentPK;
        this.content = content;
        this.createtime = createtime;
    }

    public Comment(int commentId, int userUserId, int discussionDiscussionId, int discussionUserUserId, int discussionProjectProjectId) {
        this.commentPK = new CommentPK(commentId, userUserId, discussionDiscussionId, discussionUserUserId, discussionProjectProjectId);
    }

    public CommentPK getCommentPK() {
        return commentPK;
    }

    public void setCommentPK(CommentPK commentPK) {
        this.commentPK = commentPK;
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

    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commentPK != null ? commentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.commentPK == null && other.commentPK != null) || (this.commentPK != null && !this.commentPK.equals(other.commentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.gwt.server.jpa.Comment[ commentPK=" + commentPK + " ]";
    }
    
}
