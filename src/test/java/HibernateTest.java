/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import pricecomparison.model.Model;
/**
 *
 * @author David
 */



public class HibernateTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        // Initialize Hibernate session factory
        sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @AfterEach
    public void tearDown() {
        // Roll back the transaction and close the session
        if (transaction != null) {
            transaction.rollback();
        }
        if (session != null) {
            session.close();
        }
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void testEmployeePersistence() {
        // Create an Employee instance and save it to the database
        Model model = new Model();
        model.setName("Iphone");
        model.setManufacturer("Apple inc.");

        session.save(model);

        // Commit the transaction
        transaction.commit();

        // Clear the session to detach the objects
        session.clear();

        // Retrieve the Employee from the database and assert its properties
        Model retrievedModel = session.get(Model.class, model.getId());
        assertEquals("Iphone", retrievedModel.getName());
        assertEquals("Apple inc.", retrievedModel.getManufacturer());
    }
}
