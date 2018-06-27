import { sortDownPerson,sortUpPerson,sortFirstPerson,queryFakeList,queryPerson,addPerson,removePerson } from '../services/pmapi';

export default {
  namespace: 'person',

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
  },

  effects: {
    *fetch({ payload }, { call, put }) {
        const response = yield call(queryPerson, payload);
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
    },
    *add({ payload, callback }, { call, put }) {
      if(payload.imgUrl.file){
        payload.imgUrl = payload.imgUrl.file.response.data;
      }
      const response = yield call(addPerson, payload);
      if (callback) callback();
    },
    *remove({ payload, callback }, { call, put }) {
      const response = yield call(removePerson, payload);
      
      if (callback) callback();
    },
    *sortUp({ payload, callback }, { call, put }) {
      const response = yield call(sortUpPerson, payload);
      
      if (callback) callback();
    },
    *sortDown({ payload, callback }, { call, put }) {
      const response = yield call(sortDownPerson, payload);
      
      if (callback) callback();
    },
    *sortFirst({ payload, callback }, { call, put }) {
      const response = yield call(sortFirstPerson, payload);
      
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
    appendList(state, action) {
      return {
        ...state,
        list: state.list.concat(action.payload),
      };
    },
  },
};
