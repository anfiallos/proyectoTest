import org.apache.log4j.Logger;

public class CoursePrediccion {

	private static org.apache.log4j.Logger log = Logger.getLogger(Course.class);

	private String courseId;
	private String estadoCurso;

	public static void setLog(org.apache.log4j.Logger log) {
		CoursePrediccion.log = log;
	}

	public String getEstadoAprob() {
		return estadoAprob;
	}

	public void setEstadoAprob(String estadoAprob) {
		this.estadoAprob = estadoAprob;
	}
	private String estadoAprob;
	
	public CoursePrediccion() {
	}
	
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String string) {
		this.courseId = string;
	}

	}
