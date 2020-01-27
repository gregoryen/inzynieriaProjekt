<template>
    <div id="warehouse-stocks" class="container">

        <h1>Tworzenie raportów</h1>
        <table id="create-report" align="center" class="container">
            <thead>
            <tr>
                <td>Czas początkowy</td>
                <td>Czas końcowy</td>
                <td></td>
            </tr>
            </thead>
            <tbody>

            <tr>
                <td align="center">
                    <div class="form-group col-md-6">
                        <input v-model="dateTimes.startDate" class="form-control" style=" border-right-style: none"
                               type="date"/>
                    </div>
                    <div class="form-group col-md-6">
                        <input v-model="dateTimes.startTime" class="form-control" style=" border-left-style: none"
                               type="time"/>
                    </div>

                </td>
                <td align="center">
                    <div class="form-group col-md-6">
                        <input v-model="dateTimes.endDate" class="form-control" style=" border-right-style: none"
                               type="date"/>
                    </div>
                    <div class="form-group col-md-6">
                        <input v-model="dateTimes.endTime" class="form-control" style=" border-left-style: none"
                               type="time"/>
                    </div>

                </td>
                <td>
                    <button
                            :disabled='dateTimes.startDate === "" || dateTimes.endDate === "" ||
                                        dateTimes.startTime === "" || dateTimes.endTime === ""'
                            class="btn btn-success form-control" v-on:click="createReport">Stwórz
                    </button>
                </td>
            </tr>

            </tbody>
        </table>

        <h1 id="stocks-h1">Stany magazynowe</h1>
        <table v-if="stocks.length > 0" id="show-stocks" align="center" class="container">
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
                <td><strong>{{stock.productName}}</strong></td>
                <td><strong>{{stock.amount}}</strong></td>
                <td><strong>{{stock.measure}}</strong></td>
                <td>
                    <strong v-if='stock.available === "true"'>Tak</strong>
                    <strong v-else>Nie</strong>
                </td>
                <td><strong>{{stock.dateTime}}</strong></td>
            </tr>
            </tbody>
        </table>
        <strong v-else>
            Brak stanów magazynowych
        </strong>
        <h3 id="reports-h3">Raporty</h3>
        <div v-if="reports.length > 0">
            <div id="show-reports" v-for="(report, index) in reports" v-bind:item="report" v-bind:key="index">
                <form>
                    <table align="center" class="container">
                        <thead>
                        <tr>
                            <td>Utworzony:</td>
                            <td>Czas początkowy:</td>
                            <td>Czas końcowy:</td>
                            <td></td>
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
                                <download-csv
                                        class="btn btn-default"
                                        :data="getReport(index)"
                                        name="filename.csv"
                                >
                                    pobierz
                                    <img src="../assets/download_icon.png">
                                </download-csv>
                            </td>
                            <td>
                                <button type="button" class="form-control" @click="disable(index)">
                                    <strong v-if="report.disabled">schowaj</strong>
                                    <strong v-else>pokaż</strong>
                                </button>
                            </td>
                            <td>
                                <button class="btn btn-danger form-control" @click="remove(index)">
                                    <strong>X</strong>
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </form>
                <table class="table" v-if="report.disabled === true">
                    <thead>
                    <tr>
                        <td></td>
                    </tr>
                    </thead>
                    <tr align="left" v-for="(change, index) in report.changes" v-bind:item="change" v-bind:key="index">
                        <td>{{change}}</td>
                    </tr>
                </table>
            </div>
        </div>
        <strong v-else>
            Brak raportów
        </strong>
    </div>
</template>

<script>
    import axios from 'axios';
    import Vue from 'vue';
    import JsonCSV from 'vue-json-csv';

    Vue.component('downloadCsv', JsonCSV);

    const URL = 'http://localhost:8100';
    // const STOCKS = '/stock_amounts/last_updated';
    // const PRODUCTS = '/products/search/findAllByActiveIsTrue?projection=header';
    const STOCKS_WITH_NAMES = '/stock_amounts/last_updated_with_names';
    const CREATE_REPORT = '/reports/create';
    const GET_REPORTS = '/reports/all';
    const DELETE_REPORT = '/reports/delete';

    export default {

        created: async function () {
            // const _stocks = await this.getStocks();
            // const products = await this.getProducts();
            this.getStocks();
            this.getReports();
            // this.mergeStocksWithProducts(_stocks, products);
        },
        data: function () {
            return {
                reports: [],
                dateTimes: {
                    startDate: "",
                    startTime: "",
                    endDate: "",
                    endTime: ""
                },
                stocks: []
            }
        },
        methods: {
            remove: function (index) {
                let reports = this.reports;
                let reportId = reports.splice(index, 1)[0].reportId;
                this.reports = reports.slice();

                this.deleteReport(reportId);
            },
            deleteReport: async function (reportId) {
                const data = await axios.delete(URL + DELETE_REPORT + '?reportId=' + reportId, {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json"
                });


                // eslint-disable-next-line no-console
                console.log(data);
            },
            getReport: function (index) {
                return JSON.parse("[" + JSON.stringify(this.reports[index]) + "]");
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
                    this.reports[i].disabled = false;
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
            joinDateWithTime: function (date, time) {
                date = date.toString();
                time = time.toString();
                let dateTime = date + "T" + time + ":00";

                return dateTime;
            },
            createReport: async function () {
                let startDateTime = this.joinDateWithTime(this.dateTimes.startDate, this.dateTimes.startTime);
                let endDateTime = this.joinDateWithTime(this.dateTimes.endDate, this.dateTimes.endTime);

                // eslint-disable-next-line no-console
                console.log(Date.parse(startDateTime));

                // eslint-disable-next-line no-console
                console.log(Date.parse(endDateTime));

                if (Date.parse(startDateTime) >=
                    Date.parse(endDateTime)) {
                    alert("data poczatkowa musi byc mniejsza niz koncowa");
                } else {
                    let start = startDateTime;
                    let end = endDateTime;
                    start = start.replace("T", "");
                    end = end.replace("T", "");
                    // start += ":00";
                    // end += ":00";

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
                                creationDateTime: this.parseLocalDateTime(res.data.creationDateTime),
                                startDateTime: this.parseLocalDateTime(res.data.startDateTime),
                                endDateTime: this.parseLocalDateTime(res.data.endDateTime),
                                changes: tmp
                            }
                        );
                    }
                    window.location.reload(false);
                }
            },
            postReport: async function (start, end) {
                return await axios.post(URL + CREATE_REPORT + "?startDateTime=" + start + "&endDateTime=" + end, {}, {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json"
                });
            },
            getStocks: async function () {
                const stocks = await axios.get(URL + STOCKS_WITH_NAMES, {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json"
                });

                // eslint-disable-next-line no-console
                console.log(stocks.data);

                this.stocks = stocks.data;
            }
        }
    };
</script>

<style scoped>

    #create-report {
        margin-top: 50px;
    }

    #stocks-h1 {
        margin-top: 100px;
    }

    #reports-h3 {
        margin-top: 100px;
    }

    #show-reports {
        margin-bottom: 30px;
    }

    #warehouse-stocks {
        margin-bottom: 150px;
    }

</style>