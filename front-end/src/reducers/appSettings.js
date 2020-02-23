import {
  SWITCH_APP
} from 'Constants/appSettings';


const initialState = {
  currentApp: ''
};

export default (state = initialState, action) => {
  switch (action.type) {
    case SWITCH_APP:
      return { ...state, currentApp: action.appVar };
    default:
      return state;
  }
};
