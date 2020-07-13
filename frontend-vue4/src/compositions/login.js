import { reactive, toRefs } from "vue";

const state = reactive({
    error: null,
    response: null,
    loaded: false,
    authenticating: false,
});

export default function authenticate(username,password) {
    const fetchData = async () => {
        state.authenticating=true
        try {
            const usersResponse = await fetch('http://localhost:8080/login', {
                method: "POST",
                headers: new Headers({
                    Accept: "application/json",
                    "Content-Type": "application/json"
                }),
                body: JSON.stringify({username: username, password: password})
            })
            state.response = await usersResponse.json();
        } catch (e) {
            state.error = e;
        } finally {
            state.authenticating = false;
        }
    }
    return { ...toRefs(state), fetchData };
}
