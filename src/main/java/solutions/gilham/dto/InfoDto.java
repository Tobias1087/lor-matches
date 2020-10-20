package solutions.gilham.dto;

import lombok.Data;

import java.util.List;

@Data
public class InfoDto {
    private String game_mode;
    private String game_type;
    private String game_start_time_utc;
    private List<PlayerDto> players;
    private int total_turn_count;
}
