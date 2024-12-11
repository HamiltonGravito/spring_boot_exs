package com.hamilton.github.resources.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/football")
public class FootballController {

	@GetMapping("/teams")
	public List<String> getTeams(){
		return List.of("Argentina", "Austrália", "Brazil");
	}
	
	@PostMapping("/teams")
	public String addTeam(@RequestBody String teamName){
		return teamName + " added";
	}
}
