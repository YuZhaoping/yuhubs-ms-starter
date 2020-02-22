import React from 'react';


import makeStyles from '@material-ui/core/styles/makeStyles';

import clsx from 'clsx';


import styled from '@material-ui/core/styles/styled';


const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    alignItems: 'center'
  }
}));


const BrandIcon= styled('div')({
  textAlign: 'center',
  '& img': {
    width: 24,
    height: 24
  }
});

const BrandName = styled('div')(({
  theme
}) => ({
  whiteSpace: 'nowrap',
  padding: theme.spacing(1, 4)
}));


const BrandBar = (props) => {
  const { className } = props;

  const classes = useStyles();

  return (
    <div className={clsx(classes.root, className)} >
      <BrandIcon>
        <img src={require("Assets/img/logo.png")} />
      </BrandIcon>
      <BrandName>ECOM APP</BrandName>
    </div>
  );
};


export default BrandBar;
