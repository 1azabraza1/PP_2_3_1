package web.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.models.User;
import web.service.UserService;


@Controller
@RequestMapping()
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showUsersTable(Model model){
        model.addAttribute("users", userService.getUsersList());
        return "users";
    }

    @GetMapping("/new")
    public String createNewUser(@ModelAttribute("user") User user) {
        return "new_user";
    }

    @PostMapping("/")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new_user";
        }
        userService.addUser(user);
        return "redirect:/";
    }

    @GetMapping(value = "/edit")
    public String editPage(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "edit_user";
    }

//    @PostMapping("/edit")
//    public String editUser(@ModelAttribute("user") @Valid User user
//            , BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "edit_user";
//        } else {
//            userService.editUser(user);
//            return "redirect:/";
//        }
//    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit_user";
        }
        userService.editUser(user);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteUser(id);
        return "redirect:/";
    }

}

