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
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
