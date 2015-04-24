var dataEURUSD;
var dataEURGBP;
var dataEURJPY;

var volumeAll;

$('#EURUSDLiveRate').highcharts({
    chart: {
        type: 'line',
        events: {
            load: function () {
                dataEURUSD = this.series[0];
            }
        }
    },
    title: {
        text: "EURUSD"
    },
    xAxis: {
        type: 'datetime',
        minRange: 60 * 1000
    },
    yAxis: {
        title: {
            text: "Rate"
        }//,
        // max: 100.0
    },
    legend: {
        enabled: false
    },
    plotOptions: {
        series: {
            threshold: 0,
            marker: {
                enabled: false
            }
        }
    },
    series: [
        {
            name: 'EUR/USD',
            data: [ ]
        }
    ]
});

$('#EURGBPLiveRate').highcharts({
    chart: {
        type: 'line',
        events: {
            load: function () {
                dataEURGBP = this.series[0];
            }
        }
    },
    title: {
        text: "EURGBP"
    },
    xAxis: {
        type: 'datetime',
        minRange: 60 * 1000
    },
    yAxis: {
        title: {
            text: "Rate"
        }//,
        // max: 100.0
    },
    legend: {
        enabled: false
    },
    plotOptions: {
        series: {
            threshold: 0,
            marker: {
                enabled: false
            }
        }
    },
    series: [
        {
            name: 'EUR/GBP',
            data: [ ]
        }
    ]
});


$('#EURJPYLiveRate').highcharts({
    chart: {
        type: 'line',
        events: {
            load: function () {
                dataEURJPY = this.series[0];
            }
        }
    },
    title: {
        text: "EURJPY"
    },
    xAxis: {
        type: 'datetime',
        minRange: 60 * 1000
    },
    yAxis: {
        title: {
            text: "Rate"
        }//,
        // max: 100.0
    },
    legend: {
        enabled: false
    },
    plotOptions: {
        series: {
            threshold: 0,
            marker: {
                enabled: false
            }
        }
    },
    series: [
        {
            name: 'EUR/JPY',
            data: [ ]
        }
    ]
});

$(function () {
    $('#volumeBars').highcharts({
        chart: {
            type: 'column',
            margin: 75,
            options3d: {
                enabled: true,
                alpha: 10,
                beta: 25,
                depth: 70
            }, events: {
                load: function () {
                    volumeAll = this.series[0];
                }
            }
        },
        title: {
            text: 'You can see live (interval of 1 sec) the total volume for the 3 main currency pairs'
        },

        plotOptions: {
            column: {
                depth: 25
            }
        },
        xAxis: {
            categories: ["EURUSD","EURGBP", "EURYEN"]
        },
        yAxis: {
            title: {
                text: null
            }
        },
        series: [{
            name: 'Volume',
            data: []
        }]
    });
});



var rateSocket = new SockJS('/currencyfair/analyticsEndPoint');
var rateClient = Stomp.over(rateSocket);

rateClient.connect('kosmas', 'beros', function (frame) {

    rateClient.subscribe("/analyticsData", function (message) {
        var messageJson = JSON.parse(message.body);

        var point;
        var shift;
        if (messageJson.type == 'R') {

            switch (messageJson.pair) {
                case 'EUR/USD':
                    point = [ (new Date()).getTime(), parseFloat(messageJson.rate) ];
                    shift = dataEURUSD.data.length > 60;
                    dataEURUSD.addPoint(point, true, shift);
                    break;
                case 'EUR/GBP':
                    point = [ (new Date()).getTime(), parseFloat(messageJson.rate) ];
                    shift = dataEURGBP.data.length > 60;
                    dataEURGBP.addPoint(point, true, shift);
                    break;
                case 'EUR/JPY':
                    point = [ (new Date()).getTime(), parseFloat(messageJson.rate) ];
                    shift = dataEURJPY.data.length > 60;
                    dataEURJPY.addPoint(point, true, shift);
                    break;
            }
        }else if(messageJson.type == 'V'){
            var pointEURUSD =  parseFloat(messageJson.volumes[0].volume) ;
            var pointEURGB =   parseFloat(messageJson.volumes[2].volume) ;
            var pointEURJPY =  parseFloat(messageJson.volumes[1].volume) ;
       }

    });

});
