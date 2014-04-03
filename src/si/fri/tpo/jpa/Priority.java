package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the PRIORITY database table.
 * 
 */
@Entity
@NamedQuery(name="Priority.findAll", query="SELECT p FROM Priority p")
public class Priority implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="priority_id")
	private int priorityId;

	private String name;

	//bi-directional many-to-one association to UserStory
	@OneToMany(mappedBy="priority")
	private List<UserStory> userStories;

	public Priority() {
	}

	public int getPriorityId() {
		return this.priorityId;
	}

	public void setPriorityId(int priorityId) {
		this.priorityId = priorityId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserStory> getUserStories() {
		return this.userStories;
	}

	public void setUserStories(List<UserStory> userStories) {
		this.userStories = userStories;
	}

	public UserStory addUserStory(UserStory userStory) {
		getUserStories().add(userStory);
		userStory.setPriority(this);

		return userStory;
	}

	public UserStory removeUserStory(UserStory userStory) {
		getUserStories().remove(userStory);
		userStory.setPriority(null);

		return userStory;
	}

}