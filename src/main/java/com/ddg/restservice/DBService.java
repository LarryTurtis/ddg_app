package com.ddg.restservice;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import com.ddg.db.Entry;

@Slf4j
public class DBService {

    private SessionFactory sessionFactory;

    @Autowired
    public DBService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void store(Entry entry) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entry);
            session.getTransaction().commit();
        } catch (PersistenceException | IllegalStateException e) {
            log.error(e.getMessage(), e);
            throw new DDGException("Error saving to DB");
        }
    }

    public List<Entry> loadEntries() {
        Transaction tx = null;
        List<Entry> entries = Collections.emptyList();
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Entry> cr = cb.createQuery(Entry.class);
            Root<Entry> root = cr.from(Entry.class);
            Instant yesterday = Instant.now().minus(1, ChronoUnit.DAYS);
            cr.select(root).where();
            cr.where(cb.greaterThanOrEqualTo(root.get("timestamp"), yesterday));
            Query query = session.createQuery(cr);
            entries = query.getResultList();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            log.error("Failed to retrieve entries from DB", e);
            e.printStackTrace();
        }
        return entries;
    }

}
