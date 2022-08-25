package acetest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import acetest.dto.StudentRequestDTO;
import acetest.dto.StudentResponseDTO;

@Service("StudentDao")
public class StudentDAO {
	public static Connection con=null;
	static {
		con=MyConnection.getConnection();
	}
	
	public int insertStudent(StudentRequestDTO dto) {
		int result = 0;
		String sql = "insert into students(id,name,dob,gender,phone,education)values(?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getSid());
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getDob());
			ps.setString(4, dto.getGender());
			ps.setString(5, dto.getPhone());
			ps.setString(6, dto.getEducation());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database insert ERROR!!");
		}
		return result;
	}

	public int updateStudent(StudentRequestDTO dto) {
		int result = 0;
		String sql = "update students set name=?,dob=?,gender=?,phone=?,education=? where id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getDob());
			ps.setString(3, dto.getGender());
			ps.setString(4, dto.getPhone());
			ps.setString(5, dto.getEducation());
			ps.setString(6, dto.getSid());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database updateERROR!!");
		}
		return result;
	}

	public int deleteStudent(String studentId) {
		int result = 0;
		String sql = "delete from students where id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, studentId);
			result=ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database Delete error");
		}
		return result;
	}

	public ArrayList<StudentResponseDTO> selectAll() {
		ArrayList<StudentResponseDTO> studentList = new ArrayList<StudentResponseDTO>();
		String sql = "select * from students";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				StudentResponseDTO res = new StudentResponseDTO();
				res.setSid(rs.getString("id"));
				res.setName(rs.getString("name"));
				res.setDob(rs.getString("dob"));
				res.setGender(rs.getString("gender"));
				res.setPhone(rs.getString("phone"));
				res.setEducation(rs.getString("education"));
				//res.setCourse(rs.getString("course"));
				studentList.add(res);
			}
		} catch (SQLException e) {
			System.out.println("Database select all error!!");
		}
		return studentList;
	}

	public StudentResponseDTO selectOne(String student_id) {
		StudentResponseDTO res = new StudentResponseDTO();
		String sql = "select * from students where id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, student_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				res.setSid(rs.getString("id"));
				res.setName(rs.getString("name"));
				res.setDob(rs.getString("dob"));
				res.setGender(rs.getString("gender"));
				res.setPhone(rs.getString("phone"));
				res.setEducation(rs.getString("education"));
			}

		} catch (SQLException e) {
			System.out.println("Database select one error!!");
		}
		return res;
	}

	public void insertStudent_Course(String sid, List<String> cIds) {
		try {
			for (String cid : cIds) {
				int result = 0;
				String sql = "insert into student_course(student_id,course_id)values(?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, sid);
				ps.setString(2, cid);
				result = ps.executeUpdate();
				System.out.println("insert student_ course result" + result);
			}
		} catch (SQLException e) {
			System.out.println("Database stu_course insert ERROR!!");
		}

	}

	public ArrayList<String> selectCourseIdList(String studentId) {
		ArrayList<String> courseIdList = new ArrayList<String>();
		String sql = "select course_id from student_course where student_id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, studentId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				courseIdList.add(rs.getString("course_id"));
			}
		} catch (SQLException e) {
			System.out.println("Database selectByfilter error!!");
		}
		return  courseIdList;
	}
	public ArrayList<String> selectCourseNameList(String studentId) {
		ArrayList<String> courseList = new ArrayList<String>();
		ArrayList<String> courseIdList = new ArrayList<String>();
		String sql = "select course_id from student_course where student_id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, studentId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				courseIdList.add(rs.getString("course_id"));
			}
			for(String cid: courseIdList) {
				 sql="select * from courses where id=?";
				 ps=con.prepareStatement(sql);
				 ps.setString(1, cid.trim());
				 rs=ps.executeQuery();
				 while(rs.next()) {		 
					 courseList.add(rs.getString("name"));
				 }
			}
		} catch (SQLException e) {
			System.out.println("Database selectByfilter error!!");
		}
		return  courseList;
	}
	
	public int deleteStudent_Course( String studentId) {
		int result = 0;
		String sql = "delete from student_course where student_id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,studentId);
			result=ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database Delete error");
		}
		return result;
	}
	public List<StudentResponseDTO> selectStudentListByIdOrNameOrCourse() {
		List<StudentResponseDTO> studentList = new ArrayList<StudentResponseDTO>();
		String sql = " select distinct s.id,s.name,sc.course_id,c.name from students s join student_course sc \r\n"
				+ " on s.id=sc.student_id \r\n" + "join courses c \r\n" + " on sc.course_id=c.id";
				//+ " where s.id like ? or s.name like ? or c.name like ? ;";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			//String stuId = id.isBlank() ? id : ("%" + id + "%");
			//String stuName= name.isBlank() ? name: ("%" + name + "%");
			//String stuCourse = course.isBlank() ? course : ("%" + course + "%");
			//ps.setString(1, stuId);
			//ps.setString(2, stuName);
			//ps.setString(3, stuCourse);		
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				StudentResponseDTO res = new StudentResponseDTO();
				res.setSid(rs.getString("id"));
				res.setName(rs.getString("name"));
				res.setDob(rs.getString("dob"));
				res.setGender(rs.getString("gender"));
				res.setPhone(rs.getString("phone"));
				res.setEducation(rs.getString("education"));
				//res.setCourse(rs.getArray("name"));
				studentList.add(res);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		List<StudentResponseDTO>value=studentList.stream().distinct().collect(Collectors.toList());
		return value;
	}
	
	public List<StudentResponseDTO> searchData(StudentRequestDTO dto) {
		List<StudentResponseDTO> list=new ArrayList<>();
		String sql="SELECT * FROM students WHERE id=? OR name=?";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,dto.getSid());
			ps.setString(2,dto.getName());
			//ps.setInt(3,dto.getStudentAttend());
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				StudentResponseDTO res=new StudentResponseDTO();
				res.setSid(rs.getString("id"));
				res.setName(rs.getString("name"));
				res.setDob(rs.getString("dob"));
				res.setGender(rs.getString("gender"));
				res.setPhone(rs.getString("phone"));
				res.setEducation(rs.getString("education"));
				//res.setCourse(rs.getString("course"));
				list.add(res);
			}
		} catch (SQLException e) {
			System.out.println("database error");
		}
		return list;
	}
	

	
}
