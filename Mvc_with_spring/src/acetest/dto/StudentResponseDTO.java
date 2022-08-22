package acetest.dto;

import java.util.List;

public class StudentResponseDTO {
	private String sid;
	private String name;
	private String dob;
	private String gender;
	private String phone;
	private String education;
	private List<String> course;


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public List<String> getCourse() {
		return course;
	}
	public void setCourse(List<String> course) {
		this.course = course;
	}
	public StudentResponseDTO() {
		
	}
	public StudentResponseDTO(String sid, String name, String dob, String gender, String phone, String education,
			List<String> course) {
		super();
		this.sid = sid;
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.phone = phone;
		this.education = education;
		this.course = course;
	}
	@Override
	public String toString() {
		return "StudentRequestDTO [sid=" + sid + ", name=" + name + ", dob=" + dob + ", gender=" + gender + ", phone="
				+ phone + ", education=" + education + ", course=" + course + "]";
	}

	
}
