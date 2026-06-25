package ch.axa.punchclock.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ch.axa.punchclock.entities.Entry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class EntryRepositoryCustomImpl implements EntryRepositoryCustom {
    @Autowired
    private EntityManager em;

    @Override
    public List<Entry> searchByDescription(String searchString) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Entry> cq = cb.createQuery(Entry.class);

        Root<Entry> entry = cq.from(Entry.class);
        StringBuilder search = new StringBuilder().append("%").append(searchString).append("%");

        cq.where(cb.like(entry.get("description"), search.toString()));

        return em.createQuery(cq).getResultList();
    }

}