<template>
  <div class="hello">
  <p><input v-bind:value="hello" type="text" /></p>
    <h7>msg : {{ msg }} | Hello: {{ hello }} |  Foo: {{ foo }} | object: {{ object }}</h7>
  </div>
</template>
<script>

import myObj from "../compositions/myObj";
import { ref, reactive } from "vue";

export default {
  setup() {
    const hello = ref("hi");
    const object = reactive({ foo: "bar" });
    const { foo } = myObj();
    return {
      hello,
      foo,
      object
    }
  },
  props: {
    msg: String
  }
};

</script>
<style>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
