import React from 'react';


import styled from '@material-ui/core/styles/styled';

import MuiList from '@material-ui/core/List';
import MuiListItem from '@material-ui/core/ListItem';
import MuiListItemText from '@material-ui/core/ListItemText';
import Grid from '@material-ui/core/Grid';
import Hidden from '@material-ui/core/Hidden';


import sizes from 'Components/sizes';

const footerHeight = sizes.footerHeight;


const Wrapper = styled('div')(({
  theme
}) => ({
  padding: `${theme.spacing(1) / 4}px ${theme.spacing(4)}px`,
  background: theme.footer.background,

  height: `${footerHeight}px`
}));


const List = styled(MuiList)({
  minHeight: 0,
  margin: 0,
  padding: 0
});

const ListItem = styled(MuiListItem)(({
  theme
}) => ({
  display: 'inline-block',
  width: 'auto',
  margin: 0,
  padding: theme.spacing(0, 2),

  '&, &:hover, &:active': {
    color: '#000'
  }
}));

const ListItemText = styled(MuiListItemText)(({
  theme
}) => ({
  color: theme.footer.color
}));


const Footer = () => {

  const year = new Date().getFullYear();

  return (
    <Wrapper>
      <Grid container spacing={0}>
        <Hidden smDown>
          <Grid container item xs={12} md={6}>
            <List>
              <ListItem component="a" href="#">
                <ListItemText
                  primary="Support"
                />
              </ListItem>
              <ListItem component="a" href="#">
                <ListItemText
                  primary="Help Center"
                />
              </ListItem>
              <ListItem component="a" href="#">
                <ListItemText
                  primary="Privacy"
                />
              </ListItem>
              <ListItem component="a" href="#">
                <ListItemText
                  primary="Terms of Service"
                />
              </ListItem>
            </List>
          </Grid>
        </Hidden>
        <Grid container item xs={12} md={6} justify="flex-end">
          <List>
            <ListItem>
              <ListItemText
                primary={`© ${year} - Zhaoping Yu`}
              />
            </ListItem>
          </List>
        </Grid>
      </Grid>
    </Wrapper>
  );
};


export default Footer;
