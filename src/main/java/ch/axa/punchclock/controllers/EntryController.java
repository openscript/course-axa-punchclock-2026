package ch.axa.punchclock.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ch.axa.punchclock.repositories.EntryRepository;

@Controller
public class EntryController {

  @Autowired
  private EntryRepository entryRepository;

  @GetMapping("/")
  public String showEntryList(Model model) {
    model.addAttribute("entries", entryRepository.findAll());
    return "index";
  }
}