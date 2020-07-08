import React from 'react';


import { FormattedMessage } from 'react-intl';


import makeStyles from '@material-ui/core/styles/makeStyles';

import clsx from 'clsx';


import styled from '@material-ui/core/styles/styled';


const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    alignItems: 'center'
  }
}));


const BrandIcon= styled('div')(({
  theme
}) => ({
  padding: theme.spacing(1, 0, 0, 4),
  textAlign: 'center',
  '& img': {
    width: 24,
    height: 24
  }
}));


const BrandName = styled('div')(({
  theme
}) => ({
  whiteSpace: 'nowrap',
  padding: theme.spacing(1, 4, 1, 2)
}));


const BrandBar = (props) => {
  const { className, menuComponent, ...rest } = props;

  const classes = useStyles();

  const C = menuComponent;

  return (
    <div className={ clsx(classes.root, className) } >
      { C && <C {...rest} /> }
      <BrandIcon>
        <img src={ require("Assets/img/logo.png") } />
      </BrandIcon>
      <BrandName>
        <FormattedMessage id={ "app.name" }/>
      </BrandName>
    </div>
  );
};


export default BrandBar;
