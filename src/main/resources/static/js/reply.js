let replyIndex = {
    init : function (){
        $("#btn-reply-save").on("click", ()=>{
            this.replySave();
        });
        $("#btn-reply-delete").on("click", ()=>{
            this.replyDelete();
        });
    },

    replySave: function() {
        let data = {
            userId: $("#userId").val(),
            boardId: $("#boardId").val(),
            content: $("#reply-content").val()
        };

        $.ajax({
            type: "POST",
            url: `/api/board/${data.boardId}/reply`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(res) {
            alert("댓글작성이 완료되었습니다.");
            location.href = `/board/${data.boardId}`;
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
    replyDelete: function() {
        let data = {
            boardId: $("#boardId").val(),
            replyId: $("#replyId").val()
        };

        $.ajax({
            type: "DELETE",
            url: `/api/board/${data.boardId}/reply/${data.replyId}`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(res) {
            alert("댓글삭제가 완료되었습니다.");
            location.href = `/board/${data.boardId}`;
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
}
replyIndex.init();