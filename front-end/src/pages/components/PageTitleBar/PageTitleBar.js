import React from 'react';


import makeStyles from '@material-ui/core/styles/makeStyles';
import withStyles from '@material-ui/styles/withStyles';
import styled from '@material-ui/core/styles/styled';

import Typography from '@material-ui/core/Typography';


import PageMenu from 'Pages/components/PageMenu';
import PageTabs from 'Pages/components/PageTabs';


import sizes from 'Components/sizes';

const titleBarHeight = sizes.pageTitleBarHeight;


const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    alignItems: 'center',
    height: `${titleBarHeight}px`
  }
}));


const Title = withStyles(theme => ({
  root: {
    marginRight: theme.spacing(12)
  }
}))(Typography);


const BlackSpace = styled('div')({
  flexGrow: 1
});


const PageTitleBar = (props) => {
  const { title, menus, tabs, ...rest } = props;

  const classes = useStyles();

  return (
    <div className={classes.root} >
      { title && <Title variant="h6">{title}</Title> }
      <BlackSpace />
      { tabs && <PageTabs tabs={tabs} { ...rest } /> }
      { menus && <PageMenu menus={menus} />}
    </div>
  );
};


export default PageTitleBar;
