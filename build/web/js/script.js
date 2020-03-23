function evSearch()
{
    event.preventDefault(); // evita refresh da tela
      
    jQuery.ajax(
    {
        type: "POST",
        url: "executaEvento?item_id="+$('#item_id').val(),
        success: function (data) 
        {
            $('#result').html(data);
        }
    });
    return true;
}