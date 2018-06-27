import { getMainbusiness,sortDownMainbusiness,sortFirstMainbusiness,sortUpMainbusiness,queryFakeList,queryMainbusiness,addMainbusiness,removeMainbusiness } from '../services/pmapi';

export default {
  namespace: 'mainbusiness',

  state: {
    data: {
        list: [],
        pagination: {
          current:1,
          pageSize:20,
          defaultPageSize:20,
          total:0
        },
    },
    pList:[]
  },

  effects: {
    *fetch({ payload }, { call, put }) {
        const response = yield call(queryMainbusiness, payload);
        yield put({
            type: 'save',
            payload: {
              list:response.data.list,
              pagination:{
                current:Number(response.data.pageNum) || 1,
                pageSize:Number(response.data.pageSize) || 20,
                total:Number(response.data.total)||1,
              }

            },
        });
        const res = yield call(getMainbusiness, payload);
        yield put({
          type: 'savePList',
          payload: Array.isArray(res.data) ? res.data : [],
        });
    },
    *add({ payload, callback }, { call, put }) {
      if(payload.imgUrl.file){
        payload.imgUrl = payload.imgUrl.file.response.data;
      }
      const response = yield call(addMainbusiness, payload);
      if (callback) callback();
    },
    *remove({ payload, callback }, { call, put }) {
      const response = yield call(removeMainbusiness, payload);
      
      if (callback) callback();
    },
    *sortUp({ payload, callback }, { call, put }) {
      const response = yield call(sortUpMainbusiness, payload);
      
      if (callback) callback();
    },
    *sortDown({ payload, callback }, { call, put }) {
      const response = yield call(sortDownMainbusiness, payload);
      
      if (callback) callback();
    },
    *sortFirst({ payload, callback }, { call, put }) {
      const response = yield call(sortFirstMainbusiness, payload);
      
      if (callback) callback();
    },
    *appendFetch({ payload }, { call, put }) {
      const response = yield call(queryFakeList, payload);
      yield put({
        type: 'appendList',
        payload: Array.isArray(response) ? response : [],
      });
    },
  },

  reducers: {
    queryList(state, action) {
      return {
        ...state,
        list: action.payload,
      };
    },
    save(state, action) {
        return {
          ...state,
          data: action.payload,
        };
    },
    savePList(state, action){
      return {
        ...state,
        pList:action.payload
      }
    },
    appendList(state, action) {
      return {
        ...state,
        list: state.list.concat(action.payload),
      };
    },
  },
};
