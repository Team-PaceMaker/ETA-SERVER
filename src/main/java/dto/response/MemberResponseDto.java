package dto.response;

import com.pacemaker.eta.domain.entity.UserRole;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String name;
    private String accessToken;
    private UserRole userRole;

    public MemberResponseDto(Long id, String name, String accessToken,
        UserRole userRole) {
        this.id = id;
        this.name = name;
        this.accessToken = accessToken;
        this.userRole = userRole;
    }

}
