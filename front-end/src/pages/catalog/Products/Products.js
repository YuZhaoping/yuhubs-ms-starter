import React, { useRef } from 'react';


import Layout from '../Layout';
import View from './View';


const tab = 'products';

const Products = (props) => {

  const pageRef = useRef();

  return (
    <Layout toTab={ tab } pageRef={ pageRef } >
      <View ref={ pageRef } />
    </Layout>
  );
};


export default Products;
