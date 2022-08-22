package acetest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import acetest.dto.UserRequestDTO;
import acetest.dto.UserResponseDTO;
import acetest.model.LoginBean;;

@Service("UserDao")
public class UserDAO {
	public static Connection con=null;
	static {
		con=MyConnection.getConnection();
	}
	public void insertData(UserRequestDTO dto) {
		String sql="INSERT INTO users(email,password,role,id,name)"+
				"values(?,?,?,?,?)";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,dto.getUserEmail());
			ps.setString(2,dto.getUserPassword());
			ps.setInt(3,dto.getUserRole());
			ps.setString(4,dto.getUserId());
			ps.setString(5,dto.getUserName());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<UserResponseDTO> selectUser(LoginBean bean) {
		ArrayList<UserResponseDTO> list=new ArrayList<>();
		String sql="SELECT * FROM users WHERE name='"+bean.getLoginName()+
				"' AND password='"+bean.getLoginPassword()+"'";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery(sql);
			while (rs.next()) {
				UserResponseDTO dto=new UserResponseDTO();
				dto.setUserEmail(rs.getString("email"));
				dto.setUserPassword(rs.getString("password"));
				dto.setUserRole(rs.getInt("role"));
				dto.setUserId(rs.getString("id"));
				dto.setUserName(rs.getString("name"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<UserResponseDTO> selectAll(){
		ArrayList<UserResponseDTO> list=new ArrayList<>();
		String sql="SELECT * FROM users";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery(sql);
			while (rs.next()) {
				UserResponseDTO dto=new UserResponseDTO();
				dto.setUserEmail(rs.getString("email"));
				dto.setUserPassword(rs.getString("password"));
				dto.setUserRole(rs.getInt("role"));
				dto.setUserId(rs.getString("id"));
				dto.setUserName(rs.getString("name"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void updateData(UserRequestDTO dto) {
		String sql="UPDATE users SET email=?,password=?,role=?,name=?"+
						"WHERE id=?";			
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,dto.getUserEmail());
			ps.setString(2,dto.getUserPassword());
			ps.setInt(3,dto.getUserRole());
			ps.setString(5,dto.getUserId());
			ps.setString(4,dto.getUserName());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("database error");
		}
	}
	
	public void deleteData(UserRequestDTO dto) {
		String sql="DELETE FROM users WHERE id=?";			
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,dto.getUserId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("database error");
		}
	}
	
	public UserResponseDTO selectOne(UserRequestDTO dto) {
		UserResponseDTO res=new UserResponseDTO();
		String sql="SELECT * FROM users WHERE id=?";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,dto.getUserId());
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				res.setUserEmail(rs.getString("email"));
				res.setUserPassword(rs.getString("password"));
				res.setUserRole(rs.getInt("role"));
				res.setUserId(rs.getString("id"));
				res.setUserName(rs.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println("database error");
		}
		return res;
	}
	
	public List<UserResponseDTO> searchData(UserRequestDTO dto) {
		List<UserResponseDTO> list=new ArrayList<>();
		String sql="SELECT * FROM users WHERE id=? OR name=?";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,dto.getUserId());
			ps.setString(2,dto.getUserName());
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				UserResponseDTO res=new UserResponseDTO();
				res.setUserEmail(rs.getString("email"));
				res.setUserPassword(rs.getString("password"));
				res.setUserRole(rs.getInt("role"));
				res.setUserId(rs.getString("id"));
				res.setUserName(rs.getString("name"));
				list.add(res);
			}
		} catch (SQLException e) {
			System.out.println("database error");
		}
		return list;
	}

}
