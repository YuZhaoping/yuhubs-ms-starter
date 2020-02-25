import {
  SIGNIN_USER,
  SIGNIN_USER_SUCCESS,
  SIGNIN_USER_FAILURE,
  SIGNOUT_USER,
  SIGNOUT_USER_SUCCESS,
  SIGNOUT_USER_FAILURE,
  SIGNUP_USER,
  SIGNUP_USER_SUCCESS,
  SIGNUP_USER_FAILURE,
  CLEAN_AUTH_ERROR,
  MARK_AUTH_STATE,
  SET_AUTH_USERNAME
} from 'Constants/auth';


import {
  startRefreshTokenTaskAction
} from 'Actions/auth';


import auth from 'Services/auth';


export const startAuthAction = (store) => {
  const { auth } = store.getState();

  if (auth && auth.user) {
    store.dispatch(startRefreshTokenTaskAction());
  }
};


/**
 * initial auth user
 */
const initialAuthState = () => {
  const authUser = auth.loadAuthUser();

  return {
    loading: false,
    user: authUser,
    username: authUser ? authUser.name : '',
    fromPath: null,
    error: null
  };
};

export default (state = initialAuthState(), action) => {
  switch (action.type) {
    case SIGNIN_USER:
    case SIGNOUT_USER:
    case SIGNUP_USER:
      return { ...state, loading: true };

    case SIGNIN_USER_SUCCESS:
    case SIGNUP_USER_SUCCESS:
      {
        const authUser = action.payload;
        return { ...state,
          loading: false,
          user: authUser,
          username: authUser.name,
          error: null
        };
      }

    case SIGNIN_USER_FAILURE:
      return { ...state,
        loading: false,
        error: action.payload
      };

    case SIGNUP_USER_FAILURE:
      return { ...state,
        loading: false,
        username: action.username,
        error: action.payload
      };

    case SIGNOUT_USER_SUCCESS:
      return { ...state,
        loading: false,
        user: null,
        error: null
      };

    case SIGNOUT_USER_FAILURE:
      return { ...state,
        loading: false,
        user: null,
        error: action.payload
      };

    case CLEAN_AUTH_ERROR:
      return { ...state, error: null };


    case MARK_AUTH_STATE:
      return { ...state,
        fromPath: action.fromPath,
        error: action.error
      };

    case SET_AUTH_USERNAME:
      return { ...state,
        username: action.username
      };


    default:
      return state;
  }
};
