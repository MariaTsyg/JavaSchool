$(document).ready(function (){
    const token = $("meta[name='_csrf']").attr("content");

    $('.heard-button').on('click', function (){
            let id = $(this).attr('id');
            id = id.replace("post-id-", "");

            const reqObj = {
                postId : id
            };

            $.ajax({
                type: "POST",
                url : "/post/like",
                headers: {'X-CSRF-TOKEN' : token},
                contentType : "application/json; charset=UTF-8",
                dataType : 'json',
                cache : false,
                timeout : 600000,
                data: JSON.stringify(reqObj),
                statusCode : {
                    200 : function (x){
                        const countLikeSpan = $('#post-count-like-id-'+id);
                        let countLike = $(countLikeSpan).text();
                        $(countLikeSpan).text(parseInt(countLike)+1);

                    },
                    400 : function (x){
                        console.log(x);

                        if($(x).attr("responseText") === "Already liked by You") {
                            alert("Нельзя так сильно лайкать");
                        }
                        if($(x).attr("responseText") === "Error") {
                            alert("Удивительно, но нет поста с таким Id");
                        }
                    }
                }
            });


        });
});