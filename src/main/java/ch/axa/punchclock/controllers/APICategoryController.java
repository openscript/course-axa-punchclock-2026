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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.axa.punchclock.entities.Category;
import ch.axa.punchclock.repositories.CategoryRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class APICategoryController {

  @Autowired
  private CategoryRepository entryRepository;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Category create(@RequestBody @Valid Category entry) {
    return entryRepository.save(entry);
  }

  @GetMapping
  public Iterable<Category> index() {
    return entryRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Category> read(@PathVariable long id) {
    return ResponseEntity.of(entryRepository.findById(id));
  }

  @PutMapping("/{id}")
  public Category update(@PathVariable long id, @RequestBody @Valid Category entry) {
    entry.setId(id);
    return entryRepository.save(entry);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Category> delete(@PathVariable long id) {
    var entry = entryRepository.findById(id);
    if(entry.isPresent()) {
      entryRepository.delete(entry.get());
      return new ResponseEntity<>(HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}