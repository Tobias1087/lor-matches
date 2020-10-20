package solutions.gilham.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import solutions.gilham.dto.AccountDto;
import solutions.gilham.dto.MatchDto;

import java.util.List;

@Service
public class LORMatchesClient {

    @Value("${RIOT_TOKEN}")
    private String riotToken;

    private WebClient client = WebClient.builder()
            .baseUrl("https://europe.api.riotgames.com")
            .build();

    @Cacheable("users")
    public Mono<AccountDto> getPuuidByUsername(String gameName, String tagLine) {
        return client
                .get()
                .uri("/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}", gameName, tagLine)
                .header("X-Riot-Token", riotToken)
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(AccountDto.class)
                .cache();
    }

    public Mono<List<String>> getMatchesByPuuid(String puuid) {
        return client.get()
                .uri("lor/match/v1/matches/by-puuid/{puuid}/ids", puuid)
                .header("X-Riot-Token", riotToken)
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                });
    }
    @Cacheable("matches")
    public Mono<MatchDto> getMatchById(String matchId) {
        return client.get()
                .uri("lor/match/v1/matches/{matchId}", matchId)
                .header("X-Riot-Token", riotToken)
                .header("Accept", "application/json")
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> Mono.empty())
                .bodyToMono(MatchDto.class)
                .cache();
    }

}
