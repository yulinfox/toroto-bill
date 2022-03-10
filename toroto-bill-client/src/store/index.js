import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate'
Vue.use(Vuex);
const config = {
    plugins: [createPersistedState()],
    state: {
        isLogin: false,
        loginUser: '',
        token: ''
    },
    getters: {
        isLogin: state => state.isLogin,
        token: state => state.token,
        loginUser: state => state.loginUser
    },
    mutations: {
        updateLogin(state, payload) {
            state.isLogin = payload;
        },
        updateToken(state, payload) {
            state.token = payload;
        },
        updateUser(state, payload) {
            state.loginUser = payload;
        }
    },
    actions: {}
}
const store = new Vuex.Store(config);
export default store;