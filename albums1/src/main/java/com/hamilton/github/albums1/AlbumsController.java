package com.hamilton.github.albums1;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/albums")
@RestController
public class AlbumsController {

	private FootballClientService footballClientService;

    public AlbumsController(FootballClientService footballClientService) {
        this.footballClientService = footballClientService;
    }
    
    @GetMapping("/players")
    public List<?> getPlayers() {
        List<?> list = footballClientService.getPlayers();
        list.forEach(item -> {
        	System.out.println(item.toString());
        });
        return list;
    }
    
    @GetMapping("/players/{id}")
    public Optional<Player> getPlayer(@PathVariable String id) {
        return footballClientService.getPlayer(id);
    }
}
