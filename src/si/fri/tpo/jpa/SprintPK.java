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
public class SprintPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "sprint_id")
    private int sprintId;
    @Basic(optional = false)
    @Column(name = "PROJECT_project_id")
    private int pROJECTprojectid;

    public SprintPK() {
    }

    public SprintPK(int sprintId, int pROJECTprojectid) {
        this.sprintId = sprintId;
        this.pROJECTprojectid = pROJECTprojectid;
    }

    public int getSprintId() {
        return sprintId;
    }

    public void setSprintId(int sprintId) {
        this.sprintId = sprintId;
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
        hash += (int) sprintId;
        hash += (int) pROJECTprojectid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprintPK)) {
            return false;
        }
        SprintPK other = (SprintPK) object;
        if (this.sprintId != other.sprintId) {
            return false;
        }
        if (this.pROJECTprojectid != other.pROJECTprojectid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.jpa.SprintPK[ sprintId=" + sprintId + ", pROJECTprojectid=" + pROJECTprojectid + " ]";
    }
    
}
