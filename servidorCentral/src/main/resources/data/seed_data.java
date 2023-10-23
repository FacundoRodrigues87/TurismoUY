import com.github.javafaker.Faker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SeedData {

    public static void main(String[] args) {
        // Crear el EntityManagerFactory usando la unidad de persistencia "PA2023"
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PA2023");

        // Crear un EntityManager
        EntityManager em = emf.createEntityManager();

        // Iniciar una transacción
        em.getTransaction().begin();

        // Crear instancias de Faker
        Faker faker = new Faker();

        // Crear y persistir instancias de departamento
        departamento departamento1 = new departamento(faker.company().name(), faker.lorem().sentence(), faker.internet().url(), new ArrayList<>());
        departamento departamento2 = new departamento(faker.company().name(), faker.lorem().sentence(), faker.internet().url(), new ArrayList<>());

        em.persist(departamento1);
        em.persist(departamento2);

        // Crear y persistir instancias de proveedor
        proveedor proveedor1 = new proveedor(faker.name().username(), faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(), faker.lorem().sentence(), faker.internet().url(), LocalDate.now());
        proveedor proveedor2 = new proveedor(faker.name().username(), faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(), faker.lorem().sentence(), faker.internet().url(), LocalDate.now());

        em.persist(proveedor1);
        em.persist(proveedor2);

        // Crear y persistir instancias de paquete
        paquete paquete1 = new paquete(faker.lorem().word(), faker.number().numberBetween(5, 30), faker.lorem().sentence(), LocalDate.now().plusMonths(6));
        paquete paquete2 = new paquete(faker.lorem().word(), faker.number().numberBetween(5, 30), faker.lorem().sentence(), LocalDate.now().plusMonths(6));

        em.persist(paquete1);
        em.persist(paquete2);

        // Crear y persistir instancias de salida
        salida salida1 = new salida(faker.lorem().word(), faker.number().numberBetween(10, 100), LocalDate.now(), LocalDate.now().plusMonths(1), faker.address().city(), new ArrayList<>());
        salida salida2 = new salida(faker.lorem().word(), faker.number().numberBetween(10, 100), LocalDate.now(), LocalDate.now().plusMonths(1), faker.address().city(), new ArrayList<>());

        em.persist(salida1);
        em.persist(salida2);

        // Crear y persistir instancias de inscripcion
        inscripcion inscripcion1 = new inscripcion(LocalDate.now(), faker.number().numberBetween(1, 5), faker.number().numberBetween(10, 100));
        inscripcion inscripcion2 = new inscripcion(LocalDate.now(), faker.number().numberBetween(1, 5), faker.number().numberBetween(10, 100));

        em.persist(inscripcion1);
        em.persist(inscripcion2);

        // Crear y persistir instancias de turista
        turista turista1 = new turista(faker.name().username(), faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(), faker.address().country(), LocalDate.now().minusYears(30));
        turista turista2 = new turista(faker.name().username(), faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(), faker.address().country(), LocalDate.now().minusYears(25));

        em.persist(turista1);
        em.persist(turista2);

        // Crear y persistir instancias de actividad con datos ficticios
        actividad actividad1 = new actividad(faker.book().title(), faker.lorem().paragraph(), faker.number().numberBetween(1, 10), faker.number().numberBetween(50, 500), faker.address().city(), LocalDate.now(), departamento1, proveedor1);
        actividad actividad2 = new actividad(faker.book().title(), faker.lorem().paragraph(), faker.number().numberBetween(1, 10), faker.number().numberBetween(50, 500), faker.address().city(), LocalDate.now(), departamento2, proveedor2);

        em.persist(actividad1);
        em.persist(actividad2);

        // Asociar actividades a paquetes, inscripciones, salidas, etc. (depende de tu modelo de datos)
        // Asociar la actividad1 al paquete1
		paquete1.getActividades().add(actividad1);
		
		// Asociar la actividad2 al paquete2
		paquete2.getActividades().add(actividad2);
		// Asociar la actividad1 a la inscripcion1
		inscripcion1.setActividad(actividad1);
		
		// Asociar la actividad2 a la inscripcion2
		inscripcion2.setActividad(actividad2);
		// Asociar la actividad1 a la salida1
		salida1.getActividades().add(actividad1);
		
		// Asociar la actividad2 a la salida2
		salida2.getActividades().add(actividad2);




        // Confirmar la transacción
        em.getTransaction().commit();

        // Cerrar el EntityManager y el EntityManagerFactory
        em.close();
        emf.close();
    }
}
