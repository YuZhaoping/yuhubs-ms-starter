import React from 'react';


import 'regenerator-runtime/runtime';

import Loading from './Loading';


const sleep = m => new Promise(r => setTimeout(r, m));


export default function asyncComponent(importComponent) {

  class AsyncComponent extends React.PureComponent {

    constructor(props, context) {
      super(props, context);

      this.state = {
        component: null
      };
    }

    async componentDidMount() {
      const { default: component } = await importComponent();

      this.setState({
        component: component
      });
    }

    render() {
      const C = this.state.component;

      return C ? <C {...this.props} /> : <Loading />;
    }
  }

  return AsyncComponent;
}


export { sleep };
