package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DAILY_SCRUM_ENTRY database table.
 * 
 */
@Entity
@Table(name="DAILY_SCRUM_ENTRY")
@NamedQuery(name="DailyScrumEntry.findAll", query="SELECT d FROM DailyScrumEntry d")
public class DailyScrumEntry implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DailyScrumEntryPK id;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name="future_work")
	private String futureWork;

	@Column(name="past_work")
	private String pastWork;

	@Column(name="problems_and_issues")
	private String problemsAndIssues;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	//bi-directional many-to-one association to Project
	@ManyToOne
	private Project project;

	public DailyScrumEntry() {
	}

	public DailyScrumEntryPK getId() {
		return this.id;
	}

	public void setId(DailyScrumEntryPK id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFutureWork() {
		return this.futureWork;
	}

	public void setFutureWork(String futureWork) {
		this.futureWork = futureWork;
	}

	public String getPastWork() {
		return this.pastWork;
	}

	public void setPastWork(String pastWork) {
		this.pastWork = pastWork;
	}

	public String getProblemsAndIssues() {
		return this.problemsAndIssues;
	}

	public void setProblemsAndIssues(String problemsAndIssues) {
		this.problemsAndIssues = problemsAndIssues;
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