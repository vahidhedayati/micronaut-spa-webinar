<template>
    <div class="content">

        <h1 v-if="isAdmin">Administrative Dashboard</h1>
        <h1 v-else>Inventory <span v-if="tenant">for {{tenant}}</span></h1>

        <loading :active.sync="isLoading" :is-full-page="false"></loading>

        <form v-if="isAdmin">
            <label for="tenant">Select Tenant:</label>
            <select id="tenant" v-model="tenant">
                <option value="">Select...</option>
                <option value="acme">acme</option>
                <option value="makers">makers</option>
            </select>
        </form>

        <hr/>

        <table style="width: 800px;">
            <tr>
                <th>Name</th>
                <th>Company</th>
            </tr>
            <tr v-for="product in products" :key="product.name">
                <td>{{product.name}}</td>
                <td>{{product.company}}</td>
            </tr>
        </table>


    </div>
</template>
<script>
    import Loading from 'vue-loading-overlay';
    import 'vue-loading-overlay/dist/vue-loading.css';

    export default {
        name: "ProductList",
        data() {
            return {
                tenant: this.username,
                products: [],
                isLoading: false,
            }
        },
        props: ["username", "roles"],
        components: {
            Loading
        },
        created() {
            if(this.tenant) {
                this.loadProducts();
            }
        },
        methods: {
            loadProducts() {
                this.isLoading = true;

                fetch("http://localhost:8080/products/", {
                    method: "GET",
                    headers: {
                        Accept: "application/json",
                        'tenantId': this.tenant,
                        Authorization: `Bearer ${localStorage.getItem("access_token")}`
                    }
                }).then(response => response.json())
                    .then(json => {

                        setTimeout(() => {
                            this.products = json;
                            this.isLoading = false;
                        }, 1000);

                    })
                    .catch(error => {
                        console.warn(error);
                        this.isLoading = false;
                    });
            }
        },
        watch: {
            tenant() {
                this.loadProducts();
            }
        },
        computed: {
            isAdmin() {
                return (this.roles.includes('ROLE_ADMIN'));
            }
        }

    }
</script>
<style>
    .content {
        margin: 0 auto;
        width: 800px;
    }
</style>
