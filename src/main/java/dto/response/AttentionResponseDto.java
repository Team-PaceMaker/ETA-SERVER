package dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttentionResponseDto {
    private Long attentionId;

    public AttentionResponseDto(Long attentionId) {
        this.attentionId = attentionId;
    }

}
