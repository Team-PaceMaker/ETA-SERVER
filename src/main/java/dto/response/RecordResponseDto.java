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
    private List<LocalDateTime> attentionTimeList;

    public RecordResponseDto(Duration duration, int distractionCount, int attentionCount, List<LocalDateTime> attentionTimeList) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();

        this.totalTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        this.distractionCount = distractionCount;
        this.attentionCount = attentionCount;
        this.attentionTimeList = attentionTimeList;
    }
}
