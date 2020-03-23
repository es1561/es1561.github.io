function evSearch()
{
    event.preventDefault(); // evita refresh da tela
    let frm = $("#form_search");    
    jQuery.ajax(
    {
        type: "POST",
        url: "executaEvento?evento=atualizaGridPiada",
        data: frm.serialize(),
        success: function (data) 
        {
            $('#containerPiadas').html(data);
        }
    });
    return true;
}