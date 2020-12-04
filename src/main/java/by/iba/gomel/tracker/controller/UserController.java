package by.iba.gomel.tracker.controller;

import by.iba.gomel.tracker.entity.User;
import by.iba.gomel.tracker.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private GenericService<User, Integer> userGenericService;

    @Autowired
    public UserController(@Qualifier("userService") GenericService<User, Integer> userGenericService) {
        this.userGenericService = userGenericService;
    }

    @GetMapping
    public String index(Model model) {
        List<User> userList = userGenericService.readAll();
        System.out.println(userList);
        model.addAttribute("users", userList);
        return "/user/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") String id, Model model) {
        User user = userGenericService.read(Integer.parseInt(id));
        model.addAttribute("user", user);
        return "/user/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "/user/new";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/user/new";
        }
        userGenericService.create(user);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        userGenericService.delete(Integer.parseInt(id));
        return "redirect:/user";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") String id, Model model) {
        model.addAttribute("user", userGenericService.read(Integer.parseInt(id)));
        return "/user/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") String id,@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/user/edit";
        }
        userGenericService.update(Integer.parseInt(id), user);
        return String.format("redirect:/user/%d", user.getId());
    }
}
