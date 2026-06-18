package ch.axa.punchclock.services;

import java.util.List;

import ch.axa.punchclock.entities.Entry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class EntryService {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Entry entry) {
        this.entityManager.persist(entry);
    }

    public Entry read(Long id) {
        return this.entityManager.find(Entry.class, id);
    }

    public List<Entry> index() {
        return this.entityManager.createQuery("SELECT e FROM Entry e", Entry.class).getResultList();
    }

    public void update(Entry entry) {
        this.entityManager.merge(entry);
    }

    public void delete(Entry entry) {
        Entry managedEntry = entityManager.contains(entry) ? entry : entityManager.merge(entry);
        entityManager.remove(managedEntry);
    }
}
