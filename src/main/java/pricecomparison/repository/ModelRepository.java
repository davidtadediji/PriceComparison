/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import pricecomparison.entity.Model;

/**
 *
 * @author David
 */
@Repository
public class ModelRepository {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
         System.out.println("SessionFactory set: " + sessionFactory);
    }

    public void saveOrUpdateModel(Model model) {
        try ( Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(model);
            session.getTransaction().commit();
        }
    }
    
   public Model getModelByName(String modelName) {
    try (Session session = sessionFactory.openSession()) {
        return session.createQuery("FROM Model m WHERE m.name = :modelName", Model.class)
                .setParameter("modelName", modelName)
                .uniqueResult();
    }
}

}
