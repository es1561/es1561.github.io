
class Item {
    constructor(location, item_count, min, avg, max, time) {
        this.location = location;
        this.count = parseInt(item_count);
        this.min = parseInt(min);
        this.avg = Math.round(parseInt(avg));
        this.max = parseInt(max);
        this.time = time;
    }
}

function evSearch() {

    event.preventDefault()
    let url = "https://www.albion-online-data.com/api/v2/stats/history/" + $('#item_id').val() + "?qualities=1&time-scale=1";


    //const response = await fetch(url);
    //const myJson = await response.json(); //extract JSON from the http response

    var requestURL = url;
    var request = new XMLHttpRequest();
    request.open('GET', requestURL);
    request.responseType = 'json';
    $("#status").html('Carregando!!!')
    request.send();

    request.onload = function () {
        var myJson = request.response;
        let size = myJson.length;
        let tab = null;
        $("#status").html('')
        processaDados(myJson, size)

    }
}

function sortResults(data, prop, asc) {
    data.sort(function(a, b) {
        if (asc) {
            return (a[prop] > b[prop]) ? 1 : ((a[prop] < b[prop]) ? -1 : 0);
        } else {
            return (b[prop] > a[prop]) ? 1 : ((b[prop] < a[prop]) ? -1 : 0);
        }
    });
}

function processaDados(data, size) {
    let linha = "";
    let body = ''
    let head = `<table>
                    <tr>
                        <th>Location</th>
                        <th>Avg</th>
                    </tr>`;
    for (let i in data) {
        let avg = 0
        let itens = []
        let json_city = data[i];

        sortResults(json_city.data, 'avg_price', true)

        let location = json_city.location;

        let dados = json_city.data;

        for (let k = 0; k < dados.length; k++) {
            let json_item = dados[k];
            aux = new Item(location, json_item.item_count, "0", "" + Math.round(json_item.avg_price), "0", json_item.timestamp);
            itens.push(aux);
            avg += json_item.avg_price;
        }
        avg /= dados.length
        head += `
            <tr>
                <td> ${location} </td>
                <td> ${Math.round(avg)} </td>
            </tr>`;
        //falta ordenar
        
        itens.sort(function (a, b) {
            if (a.time > b.time) {
              return -1;
            }
            if (a.name < b.name) {
              return 1;
            }
            // a must be equal to b
            return 0;
          });
        
        line = `<table>
                        <tr>
                            <th>Location</th>
                            <th>Avg</th>
                        </tr>
                        <tr>
                            <td> ${location} </td>
                            <td> ${Math.round(avg)} </td>
                        </tr>
                </table>
                <br>`
        line += `<table>
                    <tr>
                        <th>Count</th>
                        <th>Avg</th>
                        <th>Time</th>
                    </tr>`

        let n = itens.length > 10 ? 10 : itens.length
        for (let i = 0; i < n; i++) {
            line +=
                `<tr>
                    <td>${itens[i].count}</td>
                    <td>${Math.round(itens[i].avg)}</td>
                    <td>${itens[i].time}</td>
                </tr>`

        }
        line += '</table><br>'
        body += line
    }
    head += '</table><br>';
    $("#result").html(head + body);
}

