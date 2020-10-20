package solutions.gilham.dto;

import lombok.Data;

@Data
public class PlayerDto {
    private String puuid;
    private String deck_code;
    private String game_outcome;
    private int order_of_play;
}
