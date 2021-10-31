package ca.sheridancollege.controllers;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Toy;
import ca.sheridancollege.databases.DatabaseAccess;
import ca.sheridancollege.mail.EmailServiceImpl;


@Controller
public class HomeController {
	
	@Autowired
	private DatabaseAccess da;
	
	@Autowired
	private EmailServiceImpl esi;
	
	@GetMapping("/")    //localhost:8080
	public String goHome() {
		return "home.html";
	}
	
	@GetMapping("/login")
	public String goLoginPage() {
		return "login.html";
	}
	
	@GetMapping("/access_denied")
	public String goAccessDeniedPage() {
		return "/error/access_denied.html";
	}
	
	@GetMapping("/view")      
	public String viewToys(Model model) {
		model.addAttribute("toyList", da.getToys());
		return "viewToys.html";
	}
	
	@GetMapping("/goAddToy")    
	public String goAddToy(Model model) {
		model.addAttribute("toy", new Toy());
		return "addToy.html";
	}
	
	@GetMapping("/addToy")
	public String addToy(Model model, @ModelAttribute Toy toy) {
		da.addToy(toy);	
		return "redirect:/goAddToy";
	}
	
	@GetMapping("/edit/{id}")
	public String editToy(Model model, @PathVariable int id) {
		Toy toy = da.getToyById(id);
		model.addAttribute("toy", toy);
		return "editToy.html";
	}
	
	@GetMapping("/modify")
	public String modifyToy(@ModelAttribute Toy toy) {
		
		da.editToy(toy);	
		return "redirect:/view";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteToy(@PathVariable int id) {
		da.deleteToyById(id);
		return "redirect:/view";
	}
	
	@GetMapping("/sendEmailBoss")
	public String goEmailTemplateBoss() {
		
		try {
			esi.sendMailWithThymeleaf("enan.song.imbanangua@gmail.com", 
					"Yinan Song - View Toys");
			} catch (MessagingException e) {
				System.out.println(e);
				}
		return "sendEmail.html";
	}
	
	@GetMapping("/sendEmailWorker")
	public String goEmailTemplateWorker() {
		
		try {
			esi.sendMailWithThymeleaf("enan.song.imbanangua@gmail.com", 
					"Yinan Song - View Toys");
			} catch (MessagingException e) {
				System.out.println(e);
				}
		return "sendEmail.html";
	}
	
	@GetMapping("/register")
	public String goRegistrationPage() {
		return "registration.html";
	}
	
	@PostMapping("/register")
	public String processRegistration(@RequestParam String name, @RequestParam String password,
			@RequestParam String role) {
		
		da.createNewUser(name, password);
		long userId = da.findUserAccount(name).getUserId();
		da.addRole(userId, Long.valueOf(role));
		
		return "redirect:/";
	}

}
