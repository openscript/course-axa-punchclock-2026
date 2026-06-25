package ch.axa.punchclock.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ch.axa.punchclock.entities.Entry;

public interface EntryRepository extends CrudRepository<Entry, Long> {
    List<Entry> findByCategory(@Param("categoryId") Long categoryId);    
}
