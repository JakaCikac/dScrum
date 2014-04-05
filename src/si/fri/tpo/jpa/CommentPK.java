/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.jpa;

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
    @Column(name = "discussion_discussion_id")
    private int discussionDiscussionId;
    @Basic(optional = false)
    @Column(name = "user_user_id")
    private int userUserId;

    public CommentPK() {
    }

    public CommentPK(int commentId, int discussionDiscussionId, int userUserId) {
        this.commentId = commentId;
        this.discussionDiscussionId = discussionDiscussionId;
        this.userUserId = userUserId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getDiscussionDiscussionId() {
        return discussionDiscussionId;
    }

    public void setDiscussionDiscussionId(int discussionDiscussionId) {
        this.discussionDiscussionId = discussionDiscussionId;
    }

    public int getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(int userUserId) {
        this.userUserId = userUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) commentId;
        hash += (int) discussionDiscussionId;
        hash += (int) userUserId;
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
        if (this.discussionDiscussionId != other.discussionDiscussionId) {
            return false;
        }
        if (this.userUserId != other.userUserId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.jpa.CommentPK[ commentId=" + commentId + ", discussionDiscussionId=" + discussionDiscussionId + ", userUserId=" + userUserId + " ]";
    }
    
}
