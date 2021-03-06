var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-userSave').on('click', function () {
            _this.update();
        });

        $('#btn-check').on('click', function () {
            _this.check();
        });
        $('#btn-admin').on('click', function () {
            _this.gohome();
        });
        $('#btn-postsUpdate').on('click', function () {
            _this.postUpdate();
        });
        $('#btn-postsDelete').on('click', function () {
            _this.postDelete();
        });

    },
    save : function () {
        var adminId = $('#adminId').val();
        var data = {
            adminId: adminId,
            mission: $('#mission').val(),
            question: $('#question').val(),
            info: $('#info').val(),
            imgurl: $('#imgurl').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/edu-posts/save',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('미션이 등록되었습니다.');
            window.location.href = '/admin/'+adminId+'/posts/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var adminId = $('#adminId').val();
        var data = {
            adminId: adminId,
            userId: $('#userId').val()
        };


        $.ajax({
            type: 'POST',
            url: '/api/v1/user/register-admin-to-user',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(data) {
            alert('추가되었습니다!');
            // alert(JSON.stringify(data))
            window.location.href = '/admin/'+adminId+'/home';
            // var template = $("#template").html();
            // var render = Mustache.render(template, data);
            // $("#users").html(render);/admin/{adminId}/home



        }).fail(function (error) {
            alert("입력을 다시 확인해주세요");
        });
    },
    check : function () {
        var adminId = $('#adminId').val();
        window.location.href = '/users/check/'+adminId;

    },
    gohome:function (){
        var adminId = $('#adminId').val();

        $.ajax({
            type: 'GET',
            url: '/api/v1/admin/check/'+adminId,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function() {
            window.location.href = '/admin/'+adminId+'/home';
        }).fail(function (error) {
            alert("해당 ID는 없는 ID입니다.");
        });
    },
    postUpdate:function (){
        var id=$('#id').val();
        var adminId=$('#adminId').val();
        var data = {
            mission: $('#mission').val(),
            question: $('#question').val(),
            info: $('#info').val(),
            imgurl: $('#imgurl').val()
        };
        $.ajax({
            type: 'PUT',
            url: '/api/v1/edu-posts/update/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('미션이 수정되었습니다.');
            window.location.href = '/admin/'+adminId+'/posts/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },
    postDelete:function (){
        var id = $('#id').val();
        var adminId=$('#adminId').val();
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/edu-posts/delete/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('미션이 삭제되었습니다.');
            window.location.href = '/admin/'+adminId+'/posts/list';
        }).fail(function (error) {
            alert('원활한 미션을 위해 마지막 미션만 삭제가능하도록 했습니다. 마지막 미션이 아니라면 수정을 권합니다.');
        });
    }

};

main.init();
