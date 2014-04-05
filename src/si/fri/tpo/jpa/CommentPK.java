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
    @Column(name = "DISCUSSION_discussion_id")
    private int dISCUSSIONdiscussionid;
    @Basic(optional = false)
    @Column(name = "USER_user_id")
    private int uSERuserid;

    public CommentPK() {
    }

    public CommentPK(int commentId, int dISCUSSIONdiscussionid, int uSERuserid) {
        this.commentId = commentId;
        this.dISCUSSIONdiscussionid = dISCUSSIONdiscussionid;
        this.uSERuserid = uSERuserid;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getDISCUSSIONdiscussionid() {
        return dISCUSSIONdiscussionid;
    }

    public void setDISCUSSIONdiscussionid(int dISCUSSIONdiscussionid) {
        this.dISCUSSIONdiscussionid = dISCUSSIONdiscussionid;
    }

    public int getUSERuserid() {
        return uSERuserid;
    }

    public void setUSERuserid(int uSERuserid) {
        this.uSERuserid = uSERuserid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) commentId;
        hash += (int) dISCUSSIONdiscussionid;
        hash += (int) uSERuserid;
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
        if (this.dISCUSSIONdiscussionid != other.dISCUSSIONdiscussionid) {
            return false;
        }
        if (this.uSERuserid != other.uSERuserid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.jpa.CommentPK[ commentId=" + commentId + ", dISCUSSIONdiscussionid=" + dISCUSSIONdiscussionid + ", uSERuserid=" + uSERuserid + " ]";
    }
    
}
