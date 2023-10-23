package com.sc.manejadores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.sc.EMFactory;
import com.sc.entidades.departamento;
import com.sc.utilidades.log;

public class DepartamentoManejador {
    private Map<String, departamento> departamentoNombre;
    private static DepartamentoManejador instancia = null;

    // EntityManagerFactory factory = Persistence.createEntityManagerFactory("PA2023");
    EntityManagerFactory factory = EMFactory.getEntityManagerFactory();

    private DepartamentoManejador(){
        departamentoNombre = new HashMap<String, departamento>();

        // Para cada función hay que crear un nuevo em y tx.
	    EntityManager em = factory.createEntityManager();

        // List<departamento> departamentos = em.createQuery("SELECT d from departamento d LEFT JOIN FETCH d.actividades", departamento.class).getResultList();
        List<departamento> departamentos = em.createQuery("SELECT d FROM departamento d LEFT JOIN FETCH d.actividades", departamento.class).getResultList();

        if (departamentos != null) {
            for (departamento departamento : departamentos ) {
            	// log.info(departamento.getNombre());
                departamentoNombre.put(departamento.getNombre(), departamento);
            }
        }
        log.info("Departamentos cargados: " + departamentoNombre.size());
        em.close();
    }

    // Singleton.
    public static DepartamentoManejador getinstance() {
        if (instancia == null)
            instancia = new DepartamentoManejador();
        return instancia;
    }

    public void updateDepartamento(departamento departamento){
        // Para cada función hay que crear un nuevo em y tx.
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        String nombre = departamento.getNombre();

        try {
            tx.begin();

            // Se actualiza el archivo.
            em.merge(departamento);

            tx.commit();

            // Se actualiza en la colección.
            departamentoNombre.put(nombre, departamento);
            log.info("El departamento se actualizó" + nombre + "correctamente");
            log.info("Departamentos: " + departamentoNombre.size());
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            log.error("Actualizado el departamento '" + departamento.getNombre() + "' errrono.");
            e.printStackTrace();
        } finally {
            em.close();
        }
    };

    public void addDepartamento(departamento departamento) {
        // Para cada función hay que crear un nuevo em y tx.
	    EntityManager em = factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();

        String nombre = departamento.getNombre();

        try {
            tx.begin();

            // Se guarda el archivo.
            em.persist(departamento);

            tx.commit();        

            // Si el archivo se logró guardar en BD, lo guarda en la colección y además muestra cuantos usuarios hay.
            departamentoNombre.put(nombre, departamento);
            log.info("[DepartamentoManejador] se agrego el departamento: " + nombre);
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            log.error("Guardado de Departamento '" + nombre + "' errrono.");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public departamento getDepartamento(String nombre) {
    	//System.out.println("Obtiene el departamento " + departamentoNombre.get(nombre).getNombre());
        return departamentoNombre.get(nombre);
        
    }

    public List<departamento> getAllDepartamento() {
        if (departamentoNombre.isEmpty()) { return null; };

        List<departamento> deptos = new ArrayList<>(departamentoNombre.values());
        return deptos;
    }
}
