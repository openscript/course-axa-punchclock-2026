package ch.axa.punchclock.repositories;

import ch.axa.punchclock.entities.Entry;

public interface EntryRepositoryCustom {
    Iterable<Entry> searchByDescription(String searchString);
}