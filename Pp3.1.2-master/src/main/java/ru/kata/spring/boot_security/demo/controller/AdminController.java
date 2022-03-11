package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @PostConstruct
    public void addUser() {
        User user = new User();
        user.setName("Alexander");
        user.setSurName("Kondratyev");
        user.setEmail("info@ivawood.com");
        user.setPassword("admin");
        user.setRoles(Collections.singletonList(new Role("ROLE_ADMIN")));
        userService.save(user);
    }

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("listUsers", userService.findAll());
        return "admin";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user")  User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView edit(Model model, @PathVariable("id") long id) {
       ModelAndView mav = new ModelAndView("edit");
       User user = userService.findById(id);
       mav.addObject("user",user);
       return mav;
    }

    @PostMapping(value = "/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.update(id, user);
        return "redirect:/admin";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
