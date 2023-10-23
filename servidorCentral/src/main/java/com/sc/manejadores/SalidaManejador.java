package com.sc.manejadores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import com.sc.EMFactory;
import com.sc.entidades.salida;
import com.sc.utilidades.log;
import com.sc.entidades.inscripcion;

public class SalidaManejador {
    private static SalidaManejador instancia = null;

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("PA2023");
    // Singleton.
    public static SalidaManejador getInstance() {
        if (instancia == null)
            instancia = new SalidaManejador();
        return instancia;
    }

    public void addSalida(salida salida) {
        // Para cada función hay que crear un nuevo em y tx.
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        String nombre = salida.getNombre();

        try {
            tx.begin();

            // Se guarda la salida.
            em.persist(salida);

            tx.commit();
            
    	    System.out.println("Se guarda la salida " + salida.getNombre() + " correctamente");
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            log.error("Guardado de Salida '" + nombre + "' erróneo.");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public salida getSalida(String nombre) {
	    EntityManager em = factory.createEntityManager();
        salida salida = null;

        try {
            salida = em.createQuery("SELECT s from salida s WHERE s.nombreS = :nombre", salida.class)
                        .setParameter("nombre", nombre)
                        .getSingleResult();     
        } catch (Exception e) {
            return null;
        }
   
        return salida;
        // DepartamentoManejador dm = DepartamentoManejador.getinstance();
        // List <departamento> LDepto = dm.getAllDepartamento();
        // //Recorro todos los departamentos para poder buscar la actividad que
        // //me pasan por parametro
        // if (LDepto == null) {
		// 	return null;
		// }
        // for(departamento depto: LDepto){
        //     log.warning(depto.getNombre());
        //     List<actividad> LAct = depto.getActividades();
        //     if(LAct == null) {
        //     	return null;
        //     }
        //     log.warning("Cantidad de actividades: " + LAct.size());
        //     for(actividad act : LAct) {
        //     	List<salida>LSalida = act.getSalidas();
        //     	if(LSalida == null) {
        //     		return null;
        //     	}
        //     	for(salida salida: LSalida) {
        //     		if(salida.getNombre().equals(nombre))
        //     		return salida;
        //     	}
        // 	}
        // }
        // return null;
    }
    
    public List<salida> getAllSalidas() {
	    EntityManager em = factory.createEntityManager();
        List<salida> salidas = new ArrayList<salida>();

        try {
            salidas = em.createQuery("SELECT s from salida s LEFT JOIN FETCH s.inscripciones", salida.class).getResultList();     
        } catch (Exception e) {
            return null;
        }
        em.close();
        return salidas;
    }

    public void deleteSalida(String salidaNombre) {
        // Para cada función hay que crear un nuevo em y tx.
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Se elimina la salida.
            salida sali = em.find(salida.class,salidaNombre);
            
            em.remove(sali);

            tx.commit();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            log.error("Borrado de Salida '" + salidaNombre + "' erróneo.");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    
    public void updateSalida(salida salida) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        


        try {
            tx.begin();

            // Se actualiza el archivo.
            em.merge(salida);

            tx.commit();

            // Se actualiza en la colección.
            log.info("El departamento se actualizó" + salida.getNombre() + "correctamente");
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            log.error("Actualizado el departamento '" + salida.getNombre() + "' errrono.");
            e.printStackTrace();
        } finally {
            em.close();
        }
    	
    }
    
    public salida persistirInscripcionEnSalida(salida salida, inscripcion inscripcion){
    	
    	EntityManagerFactory factory = EMFactory.getEntityManagerFactory();
	    EntityManager em = factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
	    tx.begin();
	    
	    salida salida1 = em.createQuery("SELECT s FROM salida s WHERE s.nombreS = '" + salida.getNombre() + "'",salida.class).getSingleResult();
		salida1.addInscripcion(inscripcion);
		
		tx.commit();
		em.close();
		return salida1;
    	
    }
}
    