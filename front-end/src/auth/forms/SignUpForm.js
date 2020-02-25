import React, { useState } from 'react';


import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';


const hasText = function(field) {
  if (field) {
    if (Object.prototype.toString.call(field) === '[object String]') {
      const str = field.trimStart().trimEnd();
      return str.length > 0;
    } else if (typeof field === 'object') {
      let ret = true;
      Object.keys(field).forEach(item => {
        ret = ret && hasText(field[item]);
      });
      return ret;
    }
  }
  return false;
};


const SignUpForm = props => {
  const { classes, onSubmit } = props;


  const [formData, setFormData] = useState(() => ({
    username: '',
    email: '',
    password: ''
  }));

  const handleFieldChange = event => {
    event.persist();
    setFormData(formData => ({
      ...formData,
      [event.target.name]:
        event.target.type === 'checkbox'
          ? event.target.checked
          : event.target.value
    }));
  };


  const isSubmitEnabled = () => {
    return hasText(formData);
  };


  return (
    <form className={classes.form} noValidate>
      <Grid container spacing={0}>

        <Grid item xs={12}>
          <TextField
            id="username"
            label="Username"
            name="username"
            onChange={ handleFieldChange }
            value={ formData.username }
            variant="outlined"
            margin="normal"
            required
            fullWidth
            autoFocus
          />
        </Grid>

        <Grid item xs={12}>
          <TextField
            id="email"
            label="Email Address"
            name="email"
            onChange={ handleFieldChange }
            value={ formData.email }
            autoComplete="email"
            variant="outlined"
            margin="normal"
            required
            fullWidth
          />
        </Grid>

        <Grid item xs={12}>
          <TextField
            id="password"
            label="Password"
            name="password"
            onChange={ handleFieldChange }
            value={ formData.password }
            type="password"
            variant="outlined"
            margin="normal"
            required
            fullWidth
          />
        </Grid>

        <Grid item xs={12}>
          <br/>
          <br/>
        </Grid>
      </Grid>

      <Button
        onClick={() => {
          isSubmitEnabled() && onSubmit(formData);
        }}
        fullWidth
        variant="contained"
        color="primary"
        className={classes.submit}
      >
        SIGN UP
      </Button>

    </form>
  );
};


export default SignUpForm;
