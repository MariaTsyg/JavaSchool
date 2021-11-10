$(document).ready(function () {
    const token = $("meta[name='_csrf']").attr("content");

    $("#filterInputId").on("input", function () {
        const filterValue = $("#filterInputId").val();

        const filter_json = {
            filter: filterValue
        };
        const filter_json_string = JSON.stringify(filter_json);

        $.ajax({
            type: "POST",
            url : "/filter/username",
            headers: {"X-CSRF-TOKEN" : token},
            contentType : "application/json; charset=UTF-8",
            dataType : "json",
            cache : false,
            timeout : 600000,
            data: filter_json_string,
            success: function (objResp) {
                console.log(objResp)
                // const objResp = JSON.parse(jsonResp);

                  /*  $("td.user-name").each(function () {
                        for(let i = 0; i < objResp.length; i++) {
                            if($(this).text() === objResp[i].username) {
                                $(this).parent().attr('hidden', false);
                                break;
                            } else {
                                $(this).parent().attr('hidden', true);
                            }
                        }
                    });*/
                console.log()
                let resHtml = "";
                for(let i = 0; i < objResp.length; i++) {
                    resHtml = resHtml + createTableRow(objResp[i].id, objResp[i].username, objResp[i].firstName, objResp[i].lastName, objResp[i].moder, objResp[i].admin);
                }
                $("#users-table-tbody").html(resHtml);

            },
            error: function (e) {
                console.log(e)
                console.log("error!")
            }
        });
    });


    $("#users-table-tbody").on("click", ".remove-user", function () {
        const button_remove = $(this);

        console.log($(this));
        console.log(button_remove);
        console.log("Кликнул по удалению!")
        const user_id = $(button_remove).attr("data-user-id");
        const user_id_obj = {
            id: parseInt(user_id)
        };


        $.ajax({
            type: "POST",
            url : "/admin/remove",
            headers: {'X-CSRF-TOKEN' : token},
            contentType : "application/json; charset=UTF-8",
            dataType : 'json',
            cache : false,
            timeout : 600000,
            data: JSON.stringify(user_id_obj),
            statusCode: {
                200: function (){
                    $("#row-id-"+user_id).remove();
                }
            }
        });

    });

    $("#users-table-tbody").on("click", ".moder-status", function () {
        const checkbox = $(this);
        let is_moder;
        console.log("Клик по модеру");
        if($(checkbox).prop('checked')) {
            console.log("checked");
            is_moder = true;
        } else {
            console.log("unchecked");
            is_moder = false;
        }

        const user_id = $(checkbox).attr("data-user-id");

        console.log("------------------");
        console.log(user_id);
        console.log(is_moder);

        const req_data = {
            userId: parseInt(user_id),
            moderStatus: is_moder
        };

        const json_string = JSON.stringify(req_data);
        console.log(json_string);

        $.ajax({
            type: "POST",
            url : "/change/roles",
            headers: {'X-CSRF-TOKEN' : token},
            contentType : "application/json; charset=UTF-8",
            dataType : 'json',
            cache : false,
            timeout : 600000,
            data: json_string,
            success: function (jsonResp) {
                console.log(jsonResp.status)
                // const objResp = JSON.parse(jsonResp);
                // console.log(objResp.status);
            },
            error: function (e) {
                console.log(e)
                console.log("error!")
            }
        });
    })



});


function createTableRow(user_id, username, user_first_name, user_last_name, is_moder, is_admin){
    let checked_value = "";
    if(is_moder){
        checked_value = 'checked="checked"';
    }
    let hidden_value = "";
    if(is_admin) {
        hidden_value = "hidden='hidden'"
    }

    let row_html = `
    <tr id="row-id-${user_id}">
        <td class="user-id">${user_id}</td>
        <td class="user-name">${username}</td>
        <td class="user-first-name">${user_first_name}</td>
        <td class="user-last-name">${user_last_name}</td>
        <td>
        <div>
            <input class="form-check-input moder-status" type="checkbox" data-user-id="${user_id}" ${checked_value}>
        </div>
        </td>
           <td>
                <div class="form-check">
                    <button class="btn btn-danger btn-sm remove-user" data-user-id="${user_id}" ${hidden_value}>Удалить</button>
                </div>
           </td>
    </tr>
    `;

    return row_html;
}