/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.gwt.server.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "comment")
@NamedQueries({
    @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c"),
    @NamedQuery(name = "Comment.findByCommentId", query = "SELECT c FROM Comment c WHERE c.commentPK.commentId = :commentId"),
    @NamedQuery(name = "Comment.findByContent", query = "SELECT c FROM Comment c WHERE c.content = :content"),
    @NamedQuery(name = "Comment.findByCreatetime", query = "SELECT c FROM Comment c WHERE c.createtime = :createtime"),
    @NamedQuery(name = "Comment.findByDiscussionDiscussionId", query = "SELECT c FROM Comment c WHERE c.commentPK.discussionDiscussionId = :discussionDiscussionId"),
    @NamedQuery(name = "Comment.findByUserUserId", query = "SELECT c FROM Comment c WHERE c.commentPK.userUserId = :userUserId")})
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
    @JoinColumn(name = "user_user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumns(value = {
            @JoinColumn(name = "discussion_discussion_id", referencedColumnName = "discussion_id", insertable = false, updatable = false),
            @JoinColumn(name = "user_user_id", referencedColumnName = "user_user_id", insertable = false, updatable = false),
            @JoinColumn(name = "project_project_id", referencedColumnName = "project_project_id", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Discussion discussion;

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

    public Comment(int commentId, int discussionDiscussionId, int userUserId) {
        this.commentPK = new CommentPK(commentId, discussionDiscussionId, userUserId);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
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
