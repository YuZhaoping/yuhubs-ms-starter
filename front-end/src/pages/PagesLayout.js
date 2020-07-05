import React from 'react';


import makeStyles from '@material-ui/core/styles/makeStyles';

import PageTitleBar from 'Pages/components/PageTitleBar';


const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    overflow: 'hidden',
    flexDirection: 'column'
  },

  pageHeader: {
    backgroundColor: theme.menubar.background,
    color: theme.menubar.color,
    padding: theme.spacing(0, 2)
  },

  pageContent: {
    flexGrow: 1,
    marginLeft: theme.spacing(2)
  }
}));


const PagesLayout = (props) => {
  const { children, ...rest } = props;

  const classes = useStyles();

  return (
    <div className={ classes.root } >
      <div className={ classes.pageHeader } >
        <PageTitleBar { ...rest } />
      </div>
      <div className={ classes.pageContent } >
        { children }
      </div>
    </div>
  );
};


export default PagesLayout;
