package dto.response;

import com.pacemaker.eta.domain.entity.Attention;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record DayAttentionResponse (

    LocalDate date,
    Long attentionTime
)
{

    public static DayAttentionResponse of(LocalDate date, Long attentionTime) {
        return new DayAttentionResponse(
            date,
            attentionTime
        );
    }
}
