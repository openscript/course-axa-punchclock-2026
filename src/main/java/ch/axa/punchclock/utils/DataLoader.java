package ch.axa.punchclock.utils;

import java.time.LocalDateTime;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ch.axa.punchclock.entities.Category;
import ch.axa.punchclock.entities.Entry;
import ch.axa.punchclock.entities.Tag;
import ch.axa.punchclock.repositories.CategoryRepository;
import ch.axa.punchclock.repositories.EntryRepository;
import ch.axa.punchclock.repositories.TagRepository;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private EntryRepository entryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createCategories();
        createTags();
        createEntries();
    }

    private void createCategories() {
        var firstCategory = new Category();
        firstCategory.setTitle("Weiterbildung");
        categoryRepository.save(firstCategory);
        var secondCategory = new Category();
        secondCategory.setTitle("Militär");
        categoryRepository.save(secondCategory);
        var thirdCategory = new Category();
        thirdCategory.setTitle("Arbeit");
        categoryRepository.save(thirdCategory);
    }

    private void createTags() {
        var firstTag = new Tag();
        firstTag.setTitle("Modul 106");
        tagRepository.save(firstTag);
        var secondTag = new Tag();
        secondTag.setTitle("Projekt A");
        tagRepository.save(secondTag);
        var thirdTag = new Tag();
        thirdTag.setTitle("Projekt B");
        tagRepository.save(thirdTag);
    }

    private void createEntries() {
        var firstEntry = new Entry();
        firstEntry.setDescription("Sad Coding at BBW");
        firstEntry.setCategory(categoryRepository.findById(3L).get());
        var tags = new HashSet<Tag>();
        tags.add(tagRepository.findById(1L).get());
        firstEntry.setTags(tags);
        firstEntry.setCheckIn(LocalDateTime.now().minusHours(2));
        firstEntry.setDuration(60);
        entryRepository.save(firstEntry);

        var secondEntry = new Entry();
        secondEntry.setDescription("Mehr SQL Statements schreiben");
        secondEntry.setCategory(categoryRepository.findById(1L).get());
        var secondTags = new HashSet<Tag>();
        secondTags.add(tagRepository.findById(1L).get());
        secondEntry.setTags(secondTags);
        secondEntry.setCheckIn(LocalDateTime.now());
        secondEntry.setDuration(60);
        entryRepository.save(secondEntry);
    }

    
}