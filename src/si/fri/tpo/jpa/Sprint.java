package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPRINT database table.
 * 
 */
@Entity
@NamedQuery(name="Sprint.findAll", query="SELECT s FROM Sprint s")
public class Sprint implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SprintPK id;

	@Temporal(TemporalType.DATE)
	@Column(name="end_date")
	private Date endDate;

	@Column(name="seq_number")
	private int seqNumber;

	@Temporal(TemporalType.DATE)
	@Column(name="start_date")
	private Date startDate;

	private String status;

	private int velocity;

	//bi-directional many-to-one association to Project
	@ManyToOne
	private Project project;

	//bi-directional many-to-one association to UserStory
	@OneToMany(mappedBy="sprint")
	private List<UserStory> userStories;

	public Sprint() {
	}

	public SprintPK getId() {
		return this.id;
	}

	public void setId(SprintPK id) {
		this.id = id;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getSeqNumber() {
		return this.seqNumber;
	}

	public void setSeqNumber(int seqNumber) {
		this.seqNumber = seqNumber;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getVelocity() {
		return this.velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<UserStory> getUserStories() {
		return this.userStories;
	}

	public void setUserStories(List<UserStory> userStories) {
		this.userStories = userStories;
	}

	public UserStory addUserStory(UserStory userStory) {
		getUserStories().add(userStory);
		userStory.setSprint(this);

		return userStory;
	}

	public UserStory removeUserStory(UserStory userStory) {
		getUserStories().remove(userStory);
		userStory.setSprint(null);

		return userStory;
	}

}