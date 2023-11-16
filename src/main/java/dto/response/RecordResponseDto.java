package dto.response;

import com.pacemaker.eta.domain.entity.Attention;
import java.time.Duration;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecordResponseDto {
    private String totalTime;
    private int distractionCount;
    private int attentionCount;

    public RecordResponseDto(Duration duration, int distractionCount, int attentionCount) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();

        this.totalTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        this.distractionCount = distractionCount;
        this.attentionCount = attentionCount;
    }
}
