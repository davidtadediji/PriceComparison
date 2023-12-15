/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import pricecomparison.entity.ModelVariation;

/**
 *
 * @author David
 */
@Repository
public class ModelVariationRepository {

    private SessionFactory sessionFactory;

    // Ensure the setter method has the correct parameter type
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveOrUpdateModelVariation(ModelVariation modelVariation) {
        try ( Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(modelVariation);
            session.getTransaction().commit();
        }
    }

    // New method to fetch a model variation by name, value, and model ID
    public ModelVariation getModelVariationByNameAndValue(Long modelId, String variationName, String variationValue) {
        try ( Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM ModelVariation WHERE model.id = :modelId AND variationName = :variationName AND variationValue = :variationValue", ModelVariation.class)
                    .setParameter("modelId", modelId)
                    .setParameter("variationName", variationName)
                    .setParameter("variationValue", variationValue)
                    .uniqueResult();
        }
    }
}
