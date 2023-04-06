package ru.kasatchikov.springcourse.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kasatchikov.springcourse.dao.PersonDAO;
import ru.kasatchikov.springcourse.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private PersonDAO personDAO;
    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "/show";
    }
    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "/new";
        personDAO.save(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "/edit";
        personDAO.update(id, person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}/edit")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }
}
