import { createStore } from 'vuex'
import {reactive} from "vue";

const state = {
  count: 0,
  loggedIn: false,
  username: null,
  roles: null,
  tokenType: null,
  expirationTime: null
}

const mutations = {
  increment (state) {
    state.count++
  },
  decrement (state) {
    state.count--
  },

  authUser(state, response) {
    if (response && response.username) {
      state.roles = response.roles;
      state.username = response.username;
      localStorage.setItem("access_token", response.access_token);
      localStorage.setItem("refresh_token", response.refresh_token);
      state.tokenType = response.token_type;
      state.expirationTime = response.expires_in;
      state.loggedIn = true;
    }
  },
  logout(state) {
    state.loggedIn = false;
    state.roles = null;
    state.username = null;
    state.tokenType = null;
    state.expirationTime = null;
  },
}

export default createStore({
  state,
  mutations
})
