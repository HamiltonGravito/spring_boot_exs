package com.hamilton.github.football;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hamilton.github.football.exceptions.NotFoundException;
import com.hamilton.github.football.player.controller.PlayerController;
import com.hamilton.github.football.player.model.Player;
import com.hamilton.github.football.player.services.PlayerService;

@WebMvcTest(value = PlayerController.class)
public class PlayerControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockitoBean
	PlayerService playerService;

	@Test
	public void testListPlayers() throws Exception {
		//Cria uma lista para teste
		Player player1 = new Player("1884823", 5, "Ivana ANDRES", "Defender", LocalDate.of(1994, 07, 13));
		Player player2 = new Player("325636", 11, "Alexia PUTELLAS", "Midfielder", LocalDate.of(1994, 02, 04));
		List<Player> players = List.of(player1, player2);
		//Devolve esta lista quando chamar o método listPlayers() do PlayerService
		given(playerService.listPlayers()).willReturn(players);
		//Cria um objeto para serialização e desserialização do JSON.
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		//Cria a solicitação HTTP GET e espera um retorno (JSON) de uma matriz com 2 elementos    
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.get("/players")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers
						.jsonPath("$", hasSize(2)))
				.andReturn();

		//Extrai a resposta e desserializa o JSON em objetos do tipo Player e verifica se a lista enviada é igual a recebida
		String json = result.getResponse().getContentAsString();
		List<Player> returnedPlayers = mapper.readValue(json,
				mapper.getTypeFactory().constructCollectionType(List.class, Player.class));
		assertArrayEquals(players.toArray(), returnedPlayers.toArray());
	}
	
	@Test
	public void testReadPlayer_doenst_exist() throws Exception {
		String id = "1884823"; //Esse id pode conter qualquer valor que não alterará no resultado
		//Especifica o comportamento ao chamar o playService.getPlayer, ou seja, quando esse método for chamado devolve a exceção criada.
		given(playerService.getPlayer(id)).willThrow(new NotFoundException("Player not found"));
		//Verifica se o retorno é um JSON com status NOT_FOUND
		mvc.perform(MockMvcRequestBuilders.get("/players/" + id).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
		
	}
}