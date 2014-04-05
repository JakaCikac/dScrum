/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.jpa;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "USER_STORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserStory.findAll", query = "SELECT u FROM UserStory u"),
    @NamedQuery(name = "UserStory.findByStoryId", query = "SELECT u FROM UserStory u WHERE u.storyId = :storyId"),
    @NamedQuery(name = "UserStory.findByName", query = "SELECT u FROM UserStory u WHERE u.name = :name"),
    @NamedQuery(name = "UserStory.findByContent", query = "SELECT u FROM UserStory u WHERE u.content = :content"),
    @NamedQuery(name = "UserStory.findByBusinessValue", query = "SELECT u FROM UserStory u WHERE u.businessValue = :businessValue")})
public class UserStory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "story_id")
    private Integer storyId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "content")
    private String content;
    @Column(name = "business_value")
    private Integer businessValue;
    @JoinColumns({
        @JoinColumn(name = "SPRINT_sprint_id", referencedColumnName = "sprint_id"),
        @JoinColumn(name = "SPRINT_PROJECT_project_id", referencedColumnName = "PROJECT_project_id")})
    @ManyToOne
    private Sprint sprint;
    @JoinColumn(name = "PRIORITY_priority_id", referencedColumnName = "priority_id")
    @ManyToOne(optional = false)
    private Priority pRIORITYpriorityid;
    @JoinColumn(name = "PROJECT_project_id", referencedColumnName = "project_id")
    @ManyToOne(optional = false)
    private Project pROJECTprojectid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userStory")
    private Collection<Task> taskCollection;
    @OneToMany(mappedBy = "uSERSTORYstoryid")
    private Collection<AcceptanceTest> acceptanceTestCollection;

    public UserStory() {
    }

    public UserStory(Integer storyId) {
        this.storyId = storyId;
    }

    public UserStory(Integer storyId, String name, String content) {
        this.storyId = storyId;
        this.name = name;
        this.content = content;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getBusinessValue() {
        return businessValue;
    }

    public void setBusinessValue(Integer businessValue) {
        this.businessValue = businessValue;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Priority getPRIORITYpriorityid() {
        return pRIORITYpriorityid;
    }

    public void setPRIORITYpriorityid(Priority pRIORITYpriorityid) {
        this.pRIORITYpriorityid = pRIORITYpriorityid;
    }

    public Project getPROJECTprojectid() {
        return pROJECTprojectid;
    }

    public void setPROJECTprojectid(Project pROJECTprojectid) {
        this.pROJECTprojectid = pROJECTprojectid;
    }

    @XmlTransient
    public Collection<Task> getTaskCollection() {
        return taskCollection;
    }

    public void setTaskCollection(Collection<Task> taskCollection) {
        this.taskCollection = taskCollection;
    }

    @XmlTransient
    public Collection<AcceptanceTest> getAcceptanceTestCollection() {
        return acceptanceTestCollection;
    }

    public void setAcceptanceTestCollection(Collection<AcceptanceTest> acceptanceTestCollection) {
        this.acceptanceTestCollection = acceptanceTestCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storyId != null ? storyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserStory)) {
            return false;
        }
        UserStory other = (UserStory) object;
        if ((this.storyId == null && other.storyId != null) || (this.storyId != null && !this.storyId.equals(other.storyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.jpa.UserStory[ storyId=" + storyId + " ]";
    }
    
}
