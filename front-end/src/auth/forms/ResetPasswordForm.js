import React, { useState } from 'react';


import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';


const ResetPasswordForm = props => {
  const { classes, onSubmit } = props;


  const [formData, setFormData] = useState(() => ({
    email: ''
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
    return formData.email.length > 0;
  };


  return (
    <form className={classes.form} noValidate>
      <Grid container spacing={0}>

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
            autoFocus
          />
        </Grid>

        <Grid item xs={12}>
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
        RESET PASSWORD
      </Button>

    </form>
  );
};


export default ResetPasswordForm;
