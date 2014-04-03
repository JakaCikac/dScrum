package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the USER database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private int userId;

	private String email;

	@Column(name="first_name")
	private String firstName;

	@Column(name="is_active")
	private byte isActive;

	@Column(name="is_admin")
	private byte isAdmin;

	@Column(name="last_name")
	private String lastName;

	private String password;

	private String salt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="time_created")
	private Date timeCreated;

	private String username;

	//bi-directional many-to-one association to DailyScrumEntry
	@OneToMany(mappedBy="user")
	private List<DailyScrumEntry> dailyScrumEntries;

	//bi-directional many-to-one association to Discussion
	@OneToMany(mappedBy="user")
	private List<Discussion> discussions;

	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="user")
	private List<Task> tasks;

	//bi-directional many-to-many association to Team
	@ManyToMany
	@JoinTable(
		name="TEAM_has_USER"
		, joinColumns={
			@JoinColumn(name="USER_user_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="TEAM_team_id")
			}
		)
	private List<Team> teams;

	//bi-directional many-to-one association to Workload
	@OneToMany(mappedBy="user")
	private List<Workload> workloads;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public byte getIsActive() {
		return this.isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}

	public byte getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(byte isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Date getTimeCreated() {
		return this.timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<DailyScrumEntry> getDailyScrumEntries() {
		return this.dailyScrumEntries;
	}

	public void setDailyScrumEntries(List<DailyScrumEntry> dailyScrumEntries) {
		this.dailyScrumEntries = dailyScrumEntries;
	}

	public DailyScrumEntry addDailyScrumEntry(DailyScrumEntry dailyScrumEntry) {
		getDailyScrumEntries().add(dailyScrumEntry);
		dailyScrumEntry.setUser(this);

		return dailyScrumEntry;
	}

	public DailyScrumEntry removeDailyScrumEntry(DailyScrumEntry dailyScrumEntry) {
		getDailyScrumEntries().remove(dailyScrumEntry);
		dailyScrumEntry.setUser(null);

		return dailyScrumEntry;
	}

	public List<Discussion> getDiscussions() {
		return this.discussions;
	}

	public void setDiscussions(List<Discussion> discussions) {
		this.discussions = discussions;
	}

	public Discussion addDiscussion(Discussion discussion) {
		getDiscussions().add(discussion);
		discussion.setUser(this);

		return discussion;
	}

	public Discussion removeDiscussion(Discussion discussion) {
		getDiscussions().remove(discussion);
		discussion.setUser(null);

		return discussion;
	}

	public List<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Task addTask(Task task) {
		getTasks().add(task);
		task.setUser(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setUser(null);

		return task;
	}

	public List<Team> getTeams() {
		return this.teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public List<Workload> getWorkloads() {
		return this.workloads;
	}

	public void setWorkloads(List<Workload> workloads) {
		this.workloads = workloads;
	}

	public Workload addWorkload(Workload workload) {
		getWorkloads().add(workload);
		workload.setUser(this);

		return workload;
	}

	public Workload removeWorkload(Workload workload) {
		getWorkloads().remove(workload);
		workload.setUser(null);

		return workload;
	}

}