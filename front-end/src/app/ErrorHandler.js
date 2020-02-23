import React, { useState, useEffect } from 'react';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';


import Loading from 'Components/Loading';


import {
  cleanAuthUser,
  markAuthState
} from 'Actions/auth';


const timeout = function (ms) {
  return new Promise((resolve, reject) => {
    setTimeout(resolve, ms, 'done');
  });
};


const fromPath = function(location) {
  const { pathname, search, hash } = location;

  let path = pathname;

  if (search) path += search;
  if (hash) path += hash;

  return path;
};


const ErrorHandler = (props) => {
  const { error, isNew } = props;


  const [isBusy, setBusy] = useState(false);


  useEffect(() => {
    if (error && isNew) {
      const statusCode = error.statusCode;

      if (statusCode && statusCode === 401) {
        if (!isBusy) {
          setBusy(true);

          props.cleanAuthUser();

          timeout(200).then((value) => {
            setBusy(false);

            const { history } = props;
            if (history) {
              const { location } = history;
              props.markAuthState(fromPath(location), error);

              history.push('/auth/signin?logout');
            }
          });
        }
      }
    }
  }, [ error, isNew ]);


  return (
    <React.Fragment>
      { isBusy && <Loading /> }
    </React.Fragment>
  );
};


const mapStateToProps = ({ errorsProfile }) => {
  const { currentError, isNew } = errorsProfile;

  return { error: currentError, isNew };
};

export default withRouter(connect(mapStateToProps, {
  cleanAuthUser,
  markAuthState
})(ErrorHandler));
