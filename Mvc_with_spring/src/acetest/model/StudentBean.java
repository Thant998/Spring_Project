package acetest.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;

public class StudentBean {
	@NotEmpty
	private String id;
@NotEmpty
	private String name;
@NotEmpty
	private String dob;
@NotEmpty
	private String gender;
@NotEmpty
	private String phone;
@NotEmpty
	private String education;
	private List<CourseBean> attend;
	@NotEmpty
	private List<String> stuCourse;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public List<CourseBean> getAttend() {
		return attend;
	}
	public void setAttend(List<CourseBean> attend) {
		this.attend = attend;
	}
	public List<String> getStuCourse() {
		return stuCourse;
	}
	public void setStuCourse(List<String> stuCourse) {
		this.stuCourse = stuCourse;
	}

	
	public StudentBean() {
		
	}
	public StudentBean(String id, String name, String dob, String gender, String phone, String education) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.phone = phone;
		this.education = education;
	
	}
	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", dob=" + dob + ", gender=" + gender + ", phone="
				+ phone + ", education=" + education ;
	}
	
}
