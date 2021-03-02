package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.models.Role;
import web.models.User;
import web.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
		List<Role> roles = new ArrayList<>();
		roles.add(new Role("ROLE_USER"));
		roles.add(new Role("ROLE_ADMIN"));
		model.addAttribute("roleList", roles);
		model.addAttribute("userList", service.userList());
		return "admin";
	}

	@PostMapping("/admin")
	public String create(@ModelAttribute("user") User user){
		service.addUser(user);
		return "redirect:/admin";
	}

	@GetMapping("logout")
	public String logout(){
		return "logout";
	}

	@PostMapping("/logout")
	public String logoutURL(){
		return "redirect:/logout";
	}

	@GetMapping("/admin/remove/{id}")
	public String remove(@PathVariable("id") long id){
		service.deleteUser(id);
		return "redirect:/admin";
	}

	@GetMapping("/admin/{id}/edit")
	public String edit(@PathVariable("id") long id, Model model){
		model.addAttribute("user", service.getUserById(id));
		List<Role> roles = new ArrayList<>();
		roles.add(new Role("ROLE_USER"));
		roles.add(new Role("ROLE_ADMIN"));
		model.addAttribute("roleList", roles);
		model.addAttribute("userList", service.userList());
		return "edit";
	}

	@PatchMapping("/admin/{id}/edit")
	public String update(@ModelAttribute("User") User user, @PathVariable("id") long id){
		service.updateUser(user);
		return "redirect:/admin";
	}

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

}
