package springblog.myblog.dto.board;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveBoardDto {
    private String title;
    private String content;
}
