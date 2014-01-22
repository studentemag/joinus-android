package mag.joinus.model.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "participants")
public class Participant {
	
	@DatabaseField(uniqueCombo = true)
	int user_id;
	
	@DatabaseField(uniqueCombo = true)
	int meeting_id;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getMeeting_id() {
		return meeting_id;
	}

	public void setMeeting_id(int meeting_id) {
		this.meeting_id = meeting_id;
	}
	
	
	
}
