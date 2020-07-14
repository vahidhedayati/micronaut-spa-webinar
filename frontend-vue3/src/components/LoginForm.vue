<template>
    <form  @submit="login">
        <h1>Login</h1>

        <div v-if="message" class="alert">{{message}}</div>

        <label for="username">Username:</label>
        <input type="text" id="username" v-model="username" />
        <br/>
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" />

        <br />

        <input type="submit"  v-show="awaitSubmit" value="Login" />
    </form>

</template>
<script>
    import {useStore} from "vuex";
    import {computed, reactive, toRefs} from "vue";
    import authenticate from "../compositions/login";
    export default {
        name: 'LoginForm',
        setup() {
            const store = useStore();
            const state = reactive({
                username: '',
                password: '',
                message: null,
                awaitSubmit:true,
                loggedIn:computed(() => store.state.loggedIn)
            });
            function login(e) {
                e.preventDefault();
                state.awaitSubmit=false
                fetch('http://localhost:8080/login', {
                    method: "POST",
                    headers: new Headers({
                        Accept: "application/json",
                        "Content-Type": "application/json"
                    }),
                    body: JSON.stringify({username: state.username, password: state.password})
                })
                    .then(response => {
                        if (response.ok) {
                            return response.json();
                        } else {
                            state.message = "Invalid credentials"
                            state.awaitSubmit=true
                        }
                    })
                    .then(json=> {
                        store.commit("authUser", json)

                    })
                    //.then(json => this.$emit('login', json))
                    .catch(error =>  { state.message = error.message
                        state.awaitSubmit=true});
                /*
                const { response, error, message, fetchData } = authenticate(state.username,state.password);
                fetchData();

                const rsp = response.value
                if (rsp && rsp.access_token) {
                    this.$emit('login', rsp);
                    store.commit("authUser", rsp);
                } else {
                    state.awaitSubmit=true
                    if (error) {
                        state.message = error
                    } else {
                        state.message = "Invalid credentials"
                    }
                }
                */
            }
            return { ...toRefs(state),login};
        }
    }
</script>
<style scoped>
    div.alert {
        border: 1px solid red;
        color: red;
    }
</style>