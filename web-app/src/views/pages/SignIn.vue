<script setup>
import { ref, getCurrentInstance, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useBaseStore } from "@/store/index.js";
import TokenService from "@/service/TokenService.js";

const { proxy } = getCurrentInstance();

const store = useBaseStore();

const router = useRouter();
const route = useRoute();

const redirect = route.query.redirect ? route.query.redirect : "/home";

onMounted(() => {
  if (store.username !== "") {
    router.push(redirect);
  }

  // proxy.$api
  //   .get("/users")
  //   .then((res) => {})
  //   .catch(() => (errMsg.value = "Tài khoản hoặc mật khẩu không chính xác"));
  // proxy.$api
  //   .post("/users/registration", "abc")
  //   .then((res) => {})
  //   .catch(() => (errMsg.value = "Tài khoản hoặc mật khẩu không chính xác"));
  //   proxy.$api
  //   .put("/users/registration", "abc")
  //   .then((res) => {})
  //   .catch(() => (errMsg.value = "Tài khoản hoặc mật khẩu không chính xác"));
});

const errMsg = ref("");
const user = ref({
  username: "",
  password: "",
});
async function signIn() {
  errMsg.value = "";
  let userVal = user.value;
  if (userVal.username === "" || userVal.password === "") {
    errMsg.value = "Phải nhập tài khoản và mật khẩu";
    return;
  }

  await proxy.$api
    .post("/auth/token", userVal)
    .then((res) => {
      TokenService.setLocalAccessToken(res.result.token);
      store.isLoggedIn = true;
      router.push(redirect);
    })
    .catch(() => (errMsg.value = "Tài khoản hoặc mật khẩu không chính xác"));
}
</script>

<template>
  <div class="width-form-login d-flex flex-column">
    <div class="text-center mb-6">
      <h1>Quản lý phòng trọ</h1>
    </div>
    <div class="d-flex w-100">
      <div
        class="text-center bg-white text-purple-accent-4 text-16 pa-5 rounded-ts-xl rounded-te-xl w-50 font-weight-bold"
      >
        Đăng nhập
      </div>
      <router-link
        to="/auth/sign-up"
        class="text-center bg-purple-accent-4 text-16 pa-5 rounded-ts-xl rounded-te-xl w-50 font-weight-bold text-decoration-none"
      >
        Đăng ký
      </router-link>
    </div>
    <div class="bg-white pa-4">
      <form method="POST" @submit.prevent="signIn()">
        <input
          v-model.trim="user.username"
          class="w-100 border-b-sm py-2 pl-1 border-0 form-input text-grey-darken-4 mt-6"
          type="text"
          placeholder="Tên đăng nhập"
        />
        <input
          v-model.trim="user.password"
          class="w-100 border-b-sm py-2 pl-1 border-0 form-input text-grey-darken-4 mt-6"
          type="password"
          placeholder="Mật khẩu"
        />
        <div class="mt-2 text-red-darken-1 text-14">
          {{ errMsg }}
        </div>
        <button
          class="bg-purple-accent-4 w-100 py-3 rounded-lg mt-10"
          type="submit"
        >
          Đăng nhập
        </button>
      </form>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
