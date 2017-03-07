

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Student {
	
	public static final Integer GRADUATE_ID = 1;
	public static final Integer U_GRADUATE_ID = 2;

	private Integer studentId;
	private String nuid;
	private String firstName;
	private String lastName;
	private Set<String> emails = new HashSet<String>();
	private Set<Course> courses = new HashSet<Course>();

	public Student() {
	}
	
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getNuid() {
		return nuid;
	}
	public void setNuid(String nuid) {
		this.nuid = nuid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return lastName+", "+firstName+"("+nuid+"): "+emails;
	}
	
	public void addEmail(String email) {
		this.emails.add(email);
	}

	public Set<String> getEmails() {
		return emails;
	}
	
	public void addCourse(Course c) {
		this.courses.add(c);
	}
	
	public Set<Course> getCourses() {
		return this.courses;
	}

	
	public static Student[] getAllStudents() {

		List<String> nuids = new ArrayList<String>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		ResultSet rs = null;

		String query = "SELECT nuid AS NUID FROM student";
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				nuids.add(rs.getString("NUID"));
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if(rs != null && !rs.isClosed()) {
					rs.close();
				}
				if(ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Student result[] = new Student[nuids.size()];
		
		for(int i=0; i<nuids.size(); i++) {
			result[i] = getStudentByNuid(nuids.get(i).toString());
		}

		return result;
		
	}
	
	public static void addStudent(String firstName, String lastName, String nuid, Boolean isGraduate) {

		//make sure student doesn't exist first...
		Student s = getStudentByNuid(nuid);
		if(s != null) {
			return;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		String query = "INSERT INTO student (first_name, last_name, nuid, student_type_id) VALUES (?, ?, ?, ?);";
	
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, nuid);
			ps.setInt(4, isGraduate ? GRADUATE_ID : U_GRADUATE_ID);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void addCourseToStudent(String nuid, String courseName, String semester, String anio, String status, String ranking, int contVez) {

		//code reuse!
		//find student with given nuid, if it exists...
		Student s = getStudentByNuid(nuid);
		Course c = Course.getCourseByName(courseName);

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	
	//	System.out.println("realiza conexion: " +c.getCourseId());
		String query = "INSERT INTO enrollment (nuid, course_id, semester, anio, status, ranking, numtimes) VALUES (?, ?, ?,?,?,?,?);";
	
		PreparedStatement ps = null;
//		System.out.println("entra a realizar insert: " +c.getCourseId());
		try {
			ps = conn.prepareStatement(query);
			
			System.out.println("s1: " + nuid);
			System.out.println("s2: " +courseName);
			
			ps.setString(1, nuid);
			
			ps.setString(2, courseName);
			ps.setString(3, semester);
			ps.setString(4, anio);
			ps.setString(5, status);
			ps.setString(6, ranking);
			ps.setInt(7, contVez);

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void addEmailToStudent(String nuid, String email) {

		//code reuse!
		//find student with given nuid, if it exists...
		Student s = getStudentByNuid(nuid);
		if(s == null)
			return;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		String query = "INSERT INTO email (student_id, address) VALUES (?, ?);";
	
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, s.getStudentId());
			ps.setString(2, email);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static Student getStudentByNuid(String nuid) {

		Student student = new Student();
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		

		String query = "SELECT student_id   AS student_id, " +
					   "       nuid         AS nuid, " +
		               "       first_name   AS first_name," +
		               "       last_name    AS last_name " +
		               "FROM student " +
		               "WHERE nuid = ?";
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			//unfortunately, JDBC does not support named parameters
			ps.setString(1, nuid);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if(rs.next()) {
				student.setStudentId(rs.getInt("student_id"));
				student.setNuid(rs.getString("nuid"));
				student.setFirstName(rs.getString("first_name"));
				student.setLastName(rs.getString("last_name"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if(rs != null && !rs.isClosed()) {
					rs.close();
				}
				if(ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		//now that we have a student, we need to make another query to get all of his emails...
		try {
			ps = conn.prepareStatement("SELECT address FROM email WHERE student_id = ?");
			ps.setInt(1, student.getStudentId());
			rs = ps.executeQuery();
			while(rs.next()) {
				student.getEmails().add(rs.getString("address"));
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			ps = conn.prepareStatement("SELECT c.course_id, c.name, c.description FROM " +
					"enrollment e JOIN course c ON (e.course_id = c.course_id) " +
					"WHERE e.nuid = ?");
			ps.setString(1, student.getNuid());
			rs = ps.executeQuery();
			while(rs.next()) {
				Course c = new Course();
				c.setCourseId(rs.getString("c.course_id"));
				c.setName(rs.getString("c.name"));
				c.setDescrption(rs.getString("c.description"));
				student.addCourse(c);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return student;
	}
	
	public static void removeAll() {
		Student allStudents[] = Student.getAllStudents();
		for(Student s : allStudents) {
			Student.removeStudentByNuid(s.getNuid());
		}
	}
	
	public static void removeStudentByNuid(String nuid) {
		
		Student s = getStudentByNuid(nuid);
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		PreparedStatement ps = null;
		String query;
		
		
		try {
			conn.setAutoCommit(false);
			query = "DELETE FROM enrollment WHERE nuid = ?";			
			ps = conn.prepareStatement(query);
			ps.setString(1, s.getNuid());
			ps.executeUpdate();

			query = "DELETE FROM email WHERE student_id = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, s.getStudentId());
			ps.executeUpdate();
			
			query = "DELETE FROM student WHERE student_id = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, s.getStudentId());
			ps.executeUpdate();

			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e2) {
				System.out.println("SQLException attempting to rollback: ");
				e2.printStackTrace();
			}
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if(ps != null && !ps.isClosed())
					ps.close();
				if(conn != null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}


}
