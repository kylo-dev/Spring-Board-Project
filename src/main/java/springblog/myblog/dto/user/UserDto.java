package springblog.myblog.dto.user;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserDto {
    private String username;
    private String email;
    private String role;
}
