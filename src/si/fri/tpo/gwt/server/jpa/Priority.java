/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.gwt.server.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "priority")
@NamedQueries({
    @NamedQuery(name = "Priority.findAll", query = "SELECT p FROM Priority p"),
    @NamedQuery(name = "Priority.findByPriorityId", query = "SELECT p FROM Priority p WHERE p.priorityId = :priorityId"),
    @NamedQuery(name = "Priority.findByName", query = "SELECT p FROM Priority p WHERE p.name = :name")})
public class Priority implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priority_id")
    private Integer priorityId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "priorityPriorityId")
    private List<UserStory> userStoryList;

    public Priority() {
    }

    public Priority(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public Priority(Integer priorityId, String name) {
        this.priorityId = priorityId;
        this.name = name;
    }

    public Integer getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserStory> getUserStoryList() {
        return userStoryList;
    }

    public void setUserStoryList(List<UserStory> userStoryList) {
        this.userStoryList = userStoryList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (priorityId != null ? priorityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Priority)) {
            return false;
        }
        Priority other = (Priority) object;
        if ((this.priorityId == null && other.priorityId != null) || (this.priorityId != null && !this.priorityId.equals(other.priorityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.gwt.server.jpa.Priority[ priorityId=" + priorityId + " ]";
    }
    
}
