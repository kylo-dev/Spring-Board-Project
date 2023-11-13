package springblog.myblog.domain;

import lombok.*;
import springblog.myblog.domain.common.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

    @Column
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @OrderBy("id desc")
    private List<Reply> replies = new ArrayList<>();

    //== 연관관계 메소드 ==//
    public void setUser(User user){
        this.user = user;
        user.getBoards().add(this);
    }

    public void addReplies(Reply reply) {
        replies.add(reply);
        reply.setBoard(this);
    }

}
