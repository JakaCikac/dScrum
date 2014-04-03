package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DISCUSSION database table.
 * 
 */
@Entity
@NamedQuery(name="Discussion.findAll", query="SELECT d FROM Discussion d")
public class Discussion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DiscussionPK id;

	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	//bi-directional many-to-one association to Project
	@ManyToOne
	private Project project;

	public Discussion() {
	}

	public DiscussionPK getId() {
		return this.id;
	}

	public void setId(DiscussionPK id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}