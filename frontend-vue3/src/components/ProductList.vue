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
    import {useStore} from "vuex";
    import {computed,ref, watch, reactive, toRefs} from "vue";
    export default {
        name: "ProductList",
        props: ["username", "roles"],

        setup() {
            const store = useStore();
            const isAdmin= computed(() => store.state.roles.includes('ROLE_ADMIN'));
            const tenant = ref("");
            const state = reactive({
                tenant: null,
                products: [],
                isLoading: false,
            });
            loadProducts();

            function loadProducts() {
                state.isLoading = true;
                console.log('state '+tenant.value)
                if (!tenant.value) {
                    tenant.value=JSON.parse(JSON.stringify(computed(() => store.state.username)))
                }

                fetch("http://localhost:8080/products/", {
                    method: "GET",
                    headers: {
                        Accept: "application/json",
                        'tenantId': tenant.value,
                        Authorization: `Bearer ${localStorage.getItem("access_token")}`
                    }
                }).then(response => response.json())
                    .then(json => {

                        setTimeout(() => {
                            state.products = json;
                            state.isLoading = false;
                        }, 1000);

                    })
                    .catch(error => {
                        console.warn(error);
                        state.isLoading = false;
                    });
            }
            watch(tenant, newVal => {
                //console.log(' tn -- '+newVal+' '+tenant.value)
                loadProducts();
            })
            return { ...toRefs(state),isAdmin,tenant};
        }
    }
</script>
<style>
    .content {
        margin: 0 auto;
        width: 800px;
    }
</style>
