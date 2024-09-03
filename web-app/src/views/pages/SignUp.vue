<script setup>
import { ref, getCurrentInstance, onMounted } from "vue";
import { useBaseStore } from "@/store/index.js";
import { useRouter, useRoute } from "vue-router";
import { inject } from "vue";

const swal = inject("$swal");

const { proxy } = getCurrentInstance();

const store = useBaseStore();

const router = useRouter();
const route = useRoute();

const errMsg = ref("");

const user = ref({
  username: "",
  password: "",
  avatarUrl: "",
  roles: [],
});

const redirect = route.query.redirect ? route.query.redirect : "/home";
onMounted(() => {
  if (store.username !== "") {
    router.push(redirect);
  }
});

// xử lý ảnh
const file = ref(null);
async function handleFileUpload(event) {
  file.value = event.target.files[0];
}

async function submitFile() {
  let formData = new FormData();

  formData.append("image", file.value);
  await proxy.$api
    .postFile("/cloudinary/upload", formData)
    .then((res) => {
      user.value.avatarUrl = res.url;
      console.log(res.url);
    })
    .catch((error) => console.log(error));
}

function isValidUserInfo() {
  errMsg.value = "";
  if (!/^.{8,}$/.test(user.value.username.trim())) {
    errMsg.value = "Tên đăng nhập phải có ít nhất 8 ký tự";
    return false;
  }
  if (
    !/^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$/.test(user.value.password.trim())
  ) {
    errMsg.value =
      "Mật khẩu phải có ít nhất 8 ký tự chứa ít nhất một ký tự viết hoa, viết thường và chữ số";
    return false;
  }
  if (passwordConfirm.value !== user.value.password) {
    errMsg.value = "Mật khẩu không khớp";
    return false;
  }
  return true;
}

const passwordConfirm = ref("");
async function signUp() {
  if (!isValidUserInfo()) {
    return;
  }
  if (file !== null) {
    await submitFile();
  } else {
    user.value.avatarUrl = store.avatarUserDefault;
  }

  await proxy.$api
    .post("/users/registration", user.value)
    .then((res) => {
      console.log("sign up");

      if (res.message) {
        errMsg.value = res.message;
      } else {
        swal.fire({
          title: "Đăng ký Thành Công!",
          icon: "success",
        });
        router.push("/auth/sign-in");
      }
    })
    .catch((error) => {
      errMsg.value = error.response.data.message;
      console.log(error);
    });
}
</script>

<template>
  <div class="width-form-login d-flex flex-column">
    <div class="text-center mb-6">
      <h1>Long Messenger</h1>
    </div>
    <div class="d-flex w-100">
      <router-link
        to="/auth/sign-in"
        class="text-center bg-purple-accent-4 text-16 pa-5 rounded-ts-xl rounded-te-xl w-50 font-weight-bold text-decoration-none"
      >
        Đăng nhập
      </router-link>
      <div
        class="text-center bg-white text-purple-accent-4 text-16 pa-5 rounded-ts-xl rounded-te-xl w-50 font-weight-bold"
      >
        Đăng ký
      </div>
    </div>
    <div class="bg-white pa-4">
      <form method="POST" @submit.prevent="signUp()">
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
        <input
          v-model.trim="passwordConfirm"
          class="w-100 border-b-sm py-2 pl-1 border-0 form-input text-grey-darken-4 mt-6"
          type="password"
          placeholder="Nhập lại mật khẩu"
        />
        <input
          class="w-100 py-2 pl-1 mt-6"
          type="file"
          id="formFile"
          accept=".png, .jpg, .jpeg"
          @change="handleFileUpload($event)"
        />
        <div class="mt-2 text-red-darken-1 text-14">
          {{ errMsg }}
        </div>
        <button
          class="bg-purple-accent-4 w-100 py-3 rounded-lg mt-10"
          type="submit"
        >
          Đăng ký
        </button>
      </form>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
