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
        <!--        <div>-->
        <!--            <input type="file">-->
        <!--        </div>-->
        <h3>Raporty</h3>
        <div v-for="(report, index) in reports" v-bind:item="report" v-bind:key="index">
            <strong>{{report.disabled}}</strong>

            <table align="center" class="container">
                <thead>
                <tr>
                    <td>Utworzony:</td>
                    <td>Czas początkowy:</td>
                    <td>Czas końcowy:</td>
                    <td></td>
                    <td></td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>{{report.creationDateTime}}</td>
                    <td>{{report.startDateTime}}</td>
                    <td>{{report.endDateTime}}</td>
                    <td>
<!--                        <form>-->
<!--                            <div class="custom-file">-->
<!--                                <input type="file" class="custom-file-input" placeholder="Eksport">-->
<!--                                <label class="custom-file-label">Wybierz plik</label>-->
<!--                            </div>-->
<!--                        </form>-->
<!--                        <download-csv-->
<!--                                :data   = "json_data">-->
<!--                            Download Data-->
<!--                            <img src="download_icon.png">-->
<!--                        </download-csv>-->
                        <download-csv
                                class   = "btn btn-default"
                                :data   = "getReport(index)"
                                name    = "filename.csv"
                                @click = "download(index)"
                        >
                            pobierz
                            <img src="../assets/download_icon.png">
                        </download-csv>
                    </td>
                    <td>
                        <button class="form-control" @click="disable(index)">pokaż</button>
                    </td>
                </tr>
                </tbody>
            </table>
            <ul v-if="report.disabled === true">
                <li align="left" v-for="(change, index) in report.changes" v-bind:item="change" v-bind:key="index">
                    {{change}}
                <li/>
            </ul>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import Vue from 'vue';
    import JsonCSV from 'vue-json-csv';

    Vue.component('downloadCsv', JsonCSV);

    const URL = 'http://localhost:8100';
    const STOCKS = '/stock_amounts/last_updated';
    const PRODUCTS = '/products/search/findAllByActiveIsTrue?projection=header';
    const CREATE_REPORT = '/reports/create';
    const GET_REPORTS = '/reports/all';

    export default {

        created: async function () {
            const _stocks = await this.getStocks();
            const products = await this.getProducts();
            await this.getReports();
            this.mergeStocksWithProducts(_stocks, products);
        },
        data: function () {
            return {
                reports: [],
                dateTimes: {
                    startDateTime: "",
                    endDateTime: ""
                },
                stocks: []
            }
        },
        methods: {
            getReport: function (index) {
                return this.reports[index];
            },
            disable: function (index) {
                Vue.set(this.reports[index], 'disabled', !this.reports[index].disabled);
                this.reports = this.reports.slice();
            },
            getReports: async function () {
                const reports = await axios.get(URL + GET_REPORTS, {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json"
                });

                this.reports = reports.data;

                for (let i = 0; i < this.reports.length; i++) {
                    this.reports[i].creationDateTime = this.parseLocalDateTime(this.reports[i].creationDateTime);
                    this.reports[i].startDateTime = this.parseLocalDateTime(this.reports[i].startDateTime);
                    this.reports[i].endDateTime = this.parseLocalDateTime(this.reports[i].endDateTime);
                    this.reports[i].disabled = true;
                }

                // eslint-disable-next-line no-console
                console.log(this.reports);
            },
            parseLocalDateTime(dateTime) {
                let res = dateTime;
                res = res.replace("T", " ");
                res = res.substr(0, 16);
                return res;
            },
            createReport: async function () {
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

                    let res = await this.postReport(start, end);
                    // eslint-disable-next-line no-console
                    console.log(res);

                    if (res.data.status === "failed") {
                        alert("Raport nie został utworzony, brak stanów magazynowych w podanym zakresie")
                    } else if (res.data.status === "created") {
                        alert("Raport utworzono");
                        let tmp = res.data.changes.split(';');
                        this.reports.push(
                            {
                                reportId: res.data.reportId,
                                creationDateTime: res.data.creationDateTime,
                                startDateTime: res.data.startDateTime,
                                endDateTime: res.data.endDateTime,
                                changes: tmp
                            }
                        );
                        // eslint-disable-next-line no-console
                        console.log(this.reports);
                    }
                }
            },
            postReport: async function (start, end) {
                return await axios.post(URL + CREATE_REPORT + "?startDateTime=" + start + "&endDateTime=" + end, {}, {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json"
                });
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