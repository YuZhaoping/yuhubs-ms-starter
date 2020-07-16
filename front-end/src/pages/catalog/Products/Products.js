import React, { useRef } from 'react';


import Layout from '../Layout';
import View from './View';

import menus from './menus';


const tab = 'products';

const Products = (props) => {

  const pageRef = useRef();

  return (
    <Layout toTab={ tab } menus={ menus } pageRef={ pageRef } >
      <View ref={ pageRef } />
    </Layout>
  );
};


export default Products;
