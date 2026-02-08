package com.burakbaslik.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthViewController {

	@GetMapping("/")
	public String homePage() {
		return "index";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/register")
	public String registerPage() {
		return "register";
	}

	@GetMapping("/dashboard")
	public String dashboardPage() {
		return "dashboard";
	}

    @GetMapping("/auth/edit")
    public String editPage() {return "edit";}
}


