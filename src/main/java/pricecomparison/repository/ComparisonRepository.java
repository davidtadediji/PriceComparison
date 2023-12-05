/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.repository;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pricecomparison.entity.Comparison;
import pricecomparison.entity.Model;

/**
 *
 * @author David
 */
@Repository
public class ComparisonRepository {

    private final SessionFactory sessionFactory;

    // Constructor with Dependency Injection
    public ComparisonRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveOrUpdateComparison(Comparison comparison) {
        try ( Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(comparison);
            transaction.commit();
        }
    }

    public Comparison getComparisonById(Integer id) {
        try ( Session session = sessionFactory.openSession()) {
            return session.get(Comparison.class, id);
        }
    }

    public void deleteComparison(Comparison comparison) {
        try ( Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(comparison);
            transaction.commit();
        }
    }

    public List<Comparison> getComparisonsByModel(Model model) {
        try ( Session session = sessionFactory.openSession()) {
            String hql = "FROM Comparison WHERE model = :model";
            return session.createQuery(hql, Comparison.class)
                    .setParameter("model", model)
                    .getResultList();
        }
    }
}
