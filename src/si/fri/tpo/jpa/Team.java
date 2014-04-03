package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TEAM database table.
 * 
 */
@Entity
@NamedQuery(name="Team.findAll", query="SELECT t FROM Team t")
public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="team_id")
	private int teamId;

	@Column(name="is_product_owner")
	private byte isProductOwner;

	@Column(name="is_scrum_master")
	private byte isScrumMaster;

	@Column(name="user_id")
	private String userId;

	//bi-directional many-to-one association to Project
	@OneToMany(mappedBy="team")
	private List<Project> projects;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="teams")
	private List<User> users;

	public Team() {
	}

	public int getTeamId() {
		return this.teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public byte getIsProductOwner() {
		return this.isProductOwner;
	}

	public void setIsProductOwner(byte isProductOwner) {
		this.isProductOwner = isProductOwner;
	}

	public byte getIsScrumMaster() {
		return this.isScrumMaster;
	}

	public void setIsScrumMaster(byte isScrumMaster) {
		this.isScrumMaster = isScrumMaster;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Project addProject(Project project) {
		getProjects().add(project);
		project.setTeam(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		project.setTeam(null);

		return project;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}