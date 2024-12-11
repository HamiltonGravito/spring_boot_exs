package com.hamilton.github.albums1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

@SpringBootTest(properties = { "football.api.url=http://localhost:7979" })
public class FootballClientServiceTests {

	private static WireMockServer wireMockServer;
	@BeforeAll
	static void init() {
		wireMockServer = new WireMockServer(7979);
		wireMockServer.start();
		WireMock.configureFor(7979);
	}

	@Autowired
	FootballClientService footballClientService;

	@Test
	public void getPlayerTest() {

		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/players/325636"))
				.willReturn(WireMock.aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody("""
								{
								    "id": "325636",
								    "jerseyNumber": 11,
								    "name": "Alexia PUTELLAS",
								    "position": "Midfielder",
								    "dateOfBirth": "1994-02-04"
								}
								""")));
		Optional<Player> player = footballClientService.getPlayer("325636");
		Player expectedPlayer = new Player("325636", 11, "Alexia PUTELLAS", "Midfielder", LocalDate.of(1994, 2, 4));
		assertEquals(expectedPlayer, player.get());
	}
}
