package ch.axa.punchclock.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.axa.punchclock.entities.Entry;
import ch.axa.punchclock.repositories.CategoryRepository;
import ch.axa.punchclock.repositories.EntryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/entries")
public class APIEntryController {

  @Autowired
  private EntryRepository entryRepository;
  @Autowired
  private CategoryRepository categoryRepository;

  @PostMapping
  @Transactional
  @ResponseStatus(HttpStatus.CREATED)
  public Entry create(@RequestBody @Valid Entry entry) {
    var category = categoryRepository.findById(entry.getCategory().getId()).get();
    entry.setCategory(category);
    return entryRepository.save(entry);
  }

  @GetMapping
  public Iterable<Entry> index(
      @RequestParam(required = false) Long categoryId,
      @RequestParam(required = false) Long tagId,
      @RequestParam(required = false) String descriptionSearch) {
    if (categoryId != null) {
      return entryRepository.findByCategory(categoryId);
    }
    if (tagId != null) {
      return entryRepository.findByTagsId(tagId);
    }
    if (descriptionSearch != null) {
      return entryRepository.searchByDescription(descriptionSearch);
    }
    return entryRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Entry> read(@PathVariable long id) {
    return ResponseEntity.of(entryRepository.findById(id));
  }

  @PutMapping("/{id}")
  public Entry update(@PathVariable long id, @RequestBody @Valid Entry entry) {
    entry.setId(id);
    return entryRepository.save(entry);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Entry> delete(@PathVariable long id) {
    var entry = entryRepository.findById(id);
    if (entry.isPresent()) {
      entryRepository.delete(entry.get());
      return new ResponseEntity<>(HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}