import {
  SWITCH_LANGUAGE
} from 'Constants/customizings';


const initialState = {
  currentLocale: {
    languageId: 'english',
    localeKey: 'en',
    name: 'English',
    icon: 'us'
  }
};

export default (state = initialState, action) => {
  switch (action.type) {
    case SWITCH_LANGUAGE:
      return { ...state, currentLocale: action.payload };
    default:
      return state;
  }
};
