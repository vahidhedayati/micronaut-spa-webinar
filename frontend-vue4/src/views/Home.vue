<template>
  <div>
    Home


    <p>Count: {{ count }}
      <button @click="increment()">Increment</button>
      <button @click="decrement()">Decrement</button>
    </p>
    <HelloWorld msg="MOTD TADA"/>


    <button @click="toggleModalState">Open modal</button>
    <teleport to="#modal-wrapper">
      <modal v-if="modalOpen">
        <p>Hello, I'm a modal window.</p>
      </modal>
    </teleport>


  </div>
</template>
<script>
  import Modal from "./Modal.vue";
  import { computed } from "vue";
  import { useStore } from "vuex";
  import HelloWorld from '../components/HelloWorld'
  import { ref, Teleport } from "vue";
  export default {
    setup () {
      const store = useStore();
      const count = computed(() => store.state.count);

      function increment() {
        store.commit("increment");
      }

      function decrement() {
        store.commit("decrement");
      }

      const modalOpen = ref(false);
      const toggleModalState = () => {
        modalOpen.value = !modalOpen.value;
      };


      return {
        count,
        increment,
        decrement,
        modalOpen,
        toggleModalState
      }
    },
    components: {
      Modal,
      Teleport,
      HelloWorld
    }
  };
</script>