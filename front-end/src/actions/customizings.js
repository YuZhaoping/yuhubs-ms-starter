import {
  SWITCH_LANGUAGE
} from 'Constants/customizings';


export const switchLanguage = (locale) => (
  { type: SWITCH_LANGUAGE, payload: locale }
);
