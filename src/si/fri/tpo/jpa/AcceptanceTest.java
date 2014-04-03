package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ACCEPTANCE_TEST database table.
 * 
 */
@Entity
@Table(name="ACCEPTANCE_TEST")
@NamedQuery(name="AcceptanceTest.findAll", query="SELECT a FROM AcceptanceTest a")
public class AcceptanceTest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="acceptance_test_id")
	private int acceptanceTestId;

	private String content;

	//bi-directional many-to-one association to UserStory
	@ManyToOne
	@JoinColumn(name="USER_STORY_story_id")
	private UserStory userStory;

	public AcceptanceTest() {
	}

	public int getAcceptanceTestId() {
		return this.acceptanceTestId;
	}

	public void setAcceptanceTestId(int acceptanceTestId) {
		this.acceptanceTestId = acceptanceTestId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserStory getUserStory() {
		return this.userStory;
	}

	public void setUserStory(UserStory userStory) {
		this.userStory = userStory;
	}

}