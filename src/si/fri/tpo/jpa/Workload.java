/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.jpa;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "WORKLOAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workload.findAll", query = "SELECT w FROM Workload w"),
    @NamedQuery(name = "Workload.findByWorkloadId", query = "SELECT w FROM Workload w WHERE w.workloadPK.workloadId = :workloadId"),
    @NamedQuery(name = "Workload.findByTASKtaskid", query = "SELECT w FROM Workload w WHERE w.workloadPK.tASKtaskid = :tASKtaskid"),
    @NamedQuery(name = "Workload.findByTASKUSERSTORYstoryid", query = "SELECT w FROM Workload w WHERE w.workloadPK.tASKUSERSTORYstoryid = :tASKUSERSTORYstoryid"),
    @NamedQuery(name = "Workload.findByUSERuserid", query = "SELECT w FROM Workload w WHERE w.workloadPK.uSERuserid = :uSERuserid")})
public class Workload implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WorkloadPK workloadPK;
    @Lob
    @Column(name = "time_spent")
    private String timeSpent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workload")
    private Collection<Workblock> workblockCollection;
    @JoinColumn(name = "USER_user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumns({
        @JoinColumn(name = "TASK_task_id", referencedColumnName = "task_id", insertable = false, updatable = false),
        @JoinColumn(name = "TASK_USER_STORY_story_id", referencedColumnName = "USER_STORY_story_id", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Task task;

    public Workload() {
    }

    public Workload(WorkloadPK workloadPK) {
        this.workloadPK = workloadPK;
    }

    public Workload(int workloadId, int tASKtaskid, int tASKUSERSTORYstoryid, int uSERuserid) {
        this.workloadPK = new WorkloadPK(workloadId, tASKtaskid, tASKUSERSTORYstoryid, uSERuserid);
    }

    public WorkloadPK getWorkloadPK() {
        return workloadPK;
    }

    public void setWorkloadPK(WorkloadPK workloadPK) {
        this.workloadPK = workloadPK;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

    @XmlTransient
    public Collection<Workblock> getWorkblockCollection() {
        return workblockCollection;
    }

    public void setWorkblockCollection(Collection<Workblock> workblockCollection) {
        this.workblockCollection = workblockCollection;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workloadPK != null ? workloadPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workload)) {
            return false;
        }
        Workload other = (Workload) object;
        if ((this.workloadPK == null && other.workloadPK != null) || (this.workloadPK != null && !this.workloadPK.equals(other.workloadPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.jpa.Workload[ workloadPK=" + workloadPK + " ]";
    }
    
}
