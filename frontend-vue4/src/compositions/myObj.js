import { reactive, toRefs } from "vue";
export default function() {
  const object = reactive({ foo: "bar" });
  return toRefs(object);
}
