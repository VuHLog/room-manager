import { createApp } from "vue";
import "@assets/scss/main.scss";
import App from "./App.vue";
import router from "./router.js";
// Vuetify
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import { aliases, fa } from "vuetify/iconsets/fa-svg";
import "@mdi/font/css/materialdesignicons.css";

import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { fas } from "@fortawesome/free-solid-svg-icons";
import { far } from "@fortawesome/free-regular-svg-icons";

//bootstrap
import "bootstrap/dist/css/bootstrap.css";
import bootstrap from "bootstrap/dist/js/bootstrap.bundle.js";

//pinia
import { createPinia } from "pinia";

//api
import { base } from "./apis/ApiService.js";

//Sweetalert
import VueSweetalert2 from "vue-sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";

//google login
import vue3GoogleLogin from "vue3-google-login";

const pinia = createPinia();
const app = createApp(App);

app.component("font-awesome-icon", FontAwesomeIcon); // Register component globally
library.add(fas); // Include needed solid icons
library.add(far); // Include needed regular icons

const vuetify = createVuetify({
  components,
  directives,
  icons: {
    defaultSet: "fa",
    aliases,
    sets: {
      fa,
    },
  },
  theme: {
    primary: "#283046",
  },
});

app.use(router);
app.use(vuetify);
app.use(pinia);
app.use(bootstrap);
app.use(VueSweetalert2);
app.use(vue3GoogleLogin, {
  clientId:
    "692742684673-ntqlvn9b4b153p0b4i2oc5fqf4ff2ndl.apps.googleusercontent.com",
});

const config = app.config;
config.globalProperties.$api = base;

app.mount("#app");
