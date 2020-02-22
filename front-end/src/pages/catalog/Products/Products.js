import React from 'react';


import Layout from '../Layout';


import Divider from '@material-ui/core/Divider';


const tab = 'products';

const Products = (props) => {

  return (
    <Layout toTab={ tab } >
      <Divider />
    </Layout>
  );
};


export default Products;
