package com.sc.manejadores;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import com.sc.entidades.actividad;
import com.sc.utilidades.log;

public class ActividadManejador {
    private static ActividadManejador instancia = null;
    
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("PA2023");

    private ActividadManejador(){};
    
    // Singleton.
    public static ActividadManejador getinstance() {
        if (instancia == null)
            instancia = new ActividadManejador();
        return instancia;
    }


    public void addActividad(actividad actividad) {
        // Para cada función hay que crear un nuevo em y tx.
	    EntityManager em = factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();

            // Se guarda el archivo.
            // em.persist(actividad);
            em.merge(actividad);

            tx.commit();       
            log.info("Se crea la actividad " + actividad.getNombre());
            
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            log.error("Guardado de Actividad '" + actividad.getNombre() + "' errrono.");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void updateActividad(actividad actividad){
        // Para cada función hay que crear un nuevo em y tx.
	    EntityManager em = factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Se actualiza el archivo.
            em.merge(actividad);

            tx.commit();       
            log.info("La actividad " + actividad.getNombre() + " se actualizó correctamente");
            log.info("Salidas: " + actividad.getSalidas().size());

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            log.error("Actualizacion de Actividad: '" + actividad.getNombre() + "' errrono.");
            e.printStackTrace();
        } finally {
            em.close();
        }
    };

    public actividad getActividad(String nombreAct){
    	EntityManager em = factory.createEntityManager();
    	
        actividad actividad;
        try {
            actividad = em.createQuery("SELECT a FROM actividad a JOIN FETCH a.salidas WHERE a.nombreA = '" + nombreAct + "'",actividad.class).getSingleResult();
        } catch (NoResultException  e) {
            actividad = null;
        }
    	em.close();
    	return actividad;
    }
}
