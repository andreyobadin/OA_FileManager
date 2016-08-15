package entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table(name="OA_records")

	
	public class FileEntity {
		@Id
		@GeneratedValue
		@Column(name="record_id")
		private long record_id;
			
		@Column(name="content")
	    private String content;
		
		@Column(name="creation_Date")
	    private Date creation_Date;

		public long getRecord_id() {
			return record_id;
		}

		public void setRecord_id(long record_id) {
			this.record_id = record_id;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public Date getCreation_Date() {
			return creation_Date;
		}

		public void setCreation_Date(Date creation_Date) {
			this.creation_Date = creation_Date;
		}
	 	
		
	 	
	}
