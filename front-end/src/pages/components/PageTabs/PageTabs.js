import React, { useState, useEffect } from 'react';
import { Link as RouterLink } from 'react-router-dom';


import { useIntl } from 'react-intl';


import withStyles from '@material-ui/styles/withStyles';

import MuiTabs from '@material-ui/core/Tabs';
import MuiTab from '@material-ui/core/Tab';


import sizes from 'Components/sizes';

const tabsHeight = sizes.pageTabsHeight;


const Tabs = withStyles(theme => ({
  root: {
    minHeight: tabsHeight
  },

  indicator: {
    display: 'flex',
    justifyContent: 'center',
    backgroundColor: 'transparent',

    '& > div': {
      backgroundColor: theme.palette.common.white,
      maxWidth: theme.spacing(12),
      width: '100%'
    }
  }

}))(props =>
  <MuiTabs {...props}
    TabIndicatorProps={{ children: <div /> }}
  />
);


const Tab = withStyles(theme => ({
  root: {
    textTransform: 'none',
    minHeight: tabsHeight,
    minWidth: 0,
    padding: '2px 12px',
    fontWeight: theme.typography.fontWeightRegular,

    '&:hover': {
      color: theme.palette.common.white,
      opacity: 1
    },

    '&$selected': {
      fontWeight: theme.typography.fontWeightMedium
    },

    '&:focus': {
    }
  },

  selected: {}

}))(props =>
  <MuiTab disableRipple {...props} />
);


const Link = React.forwardRef((props, ref) => (
  <RouterLink innerRef={ref} {...props} />
));


const TabLink = (props) => (
  <Tab
    component={ Link }
    {...props}
  />
);


const PageTabs = (props) => {
  const { tabs, toTab } = props;

  const intl = useIntl();


  const [currentTab, setCurrentTab] = useState(toTab);

  const handleTabsChange = (event, value) => {
    setCurrentTab(value);
  };

  useEffect(() => {
    if (toTab !== currentTab) {
      setCurrentTab(toTab);
    }
  }, [toTab]);


  return (
    <Tabs
      value={ currentTab }
      onChange={ handleTabsChange }
      centered={true}
      scrollButtons="auto"
    >
      {tabs.map((tab, index) => (
        <TabLink key={ index }
          label={ intl.formatMessage({id: tab.label}) }
          value={ tab.value }
          to={ tab.path }
        />
      ))}
    </Tabs>
  );
};


export default PageTabs;
