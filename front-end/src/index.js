import React from 'react';
import ReactDOM from 'react-dom';


// Save a reference to the root element for reuse
const rootEl = document.getElementById('root');

const render = function() {
  const MainApp = require('./Main').default;

  ReactDOM.render(
    <MainApp />,
    rootEl
  );
};

if (module.hot) {
  module.hot.accept('./Main', () => {
    const NextApp = require('./Main').default;

    ReactDOM.render(
      <NextApp />,
      rootEl
    );
  });
}

render();
