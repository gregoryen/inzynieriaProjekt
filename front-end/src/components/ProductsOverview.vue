<template>
    <div>
    <Categories/>
    <Products/>
    </div>
</template>

<script>
    import Categories from "./Categories";
    import Products from "./Products";
    export default {
        name: "ProductsOverview",
        components: {Products, Categories}
    }
</script>

<style scoped>

</style>
