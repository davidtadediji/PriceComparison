/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pricecomparison.entity.ModelVariation;
/**
 *
 * @author David
 */
public class ModelVariationDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveOrUpdateModelVariation(ModelVariation modelVariation) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(modelVariation);
            session.getTransaction().commit();
        }
    }
}
