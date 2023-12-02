package dto.response;

import java.time.Duration;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DayAttentionResponse {
    LocalDate date;
    Long attentionTime;

    public DayAttentionResponse(LocalDate date, Duration attentionTime) {
        this.date = date;
        this.attentionTime = formatTime(attentionTime);
    }

    private static Long formatTime(Duration duration) {
        return duration.getSeconds();
    }

}
