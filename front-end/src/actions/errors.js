import {
  PUBLISH_ERROR,
  ADD_ERROR,
  REMOVE_ERROR,
  ADD_ERROR_TO_LIST,
  REMOVE_ERROR_FROM_LIST,
  DECREASE_ERROR_COUNT,
  ON_ADD_ERROR,
  SET_CURRENT_ERROR,
  CLEAN_CURRENT_ERROR
} from 'Constants/errors';


const publishError = (error, nextAction) => ({
  type: PUBLISH_ERROR,
  error,
  nextAction
});

export const addError = (error) => ({
  type: ADD_ERROR,
  error
});

export const removeError = (error) => ({
  type: REMOVE_ERROR,
  error
});


export const addErrorToList = (error) => ({
  type: ADD_ERROR_TO_LIST,
  error
});

export const removeErrorFromList = (error) => ({
  type: REMOVE_ERROR_FROM_LIST,
  error
});


export const decreaseErrorCount = () => ({
  type: DECREASE_ERROR_COUNT
});


export const onAddError = (error) => ({
  type: ON_ADD_ERROR,
  error
});

export const setCurrentError = (error) => ({
  type: SET_CURRENT_ERROR,
  error
});

export const cleanErrorCount = () => ({
  type: CLEAN_CURRENT_ERROR
});


export default publishError;
