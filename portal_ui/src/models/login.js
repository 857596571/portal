import { fakeAccountLogin } from '../services/api';
import { setAuthority } from '../utils/authority';
import { mdfPwd } from '../services/pmapi';
import {message } from 'antd';

export default {
  namespace: 'login',

  state: {
    status: false,
  },

  effects: {
    *login({ payload }, { call, put }) {
      if(payload.name != 'admin'){
        message.error(`用户名密码不正确`);
        return;
      }
      const response = yield call(fakeAccountLogin, payload);
      yield put({
        type: 'changeLoginStatus',
        payload: {status: response.ok ?"ok":false, type: "account", currentAuthority: response.ok ?"admin":'guest'},
      });
      // Login successfully
      if (response.ok) {
        // 非常粗暴的跳转,登陆成功之后权限会变成user或admin,会自动重定向到主页
        // Login success after permission changes to admin or user
        // The refresh will automatically redirect to the home page
        // yield put(routerRedux.push('/'));
        window.location.reload();
      }else{
        message.error(`用户名密码不正确`);
      }
    },
    *logout(_, { put, select }) {
      try {
        // get location pathname
        const urlParams = new URL(window.location.href);
        const pathname = yield select(state => state.routing.location.pathname);
        // add the parameters in the url
        urlParams.searchParams.set('redirect', pathname);
        window.history.replaceState(null, 'login', urlParams.href);
      } finally {
        // yield put(routerRedux.push('/user/login'));
        // Login out after permission changes to admin or user
        // The refresh will automatically redirect to the login page
        yield put({
          type: 'changeLoginStatus',
          payload: {
            status: false,
            currentAuthority: 'guest',
          },
        });
        window.location.reload();
      }
    },
    *mdfPwd({ payload, callback }, { call, put }) {
      
      const response = yield call(mdfPwd, payload);
      if (callback) callback(response);
    },
  },

  reducers: {
    changeLoginStatus(state, { payload }) {
      setAuthority(payload.currentAuthority);
      return {
        ...state,
        status: payload.status,
        type: payload.type,
      };
    },
  },
};
