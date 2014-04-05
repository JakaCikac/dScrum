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
public class DailyScrumEntryPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "daily_scrum_id")
    private int dailyScrumId;
    @Basic(optional = false)
    @Column(name = "USER_user_id")
    private int uSERuserid;
    @Basic(optional = false)
    @Column(name = "PROJECT_project_id")
    private int pROJECTprojectid;

    public DailyScrumEntryPK() {
    }

    public DailyScrumEntryPK(int dailyScrumId, int uSERuserid, int pROJECTprojectid) {
        this.dailyScrumId = dailyScrumId;
        this.uSERuserid = uSERuserid;
        this.pROJECTprojectid = pROJECTprojectid;
    }

    public int getDailyScrumId() {
        return dailyScrumId;
    }

    public void setDailyScrumId(int dailyScrumId) {
        this.dailyScrumId = dailyScrumId;
    }

    public int getUSERuserid() {
        return uSERuserid;
    }

    public void setUSERuserid(int uSERuserid) {
        this.uSERuserid = uSERuserid;
    }

    public int getPROJECTprojectid() {
        return pROJECTprojectid;
    }

    public void setPROJECTprojectid(int pROJECTprojectid) {
        this.pROJECTprojectid = pROJECTprojectid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) dailyScrumId;
        hash += (int) uSERuserid;
        hash += (int) pROJECTprojectid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DailyScrumEntryPK)) {
            return false;
        }
        DailyScrumEntryPK other = (DailyScrumEntryPK) object;
        if (this.dailyScrumId != other.dailyScrumId) {
            return false;
        }
        if (this.uSERuserid != other.uSERuserid) {
            return false;
        }
        if (this.pROJECTprojectid != other.pROJECTprojectid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.jpa.DailyScrumEntryPK[ dailyScrumId=" + dailyScrumId + ", uSERuserid=" + uSERuserid + ", pROJECTprojectid=" + pROJECTprojectid + " ]";
    }
    
}
