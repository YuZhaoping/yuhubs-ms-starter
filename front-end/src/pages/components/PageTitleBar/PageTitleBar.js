import React from 'react';


import { FormattedMessage } from 'react-intl';


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


const GrowSpace = styled('div')({
  flexGrow: 1
});


const PlaceHold = styled('div')({
  width: '16px'
});


const PageTitleBar = (props) => {
  const { title, menus, tabs, ...rest } = props;

  const classes = useStyles();

  return (
    <div className={ classes.root } >
      { title &&
        <Title variant="h6">
          <FormattedMessage id={ title }/>{ ' /' }
        </Title> }
      <GrowSpace />
      { tabs && <PageTabs tabs={ tabs } { ...rest } /> }
      { menus
        ? <PageMenu menus={ menus } />
        : <PlaceHold />
      }
    </div>
  );
};


export default PageTitleBar;
