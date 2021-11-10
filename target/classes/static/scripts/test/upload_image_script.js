$(document).ready(function (){
    $("#send-photo").on("click", function (){
        $("#file-input").trigger("click");
    });

    $("#file-input").on("change", function (){
        if(!this.files[0]){
            alert("Вы не выбрали файл");
            return false;
        }
        if(this.files[0].size > 1048576){
            alert("Максимально допустимый размер файла 1Мб");
            return false;
        }
        $("#send-photo-form").trigger("click");
    });

});