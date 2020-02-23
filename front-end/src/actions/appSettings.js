import {
  SWITCH_APP
} from 'Constants/appSettings';


export const switchApp = (appVar) => (
  { type: SWITCH_APP, appVar }
);
