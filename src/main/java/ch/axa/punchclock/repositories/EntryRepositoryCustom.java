package ch.axa.punchclock.repositories;

import java.util.List;

import ch.axa.punchclock.entities.Entry;

public interface EntryRepositoryCustom {
    List<Entry> searchByDescription(String searchString);
}