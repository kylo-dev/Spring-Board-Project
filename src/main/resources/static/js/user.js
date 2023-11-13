let index = {
    init : function () {
        $("#btn-save").on("click", ()=>{
            this.save();
        });
        $("#btn-update").on("click", ()=>{
            this.update();
        });
    },

    save: function (){
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        $.ajax({
            type: "POST",
            url: "/auth/api/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(res){
            if (res.data == 1){
                alert("회원가입이 완료되었습니다.");
                location.href = "/";
            } else{
                alert("이미 존재하는 회원입니다.");
                location.href = "/auth/joinForm";
            }
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },

    update : function (){
        let data = {
            id: $("#id").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        $.ajax({
            type: "PATCH",
            url: "/api/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res) {
            alert("회원수정이 완료되었습니다.");
            location.href = "/";
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    }
}

index.init();