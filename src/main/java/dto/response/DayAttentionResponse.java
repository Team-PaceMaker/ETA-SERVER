package dto.response;

import java.time.Duration;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DayAttentionResponse {
    LocalDate date;
    String attentionTime;

    public DayAttentionResponse(LocalDate date, Duration attentionTime) {
        this.date = date;
        this.attentionTime = formatTime(attentionTime);
    }

    private static String formatTime(Duration duration) {
        long seconds = duration.getSeconds();

        return String.format("%d", seconds);
    }

}
