import React from 'react';


import makeStyles from '@material-ui/core/styles/makeStyles';


const useStyles = makeStyles(theme => ({
  root: {
    height: '100%',
    display: 'flex',
    flexDirection: 'column'
  },

  content: {
    flexGrow: 1,
    padding: theme.spacing(2, 4, 0, 2)
  }
}));


const Layout = (props) => {
  const { children, ...rest } = props;

  const classes = useStyles();

  return (
    <div className={ classes.root } >
      <div className={ classes.content }>
        ...Profile / [Gerneral | Security]...
      </div>
    </div>
  );
};


export default Layout;
