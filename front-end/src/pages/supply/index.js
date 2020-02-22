import React from 'react';
import { Redirect } from 'react-router-dom';


const Default = () => (
  <Redirect to={'/app/supply/suppliers'} />
);


export default Default;
