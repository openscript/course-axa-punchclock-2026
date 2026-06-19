package ch.axa.punchclock.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.axa.punchclock.entities.Entry;

@SpringBootTest
public class EntryRepositoryTest {

    @Autowired
    private EntryRepository entryRepository;

    @BeforeEach
    public void cleanRepository() {
        entryRepository.deleteAll();
    }

    @Test
    public void testIfEntryCanBeSaved() {
        Entry entry = createEntry("Awesome", 60);

        entryRepository.save(entry);

        assertEquals("Awesome", entryRepository.findById(entry.getId()).get().getDescription());
    }

    @Test
    public void testIfSavedEntryGetsGeneratedId() {
        Entry entry = createEntry("Generated ID", 15);

        entryRepository.save(entry);

        assertNotNull(entry.getId());
    }

    @Test
    public void testIfEntryCanBeUpdatedWithSave() {
        Entry entry = createEntry("Initial", 30);
        entryRepository.save(entry);

        entry.setDescription("Updated");
        entry.setDuration(45);
        entryRepository.save(entry);

        Entry updated = entryRepository.findById(entry.getId()).orElseThrow();
        assertEquals("Updated", updated.getDescription());
        assertEquals(45, updated.getDuration());
    }

    @Test
    public void testIfEntryCanBeDeletedById() {
        Entry entry = createEntry("Delete me", 10);
        entryRepository.save(entry);

        entryRepository.deleteById(entry.getId());

        assertFalse(entryRepository.findById(entry.getId()).isPresent());
    }

    @Test
    public void testIfAllSavedEntriesCanBeFound() {
        entryRepository.save(createEntry("First", 20));
        entryRepository.save(createEntry("Second", 40));

        List<Entry> entries = (List<Entry>) entryRepository.findAll();

        assertEquals(2, entries.size());
        assertTrue(entries.stream().anyMatch(e -> "First".equals(e.getDescription())));
        assertTrue(entries.stream().anyMatch(e -> "Second".equals(e.getDescription())));
    }

    private Entry createEntry(String description, int duration) {
        Entry entry = new Entry();
        entry.setDescription(description);
        entry.setCheckIn(LocalDateTime.now());
        entry.setDuration(duration);
        return entry;
    }
}
