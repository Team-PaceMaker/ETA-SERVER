package dto.response;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttentionTimeSlotResponseDto {

    private List<String> attentionSlots;

    public AttentionTimeSlotResponseDto(List<String> attentionSlots) {
        this.attentionSlots = attentionSlots;
    }

}
