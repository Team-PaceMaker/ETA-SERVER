package dto.response;

import com.pacemaker.eta.domain.entity.Attention;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttentionOutResponseDto {
    private Long attentionId;
    private LocalDateTime stopAt;

    public static AttentionOutResponseDto of(Attention attention) {
        return new AttentionOutResponseDto(attention.getAttentionId(), attention.getStopAt());
    }

}
