import createMuiTheme from '@material-ui/core/styles/createMuiTheme';

import blue from '@material-ui/core/colors/blue';
import deepOrange from '@material-ui/core/colors/deepOrange';
import grey from '@material-ui/core/colors/grey';


const variants = [
  {
    name: "Blue",
    palette: {
      primary: {
        main: blue[700],
        dark: blue[800],
        contrastText: "#FFF"
      },
      secondary: {
        main: deepOrange[700],
        dark: deepOrange[800],
        contrastText: "#FFF"
      }
    },
    sidebar: {
      background: grey[50],
      color: grey[700]
    },
    menubar: {
      background: grey[200],
      dark: grey[500]
    },
    body: {
      background: grey[100]
    }
  }
];


const theme = variant => {
  const muiTheme = createMuiTheme(
    {
      palette: {
        ...variant.palette
      },
      spacing: 4,
      props: {
        MuiButtonBase: {
          disableRipple: true
        }
      },
      shadows: Array(25).fill("none"),
      sidebar: {
        ...variant.sidebar
      },
      menubar: {
        ...variant.menubar
      },
      body: {
        ...variant.body
      }
    },
    variant.name
  );

  return muiTheme;
};


const themes = variants.map(variant => theme(variant));

export default themes;
