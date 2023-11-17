package dto.response;

import java.time.Duration;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecordResponseDto {

    private String totalTime;
    private String attentionTime;

    public RecordResponseDto(Duration totalTime, Duration attentionTime) {
        this.totalTime = formatTime(totalTime);
        this.attentionTime = formatTime(attentionTime);
    }

    private String formatTime(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
