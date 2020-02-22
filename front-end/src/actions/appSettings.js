import {
  SWITCH_APP
} from 'Constants/appActions';


export const switchApp = (appVar) => (
  { type: SWITCH_APP, appVar }
);
