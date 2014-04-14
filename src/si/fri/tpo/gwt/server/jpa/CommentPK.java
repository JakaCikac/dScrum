/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.gwt.server.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Administrator
 */
@Embeddable
public class CommentPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "comment_id")
    private int commentId;
    @Basic(optional = false)
    @Column(name = "user_user_id")
    private int userUserId;
    @Basic(optional = false)
    @Column(name = "discussion_discussion_id")
    private int discussionDiscussionId;
    @Basic(optional = false)
    @Column(name = "discussion_user_user_id")
    private int discussionUserUserId;
    @Basic(optional = false)
    @Column(name = "discussion_project_project_id")
    private int discussionProjectProjectId;

    public CommentPK() {
    }

    public CommentPK(int commentId, int userUserId, int discussionDiscussionId, int discussionUserUserId, int discussionProjectProjectId) {
        this.commentId = commentId;
        this.userUserId = userUserId;
        this.discussionDiscussionId = discussionDiscussionId;
        this.discussionUserUserId = discussionUserUserId;
        this.discussionProjectProjectId = discussionProjectProjectId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(int userUserId) {
        this.userUserId = userUserId;
    }

    public int getDiscussionDiscussionId() {
        return discussionDiscussionId;
    }

    public void setDiscussionDiscussionId(int discussionDiscussionId) {
        this.discussionDiscussionId = discussionDiscussionId;
    }

    public int getDiscussionUserUserId() {
        return discussionUserUserId;
    }

    public void setDiscussionUserUserId(int discussionUserUserId) {
        this.discussionUserUserId = discussionUserUserId;
    }

    public int getDiscussionProjectProjectId() {
        return discussionProjectProjectId;
    }

    public void setDiscussionProjectProjectId(int discussionProjectProjectId) {
        this.discussionProjectProjectId = discussionProjectProjectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) commentId;
        hash += (int) userUserId;
        hash += (int) discussionDiscussionId;
        hash += (int) discussionUserUserId;
        hash += (int) discussionProjectProjectId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommentPK)) {
            return false;
        }
        CommentPK other = (CommentPK) object;
        if (this.commentId != other.commentId) {
            return false;
        }
        if (this.userUserId != other.userUserId) {
            return false;
        }
        if (this.discussionDiscussionId != other.discussionDiscussionId) {
            return false;
        }
        if (this.discussionUserUserId != other.discussionUserUserId) {
            return false;
        }
        if (this.discussionProjectProjectId != other.discussionProjectProjectId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.gwt.server.jpa.CommentPK[ commentId=" + commentId + ", userUserId=" + userUserId + ", discussionDiscussionId=" + discussionDiscussionId + ", discussionUserUserId=" + discussionUserUserId + ", discussionProjectProjectId=" + discussionProjectProjectId + " ]";
    }
    
}
