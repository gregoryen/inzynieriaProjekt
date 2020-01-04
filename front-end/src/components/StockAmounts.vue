<template>
    <div id="warehouse-stocks" class="container">
        <h1>Stany magazynowe</h1>
        <table id="stocks" align="center" class="container">
            <thead>
            <tr>
                <!--                <td>Nazwa</td>-->
                <td>Ilość</td>
                <td>Jednostka</td>
                <td>Dostępność</td>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(stock, index) in stocks" v-bind:item="stock" v-bind:key="index">
                <td><strong>{{stock.amount}}</strong></td>
                <td><strong>{{stock.measure}}</strong></td>
                <td><strong>{{stock.available}}</strong></td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import axios from 'axios';

    const URL = 'http://localhost:8100/';
    const STOCKS = 'stock_amounts/last_updated';

    export default {
        created: function () {
            this.getStocks();
        },
        data: function () {
            return {
                stocks: []
            }
        },
        methods: {
            getStocks: function () {
                axios.get(URL + STOCKS, {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json"
                }).then((response) => {
                    this.stocks = response.data;
                    // eslint-disable-next-line no-console
                    console.log(response)
                });
            }
        }
    };
</script>