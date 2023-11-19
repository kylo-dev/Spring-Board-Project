package springblog.myblog.dto.board;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springblog.myblog.domain.Reply;
import springblog.myblog.dto.reply.ReplyDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardReplyDto {
    private Long board_id;
    private Long user_id;
    private List<ReplyDto> replies;

    public static ReplyDto convertDto(Reply reply){
        return ReplyDto.builder()
                .replyId(reply.getId())
                .userId(reply.getUser().getId())
                .content(reply.getContent())
                .build();
    }
}
