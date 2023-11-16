package dto.response;

import com.pacemaker.eta.domain.entity.Attention;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecordResponseDto {

    private String totalTime;
    private int distractionCount;
    private int attentionCount;
    private String attentionTime;

    public RecordResponseDto(Duration totalTime, int distractionCount, int attentionCount, Duration attentionTime) {
        this.totalTime = formatTime(totalTime);
        this.distractionCount = distractionCount;
        this.attentionCount = attentionCount;
        this.attentionTime = formatTime(attentionTime);
    }

    private String formatTime(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
