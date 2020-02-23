import {
  ADD_ERROR_TO_LIST,
  REMOVE_ERROR_FROM_LIST,
  DECREASE_ERROR_COUNT,
  ON_ADD_ERROR,
  SET_CURRENT_ERROR,
  CLEAN_CURRENT_ERROR
} from 'Constants/errors';


/**
 * initial state
 */
const initialErrorsList = {
  errors: []
};

const initialErrorsProfile = {
  errorCount: 0,
  currentError: null,
  isNew: false
};


const addError = function(list, error) {
  list.unshift(error);
  return list;
};

const removeError = function(list, error) {
  for (let i = 0; i < list.length; ++i) {
    if (list[i] === error) {
      list.splice(i, 1);
      break;
    }
  }

  return list;
};

export const errorsList = (state = initialErrorsList, action) => {
  switch (action.type) {
    case ADD_ERROR_TO_LIST:
      {
        const errors = addError(state.errors, action.error);
        return {
          errors: errors
        };
      }

    case REMOVE_ERROR_FROM_LIST:
      {
        const errors = removeError(state.errors, action.error);
        return {
          errors: errors
        };
      }

    default:
      return state;
  }
};


export const errorsProfile = (state = initialErrorsProfile, action) => {
  switch (action.type) {
    case DECREASE_ERROR_COUNT:
      {
        const count = state.errorCount - 1;
        return { ...state,
          errorCount: count,
          isNew: false
        };
      }

    case ON_ADD_ERROR:
      {
        const count = state.errorCount + 1;
        return { ...state,
          errorCount: count,
          currentError: action.error,
          isNew: true
        };
      }

    case SET_CURRENT_ERROR:
      return { ...state,
        currentError: action.error,
        isNew: false
      };

    case CLEAN_CURRENT_ERROR:
      return { ...state,
        currentError: null,
        isNew: false
      };

    default:
      return state;
  }
};
