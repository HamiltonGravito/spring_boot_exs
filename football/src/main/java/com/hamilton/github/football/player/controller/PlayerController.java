package com.hamilton.github.football.player.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hamilton.github.football.exceptions.AlreadyExistsException;
import com.hamilton.github.football.exceptions.NotFoundException;
import com.hamilton.github.football.player.model.Player;
import com.hamilton.github.football.player.services.PlayerService;

@RequestMapping("/players")
@RestController
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> listPlayers() {
        return playerService.listPlayers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> readPlayer(@PathVariable String id) {
        try {
			Player player = playerService.getPlayer(id);
			return new ResponseEntity<>(player, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

    @PostMapping
    public void createPlayer(@RequestBody Player player) {
        playerService.addPlayer(player);
    }

    @PutMapping("/{id}")
    public void updatePlayer(@PathVariable String id, @RequestBody Player player) {
        playerService.updatePlayer(player);
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable String id) {
        playerService.deletePlayer(id);
    }
    
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found")
    @ExceptionHandler(NotFoundException.class)
    public void notFoundHandler() {}
    
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Already exists")
    @ExceptionHandler(AlreadyExistsException.class)
    public void alreadyExistsHandler() {}

}