package ch.axa.punchclock.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ch.axa.punchclock.entities.Entry;
import jakarta.inject.Inject;

@SpringBootTest
public class EntryRepositoryTest {
    
    @Inject
    private EntryRepository entryRepository;

    @Test
    public void testIfEntryCanBeSaved() {
        Entry entry = new Entry();
        entry.setDescription("Awesome");
        entry.setCheckIn(LocalDateTime.now());
        entry.setDuration(60);

        entryRepository.save(entry);

        assertEquals("Awesome", entryRepository.findById(entry.getId()).get().getDescription());
    }
}
