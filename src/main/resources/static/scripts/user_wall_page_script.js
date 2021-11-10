$(document).ready(function () {
    const token = $("meta[name='_csrf']").attr("content");

    $("#buttonSendPostId").on("click", function () {
        console.log($("#floatingTextarea").val());
        const postText = $("#floatingTextarea").val();

        const ajaxText = {
            text: postText
        }

        const ajaxTextString = JSON.stringify(ajaxText);

        console.log(ajaxTextString);

        $.ajax({
            type: "POST",
            url : "/user/wall",
            headers: {'X-CSRF-TOKEN' : token},
            contentType : "application/json; charset=UTF-8",
            dataType : 'json',
            cache : false,
            timeout : 600000,
            data: ajaxTextString,
            statusCode : {
                200 : function (x){
                    $(".wall-post-button-div, .wall-post-button, .wall-post-textarea").hide("slow");
                    $("#floatingTextarea").val('');
                    createPostBlock(x.txt, x.dateAndTime, x.numberOfLikes);
                }
            }
        });
    });

    $("#createPostButtonId").on("click", function () {
        $(".wall-post-button-div, .wall-post-button,.wall-post-textarea").show("slow");
    });

    $("#buttonBackId").on("click", function () {
        $("#floatingTextarea").val("");
        $(".wall-post-button-div, .wall-post-button,.wall-post-textarea").hide("slow");
    });
});


function createPostBlock(txt, dateAndTime, numberOfLikes){
    console.log(txt)
    console.log(dateAndTime)
    console.log(numberOfLikes)

    const postHtml =
    `<div class="row justify-content-center mt-5">
            <div class="col-6 px-4 pt-2 pb-1 shadow bg-body rounded post-on-wall-textarea">
                <p class="text-end text-post-data">
                    ${dateAndTime}
                </p>
                <p>
                    ${txt}
                </p>
                <p class="text-end text-post-likes" style="margin-bottom: 0.3rem">
                    <i class="fas fa-heart"></i> <span>${numberOfLikes}</span>
                </p>
            </div>
        </div>`;
    const postContainer =  $("#post-container");
    $(postContainer).html(postHtml + $(postContainer).html());


}