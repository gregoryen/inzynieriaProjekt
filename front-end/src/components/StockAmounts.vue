<template>
    <div id="warehouse-stocks" class="container">
        <h1>Stany magazynowe</h1>
        <table id="stocks" align="center" class="container">
            <thead>
            <tr>
                <td>Nazwa</td>
                <td>Ilość</td>
                <td>Jednostka</td>
                <td>Dostępny</td>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(stock, index) in stocks" v-bind:item="stock" v-bind:key="index">
                <td><strong>{{stock.name}}</strong></td>
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
    const PRODUCTS = 'products/search/findAllByActiveIsTrue?projection=header';

    export default {
        created: async function () {
            const _stocks = await this.getStocks();
            const products = await this.getProducts();
            this.mergeStocksWithProducts(_stocks, products);
        },
        data: function () {
            return {
                stocks: []
            }
        },
        methods: {
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
                    stocks.push({
                        'name': products.filter(p => (p.id === _stocks[i].productId))[0].name,
                        'amount': _stocks[i].amount,
                        'measure': _stocks[i].measure,
                        'available': _stocks[i].available ? "tak" : "nie"
                    });
                }
                this.stocks = stocks;
            }
        }
    };
</script>