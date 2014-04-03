package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DAILY_SCRUM_ENTRY database table.
 * 
 */
@Embeddable
public class DailyScrumEntryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="daily_scrum_id")
	private int dailyScrumId;

	@Column(insertable=false, updatable=false)
	private int USER_user_id;

	@Column(insertable=false, updatable=false)
	private int PROJECT_project_id;

	public DailyScrumEntryPK() {
	}
	public int getDailyScrumId() {
		return this.dailyScrumId;
	}
	public void setDailyScrumId(int dailyScrumId) {
		this.dailyScrumId = dailyScrumId;
	}
	public int getUSER_user_id() {
		return this.USER_user_id;
	}
	public void setUSER_user_id(int USER_user_id) {
		this.USER_user_id = USER_user_id;
	}
	public int getPROJECT_project_id() {
		return this.PROJECT_project_id;
	}
	public void setPROJECT_project_id(int PROJECT_project_id) {
		this.PROJECT_project_id = PROJECT_project_id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DailyScrumEntryPK)) {
			return false;
		}
		DailyScrumEntryPK castOther = (DailyScrumEntryPK)other;
		return 
			(this.dailyScrumId == castOther.dailyScrumId)
			&& (this.USER_user_id == castOther.USER_user_id)
			&& (this.PROJECT_project_id == castOther.PROJECT_project_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.dailyScrumId;
		hash = hash * prime + this.USER_user_id;
		hash = hash * prime + this.PROJECT_project_id;
		
		return hash;
	}
}