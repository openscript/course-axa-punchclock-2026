package ch.axa.punchclock.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ch.axa.punchclock.entities.Category;
import ch.axa.punchclock.entities.Entry;
import ch.axa.punchclock.entities.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class EntryRepositoryFilterImpl implements EntryRepositoryFilter {
    @Autowired
    private EntityManager em;

    @Override
    public List<Entry> filter(Long categoryId, Long tagId, String searchDescription) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Entry> cq = cb.createQuery(Entry.class);

        Root<Entry> entry = cq.from(Entry.class);
        Join<Entry, Category> entryCategory = entry.join("category");
        Join<Entry, Tag> entryTags = entry.join("tags", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        if (categoryId != null) {
            predicates.add(cb.equal(entryCategory.get("id"), categoryId));
        }
        if (tagId != null) {
            predicates.add(cb.equal(entryTags.get("id"), tagId));
        }
        if (searchDescription != null) {
            predicates.add(cb.like(entry.get("description"), "%" + searchDescription + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

}