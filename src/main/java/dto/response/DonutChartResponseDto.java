package dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DonutChartResponseDto {

    private int distractionCount;
    private int attentionCount;

    public DonutChartResponseDto(int distractionCount, int attentionCount) {
        this.distractionCount = distractionCount;
        this.attentionCount = attentionCount;
    }

}
