/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import pricecomparison.entity.ModelProperty;
/**
 *
 * @author David
 */


@Repository
public class ModelPropertyRepository {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveOrUpdateModelProperty(ModelProperty modelProperty) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(modelProperty);
            session.getTransaction().commit();
        }
    }

    // Add additional methods for custom queries or operations if needed
}
