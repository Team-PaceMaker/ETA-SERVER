package dto.response;

import com.pacemaker.eta.domain.entity.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StatusResponseDto {
    private int prediction;

    public StatusResponseDto(int prediction) {
        this.prediction = prediction;
    }
}
