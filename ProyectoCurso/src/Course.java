  

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

public class Course {

	private static org.apache.log4j.Logger log = Logger.getLogger(Course.class);

	static ArrayList<String> listFinal = new ArrayList<String>();
	private String courseId;
	private String name;
	private String descrption;
	static int numeroMateria =0;
	public Course() {
	}
	
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String string) {
		this.courseId = string;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescrption() {
		return descrption;
	}
	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}
	public String toString() {
		return this.name + " - " + this.descrption;
	}
	
	public static void addCourse(String id, String name, String description) {
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
		//Connection conn2 = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			//conn2 = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		String query = "INSERT INTO course (course_id, name, description) VALUES (?, ?, ?)";
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, description);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
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
				log.error("SQLException: ", e);
				throw new RuntimeException(e);
			}
		}		
	}
	
	
	
	
	public static Double getPesoMateria(int nivelMateria, int nivelActual, String idCurso, String ranking ) 
	{
		double pesoA=0.0, pesoB=0.0, pesoC=0.0, valorPeso = 0.0;
		double min = 0.1;
		double max = 1;
		int contMateria =0;
		Connection connReg = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		int contReg =0;
		int contAnt =0;
		
		String Prioridad ="";
		try {
			connReg = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}	

		 String queryPesoMat = "SELECT COUNT(*), PRIORIDAD, (TOTAL_MENOR/TOTAL_CURSO) AS PESOA,  " +
		 		 "(TOTAL_IGUAL/TOTAL_CURSO) AS PESOB, "+
		 		 "(TOTAL_MAYOR/TOTAL_CURSO) AS PESOC "+
		 		 "FROM MATERIA_BIAS WHERE  "+
		 		 "COD_MATERIA_ACAD = ? AND COD_CARRERA= ? "+
		 		 "AND RANKING = ? ";
		
		 String queryPred = "SELECT COUNT(*)  FROM MATERIA_PREDECESORA  " + 
				 "WHERE ((COD_MATERIA_PREDEC =? )  " + 
				 "OR (COD_MATERIA_PREDEC2=?))";
		
		 String queryAnt = "SELECT COUNT(*)  FROM MATERIA_PREDECESORA  " + 
				 "WHERE COD_MATERIA_ACAD =?   "+
		 		 "AND COD_MATERIA_PREDEC IS NOT NULL	 ";
		 
		 
		 try {
			 
				ps = connReg.prepareStatement(queryPesoMat);
				//unfortunately, JDBC does not support named parameters
				ps.setString(1, idCurso);
				ps.setString(2,"ICC");			
				ps.setString(3, ranking);
				//System.out.println(queryPesoMat);
				rs = ps.executeQuery();
				
				if(rs.next()) {
					contMateria = rs.getInt(1); 
					Prioridad = rs.getString(2);
				//	pesoB = rs.getDouble(4);
				 //	pesoC = rs.getDouble(5);
				}			 
				ps.close();
				ps = connReg.prepareStatement(queryAnt);
				//unfortunately, JDBC does not support named parameters
				ps.setString(1, idCurso);
				rs = ps.executeQuery();
				
				if(rs.next()) {
					
					contAnt = rs.getInt(1);
				}

				
				ps.close();
				ps = connReg.prepareStatement(queryPred);
				//unfortunately, JDBC does not support named parameters
				ps.setString(1, idCurso);			
				ps.setString(2, idCurso);
				rs = ps.executeQuery();
				
				if(rs.next()) {
					
					contReg = rs.getInt(1);
				}

				
		Double bias = Double.parseDouble(ranking)/10;
		Random r = new Random();
		valorPeso = min + (max - min) * r.nextDouble();
		if (contMateria == 0) {Prioridad = "C";}
		
		System.out.println("materia: " +idCurso);
		System.out.println("contflujo: " +contReg);
		System.out.println("nivel materia: " +nivelMateria);
		System.out.println("nivel actual: " +nivelActual);
		System.out.println("prioridad: " +Prioridad);
		
		
		if (nivelMateria< nivelActual && contReg>0 &&contAnt>0  &&  Prioridad.equals("A"))	
			valorPeso = valorPeso +0.8*bias;
		if (nivelMateria< nivelActual && contReg==0  &&contAnt==0  &&  Prioridad.equals("A"))	
			valorPeso = valorPeso + 0.10*bias;
		if (nivelMateria< nivelActual && contReg==0  &&contAnt>0  &&  Prioridad.equals("A"))	
			valorPeso = valorPeso + 0.35*bias;		
		if (nivelMateria< nivelActual  && contReg>0  && contAnt==0 &&  Prioridad.equals("A")){
				System.out.println("entro nivel:" +idCurso);
				valorPeso = valorPeso + 0.50*bias;}

		
		if (nivelMateria== nivelActual && contReg==0 &&contAnt==0 && Prioridad.equals("A"))
			valorPeso = valorPeso + 0.12*bias;
		if (nivelMateria== nivelActual && contReg==0 &&contAnt >0&& Prioridad.equals("A"))
			valorPeso = valorPeso + 0.4*bias;
		if (nivelMateria== nivelActual && contReg>0 &&contAnt>0 && Prioridad.equals("A"))
			valorPeso = valorPeso + 0.7*bias;
		if (nivelMateria== nivelActual && contReg>0 &&contAnt ==0&& Prioridad.equals("A"))
			if (nivelMateria == 1){
			valorPeso = valorPeso + 0.8*bias;}
			else{valorPeso = valorPeso + 0.6*bias;}
		
		
		if (nivelMateria> nivelActual&& contReg>0 && Prioridad.equals("A"))
			valorPeso = valorPeso - 0.1*bias;
		if (nivelMateria> nivelActual&& contReg==0 &&  Prioridad.equals("A"))
			valorPeso = valorPeso - 0.3*bias;

		if (nivelMateria== nivelActual && contReg>0 &&contAnt>0 && Prioridad.equals("B"))
			valorPeso = valorPeso + 0.48*bias;
		if (nivelMateria== nivelActual && contReg>0 &&contAnt==0 && Prioridad.equals("B"))
			valorPeso = valorPeso + 0.33*bias;

		if (nivelMateria< nivelActual && contReg>0 &&contAnt>0 && Prioridad.equals("B"))
			valorPeso = valorPeso + 0.52*bias;
		if (nivelMateria< nivelActual && contReg>0 &&contAnt==0 && Prioridad.equals("B"))
			valorPeso = valorPeso + 0.36*bias;
		if (Prioridad.equals("C")){
			if (nivelActual <=5)
			{
			valorPeso =0.0;	
			}
			else{
			valorPeso = valorPeso - 0.30*bias;
			}
			}
		
		

		System.out.println("valor peso:"+valorPeso);
		//if(contReg>0)
		//	valorPeso = valorPeso+0.1;
		
		
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 finally {
					try {
						if(ps != null && !ps.isClosed())
							ps.close();
						if(connReg != null && !connReg.isClosed())
							connReg.close();
					} catch (SQLException e) {
						System.out.println("SQLException: ");
						e.printStackTrace();
						throw new RuntimeException(e);
					}
			 }
		
		return valorPeso;
	}
	
	
	public static Course getCourseByName(String courseName) {
		
		Course c = new Course();
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			log.error("InstantiationException: ", e);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			log.error("IllegalAccessException: ", e);
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			log.error("ClassNotFoundException: ", e);
			throw new RuntimeException(e);
		}
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}
		

		String query = "SELECT course_id  AS courseId,   " +
					   "       name       AS courseName, " +
		               "       description AS description  " +
		               "FROM course " +
		               "WHERE name = ?";
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			//unfortunately, JDBC does not support named parameters
			ps.setString(1, courseName);
			rs = ps.executeQuery();
			if(rs.next()) {
				c.setCourseId(rs.getString("courseId"));
				c.setName(rs.getString("courseName"));
				c.setDescrption(rs.getString("description"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			log.error("SQLException: ", e);
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
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}
		
		return c;
	}
	
	/**
	 * Removes all courses in the database
	 */
	public static void removeAll() {
		removeAllEnrollmentRecords();
		
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
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}

		String query = "DELETE FROM course";
		
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
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
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}

	}


	
	public static void removeAllEnrollmentRecords() {
		
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
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}
		

		String query = "DELETE FROM enrollment";
		
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}

		try {
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}
	}

    private static void print(List<Record> list, Sort s, Field f, int numeroMateria) {
        RecordComparator rc = new RecordComparator(s, f);
        Collections.sort(list, rc);
        System.out.println("entro lista ");
        int pivote = 1;
        listFinal.clear();
        for (Record r : list) {
            System.out.println(r.getS());
            System.out.println(r.getD());
            if (pivote <= numeroMateria){
            	listFinal.add(r.getS());
            	pivote++;
            }
        }
    }
	public static CoursePrediccion getCourses(ArrayList<List<String>>courseId, String ranking, String nuid, String semester, String anio, int levelActual) {
		
		CoursePrediccion c = new CoursePrediccion();
		boolean existe = false;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			log.error("InstantiationException: ", e);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			log.error("IllegalAccessException: ", e);
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			log.error("ClassNotFoundException: ", e);
			throw new RuntimeException(e);
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		
		

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}
		
		
		/*Connection conn2 = null;
		try {
			conn2 = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		String idCurso ="";
		int nuMat =0;
		int miMat =1;
		int tam = courseId.get(1).size();
		int pivote =0;
		Random arreglo = new Random();
		nuMat =5;
		double peso = 0.0;
		System.out.println("tam: "+tam);
		String course ="";
		List<Record> lista = new ArrayList<Record>(30);

		for (int x=0; x<=10; x++){
			if (courseId.get(x).size()>0){
			for (int j=0; j<courseId.get(x).size(); j++){
			
				
				idCurso = courseId.get(x).get(j);
				peso = getPesoMateria(x, levelActual,idCurso, ranking);
//				System.out.println("nivel: "+x);
//				System.out.println("curso: "+idCurso);
				
//				lista = new ArrayList<Record>(Arrays.asList(new Record(idCurso, String.valueOf(x))));
				lista.add(new Record(idCurso, String.valueOf(peso)));
				
			}
			}
		}
		  
		System.out.println("entra a ordenar ");
		 double pr2,pr3,pr4,pr5,pr6,prm6,prEx=0.00; 
		 String queryAux = "SELECT materias_dos/total dos, " +
		 "materias_tres/total tres, materias_cuatro/total cuatro, " + 
		 "materias_cinco/total cinco, materias_seis/total seis,  " +
		 "materias_mayorseis/total mseis, materias_ex/total mex  " +
		// "FROM csi_extract.tiempos_salida_transversal " +
		 
		  																																																																																																																																																					"FROM csi_extract.tiempos_salida " +
		 "where ranking = ? and parcial = ? ";

		 	int nivelAux=levelActual;
			PreparedStatement ps2 = null;
			ResultSet rs2 = null;	
			double min = 0.0;
			double max = 1.0;
			Random r = new Random();
			System.out.println("Nivel Actual: "+levelActual);
			System.out.println("Nivel ranking: "+ranking);

			try {
				ps2 = conn.prepareStatement(queryAux);
				//unfortunately, JDBC does not support named parameters
				
				ps2.setInt(1,  Integer.parseInt(ranking));
				ps2.setInt(2, nivelAux);
				rs2 = ps2.executeQuery();

				double tot2 = 0,tot3= 0,tot4= 0,tot5= 0,tot6= 0,totm6=0;
				if(rs2.next()) {

					pr2 = rs2.getDouble(1);
					pr3 = rs2.getDouble(2);
					pr4 = rs2.getDouble(3);
					pr5 = rs2.getDouble(4);
					pr6 = rs2.getDouble(5);
					prm6 = rs2.getDouble(6);
					prEx = rs2.getDouble(7);
					System.out.println("pr2 : "+pr2);
					System.out.println("pr3 : "+pr3);
					System.out.println("pr4 : "+pr4);
					System.out.println("pr5 : "+pr5);
					System.out.println("pr6 : "+pr6);
					System.out.println("prm6: "+prm6);
					tot2=pr2;
					tot3=pr2+pr3;
					tot4=pr2+pr3+pr4;
					tot5=pr2+pr3+pr4+pr5;
					tot6=pr2+pr3+pr4+pr5+pr6;
					totm6=pr2+pr3+pr4+pr5+pr6+prm6;
					
				}
				double randomValue = min + (max - min) * r.nextDouble();
				if (randomValue<=tot2)
					numeroMateria = 2;
				if (randomValue<=tot3 && randomValue>tot2)
					numeroMateria = 3;
				if (randomValue<=tot4 && (randomValue>tot3 && randomValue>tot2))
					numeroMateria  = 4;
				if (randomValue<=tot5&& (randomValue>tot4  && randomValue>tot3 && randomValue>tot2))
					numeroMateria  = 5;
				if (randomValue<=tot6 && (randomValue>tot5 && randomValue>tot4  && randomValue>tot3 && randomValue>tot2))
					numeroMateria  = 6;
				if (randomValue<=totm6&& (randomValue>tot6&& randomValue>tot5 && randomValue>tot4  && randomValue>tot3 && randomValue>tot2))
					numeroMateria  = 7;

				if (ranking.equals("8") && (nivelAux>= 9)) 
					numeroMateria  =7;
				if (ranking.equals("7") && (nivelAux>=10)) 
					numeroMateria  =7;	
				if (ranking.equals("6") && (nivelAux>=12)) 
					numeroMateria  =6;
				
				if(nivelAux%2 !=0)
				{
				randomValue = min + (max - min) * r.nextDouble();
				if (ranking.equals("8"))
					randomValue = randomValue -0.99;
				if (ranking.equals("7"))
					randomValue = randomValue -0.93;					
				if (ranking.equals("6"))
					randomValue = randomValue -0.78;		
				System.out.println("estadistica vacaciones: "+prEx);
				System.out.println("random : "+randomValue);
				if (randomValue<=prEx)
				{	System.out.println(" materia adicional nivel: " + nivelAux);
					numeroMateria++;
				}
				}
				
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 finally {
					try {
						if(ps2 != null && !ps2.isClosed())
							ps2.close();
						//if(conn != null && !conn.isClosed())
						//	conn.close();
					} catch (SQLException e) {
						System.out.println("SQLException: ");
						e.printStackTrace();
						throw new RuntimeException(e);
					}
			 }
			System.out.println("numero materias: "+numeroMateria);
			print(lista, Sort.DESCENDING, Field.D, numeroMateria);


		for (int j=0;  j<listFinal.size();  j++ ){
	    existe = false;
	    String query = "SELECT IFNULL(AVG(ranking_pro1),1), IFNULL(AVG(ranking_pro2),1), IFNULL(AVG(ranking_pro3),1)    " +
					   "FROM MATERIA_RANKINGS " +
		               "where COD_CARRERA = ? " +
		//               "AND COD_ESPECIALIZACION = ? AND COD_MATERIA_ACAD=? ";
						"AND COD_MATERIA_ACAD=? ";
	    		   
	
/*	    String query = "SELECT ranking_pro1, ranking_pro2, ranking_pro3    " +
				   "FROM MATERIA_RANKINGS " +
	               "where COD_CARRERA = ? " +
	               "AND COD_ESPECIALIZACION = ? AND COD_MATERIA_ACAD=? ";
	*/	
	    double proMat1=0, proMat2=0, proMat3=0, probMat=0;
		double minimo = 0.1;
		double maximo = 1;
		
		double randomValue = 0;
		try {
			ps = conn.prepareStatement(query);
			//unfortunately, JDBC does not support named parameters
			ps.setString(1, "ICC");
			//ps.setString(2, "ST");
			ps.setString(2, (String) listFinal.get(j));
			System.out.println("Materia: " + (String) listFinal.get(j));
			
			rs = ps.executeQuery();
			
			if(rs.next()) {

				proMat1 = rs.getDouble(1);
				proMat2 = rs.getDouble(2);
				proMat3 = rs.getDouble(3);
				existe = true;
			}
			
			if (existe == false){
				proMat1=1.0;
				proMat2=1.0;
				proMat3=1.0;
				
			}
		/*
			if (proMat1<=0)
				proMat1 = 1;

			if (proMat2<=0)
				proMat1 = 1;
			
			if (proMat3<=0)
				proMat1 = 1;
			*/
				Random rProb = new Random();
				
				if (ranking.equals("6"))
					probMat=proMat1;
				if (ranking.equals("7"))
					probMat=proMat2;
				//double ran = r.nextDouble();
				if (ranking.equals("8"))
					probMat=proMat3;
				
	
				int contVeces = 0;
				int contVezTomada =0;
				
				//dropout por otros motivo

				
				/**tercera vez*///
				 String queryConVeces = "SELECT COUNT(*) FROM ENROLLMENT WHERE NUID= ? AND COURSE_ID = ? AND STATUS = ? ";
				
						ps = conn.prepareStatement(queryConVeces);
						//unfortunately, JDBC does not support named parameters
						ps.setString(1, nuid);
						ps.setString(2, listFinal.get(j));
						ps.setString(3, "R");
						rs = ps.executeQuery();
						
						if(rs.next()) {

							contVeces = rs.getInt(1);
						
						}
						 String queryConVezTomada = "SELECT COUNT(*) FROM ENROLLMENT WHERE NUID= ? AND COURSE_ID = ?  ";
							
							ps = conn.prepareStatement(queryConVezTomada);
							//unfortunately, JDBC does not support named parameters
							ps.setString(1, nuid);
							ps.setString(2, listFinal.get(j));
							rs = ps.executeQuery();
							
							if(rs.next()) {

								contVezTomada = rs.getInt(1);
								contVezTomada ++;
							
							}
						
						System.out.println("Materia : " + (String) listFinal.get(j)+ "veces: "+contVeces );
						
						double randomPromedio = min + (max - min) * r.nextDouble();
					
						System.out.println("porcentaje : " +probMat);
						System.out.println("random : " +randomPromedio);
						System.out.println("ranking : " +ranking);			 
						if (contVeces ==1 ){
							if (ranking.equals("8"))
							randomPromedio =randomPromedio-0.42;
							if (ranking.equals("7"))
							//randomPromedio =randomPromedio-0.38;
							randomPromedio =randomPromedio-0.36;
							if (ranking.equals("6"))
							//randomPromedio =randomPromedio-0.29;
							randomPromedio =randomPromedio-0.21;
							}

						if (contVeces ==2 )
						{
							if (ranking.equals("8"))
							randomPromedio =randomPromedio-0.75;
							if (ranking.equals("7"))
							//randomPromedio =randomPromedio-0.38;
							//	randomPromedio =randomPromedio-0.36;
								randomPromedio =randomPromedio-0.33;
							if (ranking.equals("6"))
	//						randomPromedio =randomPromedio-0.35	;
							//randomPromedio =randomPromedio-0.27	;
						    randomPromedio =randomPromedio-0.25	;
						}
						
						/*	if (contVeces ==1 )
						{
							if (ranking.equals("8"))
							randomPromedio =randomPromedio-0.75;
							if (ranking.equals("7"))
							randomPromedio =randomPromedio-0.38;
							if (ranking.equals("6"))
							randomPromedio =randomPromedio-0.35	;
							}*/
				if (randomPromedio > probMat){
					Student.addCourseToStudent(nuid, (String) listFinal.get(j), semester, anio, "R", ranking, contVezTomada);		
				//if (contVeces ==1 )
					if (contVeces ==2 )
					{	
						query = "UPDATE STUDENT SET ESTADO= ? WHERE  nuid = ?";			
					ps = conn.prepareStatement(query);
					ps.setString(1, "RT");
					ps.setString(2, nuid);
					ps.executeUpdate();
					System.out.println("retiro 3 veces: "+nuid);
					c.setEstadoAprob("RT");
					
					//break;
					}
				/*	c.setEstadoAprob("RT");
					else
					c.setEstadoAprob("R");
				*/	
				}
					else
				{
					System.out.println("entra aprob mat: " +(String) listFinal.get(j));
					Student.addCourseToStudent(nuid,  (String) listFinal.get(j), semester, anio, "A", ranking, contVezTomada);		
					c.setEstadoAprob("A");
					
				}
					
		} catch (SQLException e) {
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}
	
		
		

		}
		try {
			if (existe == true)
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (existe == true)
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (existe == true)
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	  return c;
	  
	}	
	
}
