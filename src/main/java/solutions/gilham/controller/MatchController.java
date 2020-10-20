package solutions.gilham.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import solutions.gilham.dto.AccountDto;
import solutions.gilham.dto.MatchDto;
import solutions.gilham.webclient.LORMatchesClient;

@RestController
public class MatchController {
    private final LORMatchesClient client;

    public MatchController(LORMatchesClient client) {
        this.client = client;
    }

    @GetMapping("/matches/{gameName}/{tagLine}")
    public Flux<MatchDto> matchesByUsername(@PathVariable String gameName, @PathVariable String tagLine, @RequestParam(required = false, defaultValue = "1") int limit) {
        return client.getPuuidByUsername(gameName, tagLine)
                .map(AccountDto::getPuuid)
                .flatMap(client::getMatchesByPuuid)
                .flatMapMany(Flux::fromIterable)
                .take(limit)
                .flatMap(client::getMatchById);
    }
}
