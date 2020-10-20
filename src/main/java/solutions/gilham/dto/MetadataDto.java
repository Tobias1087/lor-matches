package solutions.gilham.dto;

import lombok.Data;

import java.util.List;

@Data
public class MetadataDto {
    private String data_version;
    private String match_id;
    private List<String> participants;
}
