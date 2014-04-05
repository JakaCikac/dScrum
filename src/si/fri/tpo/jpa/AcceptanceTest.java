/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "ACCEPTANCE_TEST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AcceptanceTest.findAll", query = "SELECT a FROM AcceptanceTest a"),
    @NamedQuery(name = "AcceptanceTest.findByAcceptanceTestId", query = "SELECT a FROM AcceptanceTest a WHERE a.acceptanceTestId = :acceptanceTestId"),
    @NamedQuery(name = "AcceptanceTest.findByContent", query = "SELECT a FROM AcceptanceTest a WHERE a.content = :content")})
public class AcceptanceTest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "acceptance_test_id")
    private Integer acceptanceTestId;
    @Basic(optional = false)
    @Column(name = "content")
    private String content;
    @JoinColumn(name = "USER_STORY_story_id", referencedColumnName = "story_id")
    @ManyToOne
    private UserStory uSERSTORYstoryid;

    public AcceptanceTest() {
    }

    public AcceptanceTest(Integer acceptanceTestId) {
        this.acceptanceTestId = acceptanceTestId;
    }

    public AcceptanceTest(Integer acceptanceTestId, String content) {
        this.acceptanceTestId = acceptanceTestId;
        this.content = content;
    }

    public Integer getAcceptanceTestId() {
        return acceptanceTestId;
    }

    public void setAcceptanceTestId(Integer acceptanceTestId) {
        this.acceptanceTestId = acceptanceTestId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserStory getUSERSTORYstoryid() {
        return uSERSTORYstoryid;
    }

    public void setUSERSTORYstoryid(UserStory uSERSTORYstoryid) {
        this.uSERSTORYstoryid = uSERSTORYstoryid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acceptanceTestId != null ? acceptanceTestId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AcceptanceTest)) {
            return false;
        }
        AcceptanceTest other = (AcceptanceTest) object;
        if ((this.acceptanceTestId == null && other.acceptanceTestId != null) || (this.acceptanceTestId != null && !this.acceptanceTestId.equals(other.acceptanceTestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.jpa.AcceptanceTest[ acceptanceTestId=" + acceptanceTestId + " ]";
    }
    
}
