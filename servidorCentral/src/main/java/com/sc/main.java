//package com.sc;
//
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.List;
//
//import javax.persistence.Persistence;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
//
//import com.sc.datatypes.dataUsuario;
//import com.sc.datatypes.dataActividad;
//import com.sc.datatypes.dataPaquete;
//import com.sc.datatypes.dataSalida;
//import com.sc.excepciones.ParametrosInvalidosExcepcion;
//import com.sc.excepciones.UsuarioNoExisteExcepcion;
//import com.sc.excepciones.UsuarioYaExisteExcepcion;
//import com.sc.interfaces.IActividadController;
//import com.sc.interfaces.IDepartamentoController;
//import com.sc.interfaces.IPaqueteController;
//import com.sc.interfaces.ISalidaController;
//import com.sc.interfaces.IUsuarioController;
//import com.sc.manejadores.ActividadManejador;
//import com.sc.manejadores.DepartamentoManejador;
//import com.sc.manejadores.PaqueteManejador;
//import com.sc.manejadores.SalidaManejador;
//import com.sc.manejadores.UsuarioManejador;
//import com.sc.utilidades.log;
//import com.sc.entidades.departamento;
//import com.sc.entidades.proveedor;
//import com.sc.entidades.turista;
//import com.sc.entidades.actividad;
//import com.sc.entidades.salida;
//
//public class main {
//    public static void main(String[] args) {
//        /*UsuarioManejador zum = UsuarioManejador.getinstance();
//        ActividadManejador zam = ActividadManejador.getinstance();
//        DepartamentoManejador zdm = DepartamentoManejador.getinstance();
//        PaqueteManejador zpm = PaqueteManejador.getinstance();
//
//        log.info(".......................");
//        log.info("Hola");
//*/
//        Fabrica fabrica = Fabrica.getInstance();
//        IDepartamentoController IDC = fabrica.getIDepartamentoController();
//        IActividadController IAC = fabrica.getIActividadController();
//        IUsuarioController IUC = fabrica.getIUsuarioController();
//        IPaqueteController IPC = fabrica.getIPaqueteController();
//        ISalidaController ISC = fabrica.getISalidaController();
//
//
//
//        try {
//        	IDC.crearDepartamento("San Jose", "San jose el mejor depto", "www.sanjose.com"); 
//         	IDC.crearDepartamento("Montevideo", "Monte el mejor depto", "www.Monte.com");
//        	} catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion e) {
//             log.error(e.toString());
//		  }
//        
//         try {
//		  	IUC.registrarProveedor("nickname1", "Juan", "Gomez", "juan@gmail.com", "Proveedor de productos electrónicos", "www.juangomez.com", LocalDate.of(1985, 8, 17));
//		  } catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion e) {
//             log.error(e.toString());
//		  }
//
//         try {
//		 	IAC.crearActividad("San Jose", "nickname1","Pasear","hacer",30,100,"San Jose", LocalDate.of(2020,05,15));
//		 } catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion | UsuarioNoExisteExcepcion e) {
//		 	log.error(e.toString());
//		 }
//        
//         try {
//		 	IAC.crearActividad("Montevideo", "nickname2","Correr","hacer",30,100,"Monte", LocalDate.of(2020,05,15));
//		 } catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion | UsuarioNoExisteExcepcion e) {
//		 	log.error(e.toString());
//		 }
//        
//    	
//        try {
//            ISC.crearSalida("sali", 1, LocalDate.of(2020,05,15), LocalDate.of(2026,05,15), "parque ndel plata", "Pasear");
//            ISC.crearSalida("ir a comer", 2, LocalDate.of(2022,02,16), LocalDate.of(2027,06,10), "parque de pepe", "Correr");
//            ISC.crearSalida("ir a la playa", 3, LocalDate.of(2020,02,16), LocalDate.of(2024,06,10), "playa de kiyu", "Correr");
//            ISC.crearSalida("ir a parador", 2,  LocalDate.of(2020,05,15), LocalDate.of(2026,05,15), "paala","Pasear");
//            ISC.crearSalida("ir a jugar", 1,  LocalDate.of(2019,05,15), LocalDate.of(2022,05,15), "peteco", "Pasear");
//        } catch (UsuarioYaExisteExcepcion e){
//            // TODO Auto-generated catch block
//            log.error(e.toString());
//        }
//        
//        IPC.crearPaquete("Paquete2", "Desc2", 5, 10, LocalDate.now());
//
//        IPC.agregarActividadPaquete("Paquete2", "Pasear");
//        
//        List<dataActividad>LDtActividad = IAC.getAllActividadesDepartamento("San Jose");
//        
//        for(dataActividad DtAct : LDtActividad){
//        	System.out.println("Nombre de Actividad: " + DtAct.getNombre());
//        	List<dataSalida> DtSalidas = DtAct.getDtSalidas();
//        	List<dataPaquete> DtPaquetes = DtAct.getDtPaquetes();
//        	for(dataSalida DtSalida : DtSalidas) {
//        		System.out.println("            Nombre de la salida: "+DtSalida.getNombre());
//        	}
//        	for(dataPaquete DtPaquete : DtPaquetes) {
//        		System.out.println("            Nombre del paquete: "+DtPaquete.getNombre());
//        	}
//        }
//        
//        //DepartamentoManejador dm = DepartamentoManejador.getinstance();
//
//        // List<departamento> departamentos = dm.getAllDepartamento();
//
//        // for (departamento departamento : departamentos) {
//        //     log.warning(departamento.getNombre() + " Actividades: " + departamento.getActividades().size());
//        // }
//
//        // List<dataActividad> actividades = IAC.getActividadesNoPaquete("San José", "Paquete2");
//        // for (dataActividad actividad : actividades) {
//        //     log.warning(actividad.getNombre());
//        // }
//
//
//        // EntityManagerFactory factory = EMFactory.getEntityManagerFactory();
//
//        // // Para cada función hay que crear un nuevo em y tx.
//	    // EntityManager em = factory.createEntityManager();
//
//        // // List<departamento> departamentos = em.createQuery("SELECT d from departamento d LEFT JOIN FETCH d.actividades", departamento.class).getResultList();
//        // salida salida = em.createQuery("SELECT s from salida s WHERE s.nombreS = :nombre", salida.class)
//        //             .setParameter("nombre", "Salida")
//        //             .getSingleResult();
//
//        // if (salida != null) {
//        //     log.info(salida.getNombre());
//        // } else {
//        //     log.info("Salida es null");
//        // }
//
//
//
//
//
//
//
//
//    }
//}
//    	/*
//    	actividad act = new actividad("Paseo", "Paso muito lindo", 2, 100, "San José de Mayo", LocalDate.of(2023, 12, 12));
//    	salida sal = new salida("sali", 1, LocalDate.of(2020,05,15), LocalDate.of(2026,05,15), "parque ndel plata");
//    	sal.addActividad(act);
//    	act.addSalida(sal);
//    	
//    	
//    	EntityManagerFactory factory = Persistence.createEntityManagerFactory("PA2023");
//    	EntityManager em = factory.createEntityManager();
//    	em.getTransaction().begin();
//    	em.persist(act);
//    	em.persist(sal);
//    	em.getTransaction().commit();
//    	*/
//    	
//
//
//
//
//        // UsuarioManejador um = UsuarioManejador.getinstance();
//        // DepartamentoManejador dm = DepartamentoManejador.getinstance();
//        // ActividadManejador am = ActividadManejador.getinstance();
//        // SalidaManejador sm = SalidaManejador.getInstance();
//        
//		// Fabrica fabrica = Fabrica.getInstance();
//        
//		// IUsuarioController IUC = fabrica.getIUsuarioController();
//		// IDepartamentoController IDC = fabrica.getIDepartamentoController();
//		// IActividadController IAC = fabrica.getIActividadController();
//		// ISalidaController ISC = fabrica.getISalidaController();
//		
//		
//        // try {
//        // 	IDC.crearDepartamento("San Jose", "San jose el mejor depto", "www.sanjose.com"); 
//        // 	IDC.crearDepartamento("Montevideo", "Monte el mejor depto", "www.Monte.com");
//		//  } catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion e) {
//        //     log.error(e.toString());
//		//  }
//        
//        // try {
//		//  	IUC.registrarProveedor("nickname1", "Juan", "Gomez", "juan@gmail.com", "Proveedor de productos electrónicos", "www.juangomez.com", LocalDate.of(1985, 8, 17));
//		//  } catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion e) {
//        //     log.error(e.toString());
//		//  }
//
//        // try {
//		// 	IAC.crearActividad("San Jose", "nickname1","Pasear","hacer",30,100,"San Jose", LocalDate.of(2020,05,15));
//		// } catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion | UsuarioNoExisteExcepcion e) {
//		// 	log.error(e.toString());
//		// }
//        
//        // try {
//		// 	IAC.crearActividad("Montevideo", "nickname2","Correr","hacer",30,100,"Monte", LocalDate.of(2020,05,15));
//		// } catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion | UsuarioNoExisteExcepcion e) {
//		// 	log.error(e.toString());
//		// }
//        
//    	
//        // try {
//        //     ISC.crearSalida("sali", 1, LocalDate.of(2020,05,15), LocalDate.of(2026,05,15), "parque ndel plata", "Pasear");
//        //     ISC.crearSalida("ir a comer", 2, LocalDate.of(2022,02,16), LocalDate.of(2027,06,10), "parque de pepe", "Correr");
//        //     ISC.crearSalida("ir a la playa", 3, LocalDate.of(2020,02,16), LocalDate.of(2024,06,10), "playa de kiyu", "Correr");
//        //     ISC.crearSalida("ir a parador", 2,  LocalDate.of(2020,05,15), LocalDate.of(2026,05,15), "paala","Pasear");
//        //     ISC.crearSalida("ir a jugar", 1,  LocalDate.of(2019,05,15), LocalDate.of(2022,05,15), "peteco", "Pasear");
//        // } catch (UsuarioYaExisteExcepcion e){
//        //     // TODO Auto-generated catch block
//        //     log.error(e.toString());
//        // }
//        
//        // dataSalida DtSalida = ISC.mostrarDatosSalida("sali");
//        // System.out.println(DtSalida.getNombre());
//        
////         System.out.println("asdasd");
//        
//        
//       
////         List<dataActividad> actividades = IAC.getAllActividadesDepartamento("San Jose");
//// if (actividades == null) {
////     log.msg("null actividades");
//// } else {
////     for (dataActividad actividad : actividades) {
////         log.msg(actividad.getNombre());
////     }
//// }
//
//// List<String> nombresSalidas = IAC.getNombresSalidasAsociadas("Pasear");
//
//// if (nombresSalidas == null) {
////     // Manejo del caso en que la actividad no se encuentra
////     log.msg("La actividad no se encontró o es nula.");
//// } else {
////     // Imprime cada nombre de salida devuelto por la función
////     for (String nombreSalida : nombresSalidas) {
////         dataSalida DtSalidaTest = ISC.mostrarDatosSalida(nombreSalida);
//
//// if (DtSalidaTest != null) {
////     // Muestra los campos del DtSalida
////     log.msg("Nombre de Salida: " + DtSalidaTest.getNombre());
////     log.msg("Capacidad: " + DtSalidaTest.getCapacidad());
////     log.msg("Fecha de Alta: " + DtSalidaTest.getFechaAlta());
////     log.msg("Fecha de Salida: " + DtSalidaTest.getFechaSalida());
////     log.msg("Lugar de Salida: " + DtSalidaTest.getLugarSalida());
//
//// } else {
////     log.msg("La salida no se encontró o es nula.");
//// }
////         log.msg("Nombre de salida: " + nombreSalida);
////     }
//// }
//  
////  System.out.println("asdasd");
//        
//
//    	
////     }
//// }
//        /*UsuarioManejador zum = UsuarioManejador.getinstance();
//        DepartamentoManejador zdm = DepartamentoManejador.getinstance();
//        ActividadManejador zam = ActividadManejador.getinstance();
//
//		Fabrica fabrica = Fabrica.getInstance();
//
//        // proveedor p1 = new proveedor("dargon227", "ezequiel", "blandin", "ezequiel@utec.edu.uy", "asndojasndojansd", "wwww.sanjose.com", LocalDate.of(2000,12,05));
//        // log.warning("Proveedor: " + p1.getNickname() + " " + p1.getNacimiento().toString());
//        // // log.warning("Se crea P1");
//        UsuarioManejador um = UsuarioManejador.getinstance();
//        // // um.addUsuario(p1);
//
//        DepartamentoManejador dm = DepartamentoManejador.getinstance();
//        // // departamento d1 = new departamento("San Jose", "San jose el mejor depto", "www.sanjose.com");
//        // // dm.addDepartamento(d1);
//
//        
//        ActividadManejador am = ActividadManejador.getinstance();
//        actividad a1 = new actividad("Paseo", "Paso muito lindo", 2, 100, "San José de Mayo", LocalDate.of(2023, 12, 12));
//        am.addActividad(a1);
//        proveedor p11 = (proveedor) um.getUsuario("dargon227");
//
//        departamento d11 = dm.getDepartamento("San Jose");
//
////        log.warning("Departamento: " + d11.getNombre() + " tiene: " + d11.getActividades().size() + " actividades dentro");
////        for (actividad act : d11.getActividades()){
////            log.warning("[actividad] " + act.getNombre());
////        }
//
//        // if (p11 == null) {
//        //     log.warning("Proveedor es null");
//        // } else {
//        //     log.warning("No es nulo");
//        //     a1.setDepartamento(d11);
//        //     d11.agregarActividad(a1);
//        //     a1.setProveedor(p11);
//        //     am.addActividad(a1);
//        //     dm.updateDepartamento(d11);
//        // }
//        // log.warning("tirin");
//        
//        // am.addActividad(a1);
//
//        // d1.agregarActividad(a1);
//        
//        // p1.agregarActividad(a1);
//        
//        // log.info(p1.getAllActividades().size() + " Actividades dentro de: " + p1.getNickname());
//        // log.info(d1.getActividades().size() + " Actividades dentro de: " + d1.getNombre());
//        
//        // // dm.addDepartamento(d1);
//        
//        // // am.addActividad(a1);
//        
//        
//        // dm.updateDepartamento(d1);
//        // um.updateUsuario(p1);
//        
//        // actividad a2 = new actividad("Fumememe", "Fumarseaosldas", 3, 120, "San José de Mayo", LocalDate.of(2023, 12, 12));
//        // a2.setProveedor(p11);
//        // a2.setDepartamento(d11);
//        // d11.agregarActividad(a2);
//        // am.addActividad(a2);
//        // dm.updateDepartamento(d11);
//
//
//
//        // departamento d11 = dm.getDepartamento("San Jose");
//        // log.warning(d11.getNombre());
//        // d11.agregarActividad(a2);
//
//        // dm.updateDepartamento(d11);
//
//
//
//
//
//
//
//
//
//
//
//
//
//		 IUsuarioController IUC = fabrica.getIUsuarioController();
//		IDepartamentoController IDC = fabrica.getIDepartamentoController();
//		IActividadController IAC = fabrica.getIActividadController();
//		ISalidaController ISC = fabrica.getISalidaController();
//
//        // try {
//        //     IUC.registrarTurista("dargon227", "Ezequiel", "Blandin", "exzeblan@gmail.com", "Uruguayan", LocalDate.of(2000, 05, 12));
//        // } catch (Exception e) {
//        //     log.error(e.toString());
//        // }
//
//         try {
//		 	IUC.registrarProveedor("nickname1", "Juan", "Gomez", "juan@gmail.com", "Proveedor de productos electrónicos", "www.juangomez.com", LocalDate.of(1985, 8, 17));
//		 } catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion e) {
//             log.error(e.toString());
//		 }
//        
//        IDC.crearDepartamento("San Jose", "Muito bonito", "www.sanjose.com.uy");
//        IDC.crearDepartamento("Montevideo", "golo", "www.montevideo.com.uy");
//        
//        try {
//			IAC.crearActividad("San Jose", "nickname1","Pasear","hacer un pete",30,100,"San Jose", LocalDate.of(2020,05,15));
//		} catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion | UsuarioNoExisteExcepcion e) {
//			log.error(e.toString());
//		}
//        
//     
//        List<dataActividad> actividadesSanJose = IAC.getAllActividadesDepartamento("San Jose");
//
//        // Imprimir en el log el resultado
//        for (dataActividad actividad : actividadesSanJose) {
//            log.info("Actividad en San Jose: " + actividad.getNombre());
//        }
//       
//        //ISC.eliminarSalida("sali");
//        
//        try {
//            ISC.crearSalida("sali", 1, LocalDate.of(2020,05,15), LocalDate.of(2026,05,15), "parque ndel plata", "Pasear");
//        } catch (UsuarioYaExisteExcepcion e) {
//            // TODO Auto-generated catch block
//            log.error(e.toString());
//        }
//        
//        
//        //IDC.crearDepartamento("asdawd", "awdawdaw", "www.awdasdad.com.uy");
//        
//
////          try {
////			IAC.crearActividad("Jan Sose", "Anickname1","Pasear","hacer un pete",130,100,"San Jose", LocalDate.of(2020,05,15));
////		} catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion | UsuarioNoExisteExcepcion e) {
////			log.error(e.toString());
////		}
//
//        // log.warning("asdasds");
//
//        // dataActividad DTA = IAC.mostrarDatos("Pasear");
//        // System.out.println(DTA.getDesc());
//        // try {
//		// 	IAC.modificarActividad("Pasear", "por la playa", 100, 500, "San Jose", LocalDate.of(2022, 10, 10));
//		// } catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion | UsuarioNoExisteExcepcion e) {
//		// 	e.printStackTrace();
//		// }
//        //DTA = IAC.mostrarDatos("Pasear");
//        //System.out.println(DTA.getDesc());
//        //System.out.println(DTA.getNombre());
//        
//        
//
//        // IUC.modificarUsuario("Dargon227", "Jorge", "Blandini", LocalDate.of(2000,05,15));
//        // IUC.modificarUsuario("Dargon227", "Nico", "Escobi", LocalDate.of(2005,12,25));
//        
//        //IUC.registrarTurista("Nico", "Nicolas", "Blandin", "exzeblan@gmail.com", "Uruguayan", LocalDate.of(2000, 05, 12));
//		// IUC.registrarTurista("junker8", "Nicolas", "Blandin", "exzeblan@gmail.com", "Uruguayan", LocalDate.of(2000, 05, 12));
//        // IUC.registrarTurista("jorge123", "Jorge", "Rodriguez", "jorge123@gmail.com", "Uruguay", LocalDate.of(2000, 05, 20));
//        // IUC.registrarTurista("alice02", "Alice", "Johnson", "aaalice01@gmail.com", "American", LocalDate.of(1995, 11, 8));
//        // IUC.registrarTurista("michael88", "Michael", "Smith", "michael88@gmail.com", "British", LocalDate.of(1988, 3, 15));
//        // IUC.registrarTurista("laura789", "Laura", "Garcia", "laura789@gmail.com", "Spanish", LocalDate.of(1992, 7, 23));
//        // IUC.registrarTurista("emily22", "Emily", "Anderson", "emily22@gmail.com", "Canadian", LocalDate.of(1997, 9, 4));
//        // IUC.registrarTurista("carlos456", "Carlos", "Martinez", "carlos456@gmail.com", "Mexican", LocalDate.of(1990, 12, 30));
//        // IUC.registrarTurista("anna007", "Anna", "Williams", "anna007@gmail.com", "Australian", LocalDate.of(1993, 2, 18));
//        // IUC.registrarTurista("david777", "David", "Lee", "david777@gmail.com", "Korean", LocalDate.of(1998, 6, 10));
//        // IUC.registrarTurista("maria555", "Maria", "Lopez", "maria555@gmail.com", "Argentinian", LocalDate.of(1991, 10, 25));
//        // IUC.registrarTurista("robert222", "Robert", "Brown", "robert222@gmail.com", "American", LocalDate.of(1985, 4, 5));
//        // IUC.registrarTurista("linda444", "Linda", "Davis", "linda444@gmail.com", "Canadian", LocalDate.of(1987, 8, 14));
//        // IUC.registrarTurista("pablo666", "Pablo", "Gonzalez", "pablo666@gmail.com", "Mexican", LocalDate.of(1994, 1, 3));
//        // IUC.registrarTurista("emily999", "Emily", "Harris", "emily999@gmail.com", "British", LocalDate.of(1989, 7, 28));
//        // IUC.registrarTurista("alex333", "Alex", "Moore", "alex333@gmail.com", "Australian", LocalDate.of(1996, 12, 9));
//        // IUC.registrarTurista("juan111", "Juan", "Perez", "juan111@gmail.com", "Spanish", LocalDate.of(1999, 5, 17));
//        // IUC.registrarTurista("lucas888", "Lucas", "Taylor", "lucas888@gmail.com", "Australian", LocalDate.of(1986, 2, 22));
//        // IUC.registrarTurista("mariana777", "Mariana", "Rodriguez", "mariana777@gmail.com", "Mexican", LocalDate.of(1992, 11, 6));
//        // IUC.registrarTurista("olivia555", "Olivia", "Wilson", "olivia555@gmail.com", "American", LocalDate.of(1984, 9, 1));
//        // IUC.registrarTurista("carolina444", "Carolina", "Garcia", "carolina444@gmail.com", "Spanish", LocalDate.of(1987, 4, 19));
//        // IUC.registrarTurista("andres222", "Andres", "Hernandez", "andres222@gmail.com", "Colombian", LocalDate.of(1993, 8, 12));
//        // IUC.registrarTurista("sofia999", "Sofia", "Lopez", "sofia999@gmail.com", "Argentinian", LocalDate.of(1998, 3, 30));
//        // IUC.registrarTurista("gabriel666", "Gabriel", "Silva", "gabriel666@gmail.com", "Brazilian", LocalDate.of(1997, 6, 27));
//        // IUC.registrarTurista("ana111", "Ana", "Fernandez", "ana111@gmail.com", "Spanish", LocalDate.of(1995, 11, 14));
//        // IUC.registrarTurista("kevin888", "Kevin", "Miller", "kevin888@gmail.com", "Canadian", LocalDate.of(1990, 7, 5));
//        // IUC.registrarTurista("luis777", "Luis", "Perez", "luis777@gmail.com", "Mexican", LocalDate.of(1994, 12, 9));
//        // IUC.registrarTurista("clara555", "Clara", "Jones", "clara555@gmail.com", "American", LocalDate.of(1988, 1, 22));
//        // IUC.registrarTurista("daniel444", "Daniel", "Brown", "daniel444@gmail.com", "British", LocalDate.of(1983, 5, 3));
//        // IUC.registrarTurista("valentina222", "Valentina", "Garcia", "valentina222@gmail.com", "Colombian", LocalDate.of(1996, 9, 16));
//        // IUC.registrarTurista("martin999", "Martin", "Williams", "martin999@gmail.com", "Australian", LocalDate.of(1991, 3, 8));
//		// IUC.registrarTurista("juan777", "Juan", "Lopez", "juan777@gmail.com", "Spanish", LocalDate.of(1982, 4, 18));
//		// IUC.registrarTurista("juan777", "Juan", "Lopez", "juan777@gmail.com", "Spanish", LocalDate.of(1982, 4, 18));
//		// IUC.registrarTurista("juan777", "Juan", "Lopez", "juan777@gmail.com", "Spanish", LocalDate.of(1982, 4, 18));
//        // IUC.registrarTurista("sara666", "Sara", "Smith", "sara666@gmail.com", "Canadian", LocalDate.of(1987, 8, 27));
//        // try {
//		// 	IUC.registrarProveedor("sarasola", "Ana Sara", "Rodriguez", "ana@gmail.com", "Distribuidora de ropa de moda", "www.anasara.com", LocalDate.of(1990, 3, 25));
//		// } catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion e) {
//		// 	// TODO Auto-generated catch block
//		// 	e.printStackTrace();
//		// }
//        // List<dataActividad> Luser = IAC.getAllActividades();
//        // for(dataActividad DtAct: Luser) {
//        // 	System.out.println(DtAct.getNombre());
//        // }
//        // IUC.registrarProveedor("acostaMuebles", "María", "Acosta", "maria.acosta@muebles.com", "Fabricante de muebles artesanales", "www.acostamuebles.com", LocalDate.of(1975, 12, 5));
//        // IUC.registrarProveedor("tecnoMax", "Luis", "Martínez", "luis@tecnomax.com", "Especialistas en electrónica y tecnología", "www.tecnomax.com", LocalDate.of(2002, 6, 10));
//        // IUC.registrarProveedor("gourmetDelight", "Elena", "Pérez", "elena@gourmetdelight.com", "Importadora de productos gourmet internacionales", "www.gourmetdelight.com", LocalDate.of(1998, 9, 21));
//        // IUC.registrarProveedor("decorArte", "Roberto", "Fernández", "roberto@decorarte.com", "Tienda de decoración y arte para el hogar", "www.decorarte.com", LocalDate.of(1982, 2, 8));
//        // IUC.registrarProveedor("ropaSport", "Lucía", "Hernández", "lucia.ropa@sport.com", "Especialistas en ropa deportiva y accesorios", "www.ropasport.com", LocalDate.of(1995, 7, 14));
//        // IUC.registrarProveedor("beautyZone", "Carolina", "Vargas", "caro@beautyzone.com", "Productos de belleza y cuidado personal", "www.beautyzone.com", LocalDate.of(2005, 4, 30));
//        // IUC.registrarProveedor("naturalHealth", "Diego", "López", "diego@naturalhealth.com", "Productos naturales y suplementos alimenticios", "www.naturalhealth.com", LocalDate.of(1993, 1, 12));
//        // IUC.registrarProveedor("luxuryWatches", "Julia", "Pérez", "julia@luxurywatches.com", "Relojes de lujo de marcas internacionales", "www.luxurywatches.com", LocalDate.of(1978, 11, 7));
//        // IUC.registrarProveedor("urbanStyle", "Carlos", "Montes", "carlos@urbanstyle.com", "Ropa urbana y moderna para jóvenes", "www.urbanstyle.com", LocalDate.of(2000, 9, 18));
//        // IUC.registrarProveedor("greenPlanet", "María", "Fuentes", "maria@greenplanet.com", "Productos ecológicos y sustentables", "www.greenplanet.com", LocalDate.of(1997, 5, 3));
//        // IUC.registrarProveedor("techHub", "Andrés", "Hernández", "andres@techhub.com", "Centro de innovación y tecnología", "www.techhub.com", LocalDate.of(1988, 12, 15));
//        // IUC.registrarProveedor("coffeeWorld", "Laura", "Martínez", "laura@coffeeworld.com", "Café de calidad y accesorios para amantes del café", "www.coffeeworld.com", LocalDate.of(1991, 10, 9));
//        // IUC.registrarProveedor("bookHaven", "Pedro", "Gómez", "pedro@bookhaven.com", "Librería con una amplia selección de libros", "www.bookhaven.com", LocalDate.of(1980, 4, 26));
//        // IUC.registrarProveedor("fashionista", "Isabela", "Silva", "isabela@fashionista.com", "Blog de moda y estilo personal", "www.fashionista.com", LocalDate.of(1994, 11, 2));
//        // IUC.registrarProveedor("petParadise", "Fernando", "Chávez", "fernando@petparadise.com", "Productos y servicios para mascotas", "www.petparadise.com", LocalDate.of(2001, 7, 22));
//        // IUC.registrarProveedor("organicEats", "Sofía", "Ortega", "sofia@organiceats.com", "Restaurante con enfoque en alimentos orgánicos", "www.organiceats.com", LocalDate.of(1999, 3, 7));
//        // IUC.registrarProveedor("fitnessFusion", "Alejandro", "Luna", "alejandro@fitnessfusion.com", "Gimnasio y centro de acondicionamiento físico", "www.fitnessfusion.com", LocalDate.of(1992, 6, 28));
//        // IUC.registrarProveedor("travelDreams", "Valeria", "Ramírez", "valeria@traveldreams.com", "Agencia de viajes para destinos exóticos", "www.traveldreams.com", LocalDate.of(1987, 1, 11));
//        // IUC.registrarProveedor("artGallery", "Gustavo", "Díaz", "gustavo@artgallery.com", "Galería de arte contemporáneo", "www.artgallery.com", LocalDate.of(1984, 9, 9));
//        // IUC.registrarProveedor("homeSolutions", "Mariana", "Perez", "mariana@homesolutions.com", "Soluciones para el hogar y decoración", "www.homesolutions.com", LocalDate.of(2004, 12, 3));
//        // IUC.registrarProveedor("techGadgets", "Manuel", "Ríos", "manuel@techgadgets.com", "Dispositivos tecnológicos y accesorios", "www.techgadgets.com", LocalDate.of(1996, 5, 15));
//        // IUC.registrarProveedor("urbanExplore", "Renata", "Martínez", "renata@urbanexplore.com", "Explora la ciudad con nuestras rutas turísticas", "www.urbanexplore.com", LocalDate.of(1992, 2, 24));
//        // IUC.registrarProveedor("designCraft", "Lucas", "Torres", "lucas@designcraft.com", "Artesanía y diseño en productos únicos", "www.designcraft.com", LocalDate.of(1989, 8, 2));
//        // IUC.registrarProveedor("musicMania", "María", "Ruiz", "maria@musicmania.com", "Tienda de instrumentos musicales y accesorios", "www.musicmania.com", LocalDate.of(1997, 6, 6));
//        // IUC.registrarProveedor("petCompanion", "Camila", "García", "camila@petcompanion.com", "Cuidado y servicios para mascotas", "www.petcompanion.com", LocalDate.of(2003, 10, 20));
//        // IUC.registrarProveedor("ecoLiving", "Mateo", "Morales", "mateo@ecoliving.com", "Productos ecológicos y estilo de vida sostenible", "www.ecoliving.com", LocalDate.of(1998, 4, 14));
//        // IUC.registrarProveedor("fashionSpot", "Valentina", "Vega", "valentina@fashionspot.com", "Plataforma para compartir tendencias de moda", "www.fashionspot.com", LocalDate.of(1995, 9, 28));
//        // IUC.registrarProveedor("deliciousBakery", "Javier", "López", "javier@deliciousbakery.com", "Panadería y repostería con productos artesanales", "www.deliciousbakery.com", LocalDate.of(2000, 1, 3));
//    }
//
//}
//*/