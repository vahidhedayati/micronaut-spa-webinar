<template>
  <div :class="userInfoClass" v-if="auth && auth.loggedIn">
    <span><label>Username:</label> {{auth.username}}</span>
    <span><label>Token Type:</label> {{auth.tokenType}}</span>
    <span><label>Expiration:</label> {{auth.expirationTime}} ms</span>

    <span><a href="#" @click="logout">[Logout]</a></span>
  </div>
  <img alt="Micronaut logo" src="./assets/logo.png" style="max-width: 480px;">
  <LoginForm @login="login" v-if="!auth.loggedIn"/>
  <ProductList v-else :username="auth.username" :roles="auth.roles" />
<div v-if="loggedIn">
  <div id='nav' >
    <router-link to='/'> Home</router-link>
    <router-link to='/contact'>Contact </router-link>
  </div>
  <router-view />
</div>
</template>

<script>
import {computed, reactive, toRefs} from "vue";
import { useStore } from "vuex";
import LoginForm from "./components/LoginForm";
import ProductList from "./components/ProductList";

export default {
  components: {
    LoginForm,
    ProductList
  },
  setup() {
    const store = useStore();
    const auth = computed(() => store.state);
    const userInfoClass = {'user-info': true, 'admin-info': computed(() => store.state.roles.includes('ROLE_ADMIN'))}

    const state = reactive({
      loggedIn: auth.loggedIn,
      username: auth.username,
      roles: auth.roles,
      tokenType: auth.tokenType,
      expirationTime: auth.expirationTime,
    });

    function login(response) {
      console.log('emitted response from LoginForm')
    }

    function logout() {
      store.commit("logout");
    }
    return { ...toRefs(state),userInfoClass, login,logout, auth};
  }
};
</script>
<style>
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  .user-info {
    height: 25px;
    padding-top: 6px;
    background-color: aquamarine;
    font-size: smaller;
  }

  .admin-info {
    background-color: lightcoral!important;
  }

  .user-info span {
    margin-left: 20px;
    margin-right: 20px;
  }

  .user-info span label {
    font-weight: bold;
  }
  img {
    width: 200px;
  }

  #nav {
    font-size: 1.5em;
    margin-bottom: 30px;
  }

  a {
    text-decoration: none;
    margin: 30px 25px;
    color: #333;
  }

  a:hover {
    text-decoration: underline;
    color:  #333;
  }
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  .user-info {
    height: 25px;
    padding-top: 6px;
    background-color: aquamarine;
    font-size: smaller;
  }

  .admin-info {
    background-color: lightcoral!important;
  }

  .user-info span {
    margin-left: 20px;
    margin-right: 20px;
  }

  .user-info span label {
    font-weight: bold;
  }
</style>