package springblog.myblog.dto.reply;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveReplyDto {
    private Long userId;
    private Long boardId;
    private String content;
}
