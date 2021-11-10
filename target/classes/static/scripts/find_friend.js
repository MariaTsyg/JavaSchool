$(document).ready(start);
function start(){
    const token = $("meta[name='_csrf']").attr("content");

    $('#find-friend').on('click', function (){
        let fio_input = $('#fio_input').val();
        let fio_input_arr_splitted = fio_input.split(' ');
        for (let i = 0; i < fio_input_arr_splitted.length; i++) {
            console.log(fio_input_arr_splitted[i]);
        }

        const filter_json = {
            filterName: fio_input_arr_splitted[0],
            filterLastName: fio_input_arr_splitted[1]
        };
        const filter_json_string = JSON.stringify(filter_json);
        console.log(filter_json_string);

        //ajax
        $.ajax({
            type: "POST",
            url : "/user/friends",
            headers: {"X-CSRF-TOKEN" : token},
            contentType : "application/json; charset=UTF-8",
            dataType : "json",
            cache : false,
            timeout : 600000,
            data: filter_json_string,
            success: function (objResp) {
                console.log(objResp)

                let resHtml = "";
                for(let i = 0; i < objResp.length; i++) {
                    resHtml = resHtml + createUserHtml(objResp[i].imgLink, objResp[i].fio, objResp[i].accountId);
                    console.log(objResp[i]);
                }
                $("#user-list-wrap").html(resHtml);

            },
            error: function (e) {
                console.log(e)
                console.log("error!")
            }
        });
    });


    //Событие подписки на пользователя
    $('#user-list-wrap').on('click', '.subscribe', function (){
        //Отправляем account_id того, на кого мы хотим подписаться
        const follow_button = $(this);
        const account_id = follow_button.attr('data-account-id');
        console.log(account_id);

        const account_id_json = {
            account_id: account_id,
        };

        const account_id_json_string = JSON.stringify(account_id_json);
        console.log(account_id_json_string);


        $.ajax({
            type: "POST",
            url : "/user/following",
            headers: {"X-CSRF-TOKEN" : token},
            contentType : "application/json; charset=UTF-8",
            dataType : "json",
            cache : false,
            timeout : 600000,
            data: account_id_json_string,
            statusCode: {
                200: function (objResp){
                    console.log(objResp)
                    console.log(200);
                    alert("Получилось, молодец");
                },
                405: function (e){
                    console.log(e)
                    console.log(405);
                    alert("C самим собой нельзя");
                },
                406: function (e){
                    console.log(e)
                    console.log(406);
                    alert("Такая сильная дружба недопустима");
                }
            }
        });


    });

}

function createUserHtml(img_link, fio, account_id){
    const res_html =
        `<div class="user-item">
            <img src="${img_link}" alt="Фото">
            <span class="fio-span">${fio}</span>
            <button data-account-id="${account_id}" class="subscribe">Подписаться</button>
        </div>`;
    return res_html;
}