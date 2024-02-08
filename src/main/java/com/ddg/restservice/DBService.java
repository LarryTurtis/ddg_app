package com.ddg.restservice;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

}
