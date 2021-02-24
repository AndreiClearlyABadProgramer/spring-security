package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.models.Role;
import web.models.User;
import web.service.UserDetailsServiceImpl;
import web.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	final private UserDetailsService userService;

	@Autowired
	final private UserService service;

	public UserController(UserDetailsService userService, UserService service) {
		this.userService = userService;
		this.service = service;
	}

	@GetMapping("user")
	public String userPage(Principal principal,  Model model){
		model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
		return "user";
	}

	@GetMapping("admin")
	public String adminPage(Model model){
		model.addAttribute("user", new User());
		model.addAttribute("userList", service.userList());
		return "admin";
	}

	@PostMapping("/admin")
	public String create(@ModelAttribute("user") User user){
		service.addUser(user);
		return "redirect:/admin";
	}

	@GetMapping(value = "logout")
	public String logout(){
		return "logout";
	}

	@PostMapping("/logout")
	public String logoutURL(){
		return "redirect:/logout";
	}

	@GetMapping(value = "/admin/Remove/{id}")
	public String remove(@PathVariable("id") long id){
		service.deleteUser(id);
		return "redirect:/admin";
	}

	@GetMapping(value = "/admin/{id}/Edit")
	public String edit(@PathVariable("id") long id, Model model){
		model.addAttribute("user", service.getUserById(id));
		model.addAttribute("userList", service.userList());
		return "Edit";
	}

	@PatchMapping("/admin/{id}/Edit")
	public String update(@ModelAttribute("User") User user, @PathVariable("id") long id){
		service.updateUser(user);
		return "redirect:/admin";
	}

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

}
