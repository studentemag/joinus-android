package mag.joinus.model.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "participants")
public class Participant {
	
	@DatabaseField(uniqueCombo = true)
	String phone;
	
	@DatabaseField(uniqueCombo = true)
	int meeting_id;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getMeeting_id() {
		return meeting_id;
	}

	public void setMeeting_id(int meeting_id) {
		this.meeting_id = meeting_id;
	}
	
	
	
}
