<template>
    <div id="warehouse-stocks" class="container">
        {{dateTimes.startDateTime}}
        <table align="center" class="container">
            <thead>
            <tr>
                <td>Czas początkowy</td>
                <td>Czas końcowy</td>
                <td>Stwórz raport</td>
            </tr>
            </thead>
            <tbody>

            <tr>
                <td>
                    <input v-model="dateTimes.startDateTime" type="datetime-local" min="0001-01-01T00:00"
                           max="5000-01-01T00:00">

                </td>
                <td>
                    <input v-model="dateTimes.endDateTime" type="datetime-local" min="0001-01-01T00:00"
                           max="5000-01-01T00:00">

                </td>
                <td>
                    <button
                            :disabled='dateTimes.startDateTime === "" || dateTimes.endDateTime === ""'
                            class="btn btn-success form-control" v-on:click="createReport">Stwórz
                    </button>
                </td>
            </tr>

            </tbody>
        </table>
        {{dateTimes.endDateTime}}

        <h1>Stany magazynowe</h1>
        <table id="stocks" align="center" class="container">
            <thead>
            <tr>
                <td>Nazwa</td>
                <td>Ilość</td>
                <td>Jednostka</td>
                <td>Dostępny</td>
                <td>Czas aktualizacji</td>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(stock, index) in stocks" v-bind:item="stock" v-bind:key="index">
                <td><strong>{{stock.name}}</strong></td>
                <td><strong>{{stock.amount}}</strong></td>
                <td><strong>{{stock.measure}}</strong></td>
                <td><strong>{{stock.available}}</strong></td>
                <td><strong>{{stock.dateTimeForDisplay}}</strong></td>
            </tr>
            </tbody>
        </table>
        <!--        "dateTimes": {-->
        <!--        "startDateTime": "2019-12-11 19:15:16",-->
        <!--        "endDateTime": "2019-12-11 19:15:16",-->
        <!--        }-->
        <div>
            <input type="file">
        </div>
    </div>
</template>

<script>
    import axios from 'axios';

    const URL = 'http://localhost:8100';
    const STOCKS = '/stock_amounts/last_updated';
    const PRODUCTS = '/products/search/findAllByActiveIsTrue?projection=header';
    const CREATE_REPORT = '/reports/create';
    export default {
        created: async function () {
            const _stocks = await this.getStocks();
            const products = await this.getProducts();
            this.mergeStocksWithProducts(_stocks, products);
        },
        data: function () {
            return {
                dateTimes: {
                    startDateTime: "",
                    endDateTime: ""
                },
                stocks: []
            }
        },
        methods: {
            createReport: function () {
                if (Date.parse(this.dateTimes.startDateTime.toString()) >=
                    Date.parse(this.dateTimes.endDateTime.toString())) {
                    alert("data poczatkowa musi byc mniejsza niz koncowa");
                } else {
                    let start = this.dateTimes.startDateTime;
                    let end = this.dateTimes.endDateTime;
                    start = start.replace("T", "");
                    end = end.replace("T", "");
                    start += ":00";
                    end += ":00";
                    axios.post(URL + CREATE_REPORT + "?startDateTime=" + start + "&endDateTime=" + end, {
                        // dateTimes: {
                        //     startDateTime: start,
                        //     endDateTime: end
                        // }
                    }, {
                        "Access-Control-Allow-Origin": "*",
                        "Content-Type": "application/json"
                    }).then(response => {
                        // eslint-disable-next-line no-console
                        console.log(response)
                    }).catch(error => {
                        // eslint-disable-next-line no-console
                        console.log(error.response)
                    });
                }
            },
            getStocks: async function () {
                const _stocks = await axios.get(URL + STOCKS, {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json"
                });
                return _stocks.data;
            },
            getProducts: async function () {
                const products = await axios.get(URL + PRODUCTS, {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json"
                });
                return products.data._embedded.products;
            },
            mergeStocksWithProducts: function (_stocks, products) {
                let stocks = [];
                for (let i = 0; i < _stocks.length; i++) {
                    let dateTimeForDisplay = _stocks[i].dateTime.slice();
                    dateTimeForDisplay = dateTimeForDisplay.replace("T", " ");
                    dateTimeForDisplay = dateTimeForDisplay.substr(0, dateTimeForDisplay.length - 10);
                    stocks.push({
                        'name': products.filter(p => (p.id === _stocks[i].productId))[0].name,
                        'amount': _stocks[i].amount,
                        'measure': _stocks[i].measure,
                        'available': _stocks[i].available ? "tak" : "nie",
                        'dateTime': _stocks[i].dateTime,
                        'dateTimeForDisplay': dateTimeForDisplay
                    });
                }
                this.stocks = stocks;
            }
        }
    };
</script>