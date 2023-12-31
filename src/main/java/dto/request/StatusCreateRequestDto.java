package dto.request;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatusCreateRequestDto {
    private int status;
    private LocalDateTime currentTime;
    private Long stopAt;
}
