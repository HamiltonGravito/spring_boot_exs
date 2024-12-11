package com.hamilton.github.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FootballController {

	@GetMapping("/")
	public String home() {
		return "home";
	}
}
