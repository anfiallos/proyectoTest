

import org.apache.log4j.BasicConfigurator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import java.util.Random;
public class SimulacionEventosRegla2Semestres {
	
	private static org.apache.log4j.Logger log = Logger.getLogger(SimulacionEventosRegla2Semestres.class);

	public static void main(String args[]) {

		//alternative to properties file:
		BasicConfigurator.configure();
		
		log.info("Removing all Students...");
		Student.removeAll();
		Course.removeAll();

		Course.addCourse("CELEX00067",   "CELEX00067",  "INGLÉS BÁSICO A");
		Course.addCourse("CELEX00075", "CELEX00075", "INGLÉS BÁSICO B");
		Course.addCourse("FIEC04341", "FIEC04341", "FUNDAMENTOS DE PROGRAMACIÓN");
		Course.addCourse("FIEC06411", "FIEC06411", "COMPUTACIÓN Y SOCIEDAD");
		Course.addCourse("FIEC06460", "FIEC06460", "HERRAMIENTAS DE COLABORACIÓN DIGITAL");
		Course.addCourse("FMAR04093", "FMAR04093", "BIOLOGÍA");
		Course.addCourse("ICF00687", "ICF00687", "FÍSICA A");
		Course.addCourse("ICHE00877", "ICHE00877", "TÉC.EXP.ORAL ESCRITA E INVESTIGACIÓN (B)");
		Course.addCourse("ICM00604", "ICM00604", "ALGEBRA LINEAL (B)");
		Course.addCourse("ICM01941", "ICM01941", "CÁLCULO DIFERENCIAL (2005)");		
		Course.addCourse("ICM01958", "ICM01958", "CÁLCULO INTEGRAL (2005)");
		Course.addCourse("ICQ00018", "ICQ00018", "QUÍMICA GENERAL I (B)");
		Course.addCourse("FIEC04622", "FIEC04622", "PROGRAMACIÓN ORIENTADA A OBJETOS");
		Course.addCourse("ICM01974", "ICM01974", "ECUACIONES DIFERENCIALES (2005)");
		Course.addCourse("ICM01966", "ICM01966", "CÁLCULO DE VARIAS VARIABLES (2005)");	
		Course.addCourse("ICF00703", "ICF00703", "FÍSICA C");	
		Course.addCourse("CELEX00083", "CELEX00083", "INGLÉS INTERMEDIO A");			
		Course.addCourse("FIEC03012", "FIEC03012", "ESTRUCTURAS DE DATOS");		
		Course.addCourse("ICM00901", "ICM00901", "MATEMÁTICAS DISCRETAS (IEC)");	
		Course.addCourse("ICHE00885", "ICHE00885", "ECOLOGÍA Y EDUCACIÓN AMBIENTAL (B)");	
		Course.addCourse("FIEC01735", "FIEC01735", "ANÁLISIS DE REDES ELÉCTRICAS I");	
		Course.addCourse("CELEX00091", "CELEX00091", "INGLÉS INTERMEDIO B");
		Course.addCourse("FIEC03319", "FIEC03319", "ORGANIZACIÓN Y ARQUITECTURA DE COMPUTADORES");
		Course.addCourse("FIEC01552", "FIEC01552", "LENGUAJES DE PROGRAMACIÓN");	
		Course.addCourse("FIEC04366", "FIEC04366", "ANÁLISIS DE ALGORÍTMOS");	
		Course.addCourse("ICM00166", "ICM00166", "ESTADISTICA (ING.) (B)");		
		Course.addCourse("FIEC04705", "FIEC04705", "REDES DE COMPUTADORES");			 	
		Course.addCourse("FIEC00299", "FIEC00299", "SISTEMAS DIGITALES I");			 	
		Course.addCourse("CELEX00109", "CELEX00109", "INGLES AVANZADO A");			 	
		/*Course.addCourse("FIEC01552", "FIEC01552", "LENGUAJES DE PROGRAMACIÓN");	*/		
		Course.addCourse("FIEC05553", "FIEC05553", "SISTEMAS DE BASES DE DATOS I");	
		Course.addCourse("FIEC06437", "FIEC06437", "METODOS DE INVESTIGACIÓN APLICADOS A LA COMPUTACIÓN");	
		Course.addCourse("FIEC01800", "FIEC01800", "LABORATORIO DE REDES ELÉCTRICAS");	
		Course.addCourse("CELEX00117", "CELEX00117", "INGLES AVANZADO B");	
		Course.addCourse("FIEC00075", "FIEC00075", "ELECTRONICA I");	
	/*	Course.addCourse("FIEC06437", "FIEC06437", "MÉTODOS DE INVESTIGACIÓN APLICADOS A LA COMPUTACIÓN");	*/
		
		
		/*nuevo 7*/
		
	/*	Course.addCourse("ICM00166", "ICM00166", "ESTADÍSTICA (ING.) (B)");	*/	
		Course.addCourse("FIEC01099", "FIEC01099", "LABORATORIO DE ELECTRÓNICA A");	
		Course.addCourse("FIEC02097", "FIEC02097", "SISTEMAS OPERATIVOS");				
		Course.addCourse("FIEC03046", "FIEC03046", "INGENIERÍA DE SOFTWARE I");				
		Course.addCourse("FIEC05884", "FIEC05884", "DESARROLLO DE APLICACIONES WEB");				 
		Course.addCourse("ICHE02675", "ICHE02675", "INGENIERÍA ECONÓMICA I");				
		Course.addCourse("ICHE03541", "ICHE03541", "EMPRENDIMIENTO E INNOVACIÓN TECNOLÓGICA");				 
			
/*nuevo 8*/
		
		Course.addCourse("FIEC01545", "FIEC01545", "INTERACCIÓN HOMBRE MÁQUINA");				
		Course.addCourse("FIEC03053", "FIEC03053", "INGENIERÍA DE SOFTWARE II");				 
		Course.addCourse("FIEC03459", "FIEC03459", "INTELIGENCIA ARTIFICIAL");
		Course.addCourse("FIEC05561", "FIEC05561", "MICROCONTROLADORES");
		
/*		Course.addCourse("FIEC05561", "FIEC05561", "MICROCONTROLADORES");	*/
		Course.addCourse("LIBREOPCION1", "LIBREOPCION1", "LIBREOPCION1");		
		Course.addCourse("OPTATIVA1", "OPTATIVA1", "OPTATIVA1");
		
		/*nuevo 9*/
		
		Course.addCourse("FIEC05306", "FIEC05306", "FORMULACIÓN Y EVALUACIÓN DE PROYECTOS INFORMÁTICOS");	
		Course.addCourse("FIEC06429", "FIEC06429", "DISEÑO DE SISTEMAS CONTROLADOS POR COMPUTADOR");	
		Course.addCourse("OPTATIVA2", "OPTATIVA2", "OPTATIVA2");		
		Course.addCourse("OPTATIVA3", "OPTATIVA3", "OPTATIVA3");	
		Course.addCourse("OPTATIVA4", "OPTATIVA4", "OPTATIVA4");
		Course.addCourse("OPTATIVA5", "OPTATIVA5", "OPTATIVA5");
		
		
		/*nuevo 10*/
		
		Course.addCourse("LIBREOPCION2", "LIBREOPCION2", "LIBREOPCION2");
		Course.addCourse("LIBREOPCION3", "LIBREOPCION3", "LIBREOPCION3");
		Course.addCourse("LIBREOPCION4", "LIBREOPCION4", "LIBREOPCION4");
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		Random r = new Random();
		ResultSet rs = null;
		ResultSet rsRegla = null;
		int anio = 2016;
		int muestraAlumnos = 120;
		double min = 0.0;
		double max = 1.0;
		double pro1=1.00, pro2=0.44, pro3=0.07;
		//double pro1=1.00, pro2=0.62, pro3=0.2;
		
		String nuid01;
		
		for (int j=0;  j<=muestraAlumnos;  j++ ){	
		int prob=0;
		
		double randomValue = min + (max - min) * r.nextDouble();
		if (randomValue<=pro3)
			prob = 8;
		if (randomValue<=pro2 && randomValue>pro3)
			prob = 7;
		if (randomValue<=pro1 && randomValue>pro2)
			prob = 6;
		System.out.println("random calif: " +randomValue);
		System.out.println("porcentaje asignado: " +prob);
		boolean estadoEstudiante = true;
		
		nuid01 = "10"+ Integer.toString(j);;
		Student.addStudent("Angel", "Fiallos", nuid01, false);
	 
		String ranking = Integer.toString(prob);
		CoursePrediccion estadoAprob;
		String estado;
		//for(int i=0; i<3; i++) {
		int rows=1;
		int rowsRegla=0;
		int itera = 1;
		int pivAleatorio = 0;
		// estadoEstudiante == true
		while (rows > 0 && estadoEstudiante == true){
		//int i = 1;
		//String query = "SELECT * FROM MATERIA_PREDECESORA A  WHERE A.NIVEL <= ? AND A.COD_MATERIA_ACAD NOT IN  (SELECT COURSE_ID FROM enrollment E, STUDENT S where E.status = ? AND E.nuid = S.nuid AND S.nuid  = ?) ORDER BY nivel ";
		//int i = 1		
			System.out.println("iteracion 0: "+itera);
			
		String query = "SELECT * FROM MATERIA_PREDECESORA A  WHERE A.NIVEL <= ?  " +
			" AND A.COD_MATERIA_ACAD NOT IN  (SELECT COURSE_ID FROM enrollment E, STUDENT S " +
			"	where E.status = ? AND E.nuid = S.nuid AND S.nuid  = ?) " +
			"	AND A.COD_MATERIA_PREDEC IN (SELECT  COURSE_ID FROM ENROLLMENT B, STUDENT T " +
			" 	WHERE B.STATUS = ? AND B.NUID = T.NUID AND T.NUID = ?)  " +
			" 	AND A.COD_MATERIA_PREDEC2  IN (SELECT  COURSE_ID FROM ENROLLMENT B, STUDENT T " +
			" 	WHERE B.STATUS = ? AND B.NUID = T.NUID AND T.NUID = ?) " +
			" 	UNION  " +
			" 	SELECT * FROM MATERIA_PREDECESORA A  WHERE A.NIVEL <= ?  " +
			" 	AND A.COD_MATERIA_ACAD NOT IN  (SELECT COURSE_ID FROM enrollment E, STUDENT S " +
			" 	where E.status = ? AND E.nuid = S.nuid AND S.nuid  = ? )  " +
			" 	AND A.COD_MATERIA_PREDEC IN (SELECT  COURSE_ID FROM ENROLLMENT B, STUDENT T " +
			" 	WHERE B.STATUS = ? AND B.NUID = T.NUID AND T.NUID = ? ) " +
			" 	AND A.COD_MATERIA_PREDEC2 IS NULL " +
			" 	UNION " +
			" 	SELECT * FROM MATERIA_PREDECESORA A  WHERE A.NIVEL <= ? " +
			" 	AND A.COD_MATERIA_ACAD NOT IN  (SELECT COURSE_ID FROM enrollment E, STUDENT S " +
			" 	where E.status = ? AND E.nuid = S.nuid AND S.nuid  = ?) "+
			"	AND A.COD_MATERIA_PREDEC IS NULL ";
		System.out.println("query prin: "+query);
		
		PreparedStatement ps =null, psAux = null;
		PreparedStatement ps2 =null, psAuxRegla = null;
		try {
			int termino = 1;
			int level =0;
			String semestre = "";
			ps = conn.prepareStatement(query);
			ps.setInt(1, itera);
			ps.setString(2, "A");
			ps.setString(3, nuid01);
			ps.setString(4, "A");
			ps.setString(5, nuid01);
			ps.setString(6, "A");
			ps.setString(7, nuid01);
			ps.setInt(8, itera);
			ps.setString(9, "A");
			ps.setString(10, nuid01);	
			ps.setString(11, "A");
			ps.setString(12, nuid01);
			ps.setInt(13, 10);	
			ps.setString(14, "A");
			ps.setString(15, nuid01);
			rs = ps.executeQuery();
			//int per = "1";
			System.out.println("iteracion a: "+itera);
			
			if(itera%2==0)
				termino = 2;
			semestre = Integer.toString(termino) ;		
			rs.last();
			rows = rs.getRow();		
			System.out.println("iteracion b: "+itera);
			
			System.out.println("filas: " +rows);
			rs.beforeFirst();
			int tamanio =0;
			//ArrayList<String> list = new ArrayList<String>();
			ArrayList<List<String>> list = new ArrayList<List<String>>();
			
			if (itera >=3){
			String queryRegla = "SELECT * FROM MATERIA_PREDECESORA A  WHERE A.NIVEL <= ?  " +
					" AND A.COD_MATERIA_ACAD NOT IN  (SELECT COURSE_ID FROM enrollment E, STUDENT S " +
					"	where E.status = ? AND E.nuid = S.nuid AND S.nuid  = ?) " +
					"	AND A.COD_MATERIA_PREDEC IN (SELECT  COURSE_ID FROM ENROLLMENT B, STUDENT T " +
					" 	WHERE B.STATUS = ? AND B.NUID = T.NUID AND T.NUID = ?)  " +
					" 	AND A.COD_MATERIA_PREDEC2  IN (SELECT  COURSE_ID FROM ENROLLMENT B, STUDENT T " +
					" 	WHERE B.STATUS = ? AND B.NUID = T.NUID AND T.NUID = ?) " +
					" 	UNION  " +
					" 	SELECT * FROM MATERIA_PREDECESORA A  WHERE A.NIVEL <= ?  " +
					" 	AND A.COD_MATERIA_ACAD NOT IN  (SELECT COURSE_ID FROM enrollment E, STUDENT S " +
					" 	where E.status = ? AND E.nuid = S.nuid AND S.nuid  = ? )  " +
					" 	AND A.COD_MATERIA_PREDEC IN (SELECT  COURSE_ID FROM ENROLLMENT B, STUDENT T " +
					" 	WHERE B.STATUS = ? AND B.NUID = T.NUID AND T.NUID = ? ) " +
					" 	AND A.COD_MATERIA_PREDEC2 IS NULL ";
					System.out.println("query prin: "+queryRegla);
				
				
					ps2 = conn.prepareStatement(queryRegla);
					ps2.setInt(1, itera-2);
					ps2.setString(2, "A");
					ps2.setString(3, nuid01);
					ps2.setString(4, "A");
					ps2.setString(5, nuid01);
					ps2.setString(6, "A");
					ps2.setString(7, nuid01);
					ps2.setInt(8, itera-2);
					ps2.setString(9, "A");
					ps2.setString(10, nuid01);	
					ps2.setString(11, "A");
					ps2.setString(12, nuid01);
					rsRegla = ps2.executeQuery();
					
					rsRegla.last();
					rowsRegla = rsRegla.getRow();		
					System.out.println("iteracion b: "+itera);
					
					System.out.println("filas Reglas: " +rows);
					rsRegla.beforeFirst();
					
					
					}
			
			
			for (int x=0; x<=10; x++)
			{				
				list.add(new ArrayList<String>());

			/*	for (int d=0; d<6; d++){
					list.get(x).add("0");
				}
				*/
			}
			if (rowsRegla==0){ 
			while(rs.next()) {
				//nuids.add(rs.getString("NUID"));
				System.out.println(rs.getString(1));	
				System.out.println(rs.getString(6));
				level =  Integer.valueOf(rs.getString(6));
								
//				list.add(rs.getString(1));
				list.get(level).add(rs.getString(1));
		//		list.get(level).set(index,rs.getString(1));
				//System.out.println(rs.getString(2));
				tamanio++;
				//				estadoAprob = Course.getCourses(rs.getString(1), ranking);
//				estado = estadoAprob.getEstadoAprob();
//				Student.addCourseToStudent(nuid02, rs.getString(1),semestre, Integer.toString(anio), estado);		
			}
			
			}
			else{
				
				while(rsRegla.next()) {
					//nuids.add(rs.getString("NUID"));
					System.out.println(rsRegla.getString(1));	
					System.out.println(rsRegla.getString(6));
					level =  Integer.valueOf(rsRegla.getString(6));
									
//					list.add(rs.getString(1));
					list.get(level).add(rsRegla.getString(1));
			//		list.get(level).set(index,rs.getString(1));
					//System.out.println(rs.getString(2));
					tamanio++;
				}
			}
			double randomDropout = min + (max - min) * r.nextDouble();
			
			//System.out.println("porcentaje Dr: " +probMat);
		 
		
				if (ranking.equals("8"))
				randomDropout =randomDropout+0.95;
				if (ranking.equals("7"))
					randomDropout =randomDropout+0.83;
				if (ranking.equals("6"))
					randomDropout =randomDropout+0.81;
				
				System.out.println("random Dr: " +randomDropout);
				System.out.println("ranking Dr: " +ranking);	
				
				if (randomDropout<=1 && pivAleatorio ==0)
				{
					query = "UPDATE STUDENT SET ESTADO= ? WHERE  nuid = ?";			
					psAux = conn.prepareStatement(query);
					psAux.setString(1, "RA");
					psAux.setString(2, nuid01);
					psAux.executeUpdate();
					//break;
				}
			pivAleatorio =j;	
			estadoAprob = Course.getCourses(list, ranking, nuid01,  semestre, Integer.toString(anio),itera);
			if (estadoAprob.getEstadoAprob() == "RT"){
					
					//query = "UPDATE STUDENT SET ESTADO= ? WHERE  nuid = ?";			
					//psAux = conn.prepareStatement(query);
					//psAux.setString(1, "RT");
					//psAux.setString(2, nuid01);
					//psAux.executeUpdate();
					estadoEstudiante = false;
					//System.out.println("retiro 3 veces: "+nuid01);
					//j--;
			} 			
			itera++;
			if(itera%2 !=0)
				anio++;
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if(rs != null && !rs.isClosed()) {
					rs.close();
				}
				if(rsRegla != null && !rsRegla.isClosed()) {
					rsRegla.close();
				}
				if(ps != null && !ps.isClosed()) {
				ps.close();
				}
				if(ps2 != null && !ps2.isClosed()) {
				ps2.close();
				}
				
			if(psAux != null && !psAux.isClosed()) {
			psAux.close();
			
			}
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}	
		}
		/*
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
		*/
		
		}
		anio = 2016;
		
		}
		
	//	Student.addCourseToStudent(nuid01, "FIEC04341", "I-2016", "A");
	//	Student.addCourseToStudent(nuid01, "CELEX00067", "I-2016","A");

		Student roster[] = Student.getAllStudents();
		for(Student s : roster) {
			System.out.println("Student: "+s);
			System.out.println("Courses: ");
			for(Course c : s.getCourses()) {
				System.out.println("\t" + c);
			}
		}
		


		
	}
	
}
