import { base } from '../apis/ApiService.js';

export default {
  install: (app) => {
    app.config.globalProperties.$api = base;
  }
};
