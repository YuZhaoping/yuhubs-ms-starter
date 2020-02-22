import React from 'react';
import { withRouter } from "react-router";


import Layout from '../Layout';


import Divider from '@material-ui/core/Divider';


const tab = 'categories';

const Categories = (props) => {
  const type = props.match.params.type;

  return (
    <Layout toTab={ `${type}-${tab}` } >
      <Divider />
    </Layout>
  );
};


export default withRouter(Categories);
