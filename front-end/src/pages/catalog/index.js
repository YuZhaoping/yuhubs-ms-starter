import React from 'react';
import { Redirect } from 'react-router-dom';


const Default = () => (
  <Redirect to={'/app/catalog/categories/common'} />
);


export default Default;
