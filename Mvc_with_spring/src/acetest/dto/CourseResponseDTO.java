package acetest.dto;

public class CourseResponseDTO {
	private String cid;
	private String name;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CourseResponseDTO() {
		
	}
	public CourseResponseDTO(String cid, String name) {
		super();
		this.cid = cid;
		this.name = name;
	}
	@Override
	public String toString() {
		return "CourseRequestDTO [cid=" + cid + ", cname=" + name + "]";
	}
}
