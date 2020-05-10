import React from 'react'
import { Button, Form, Grid, Header, Image, Message, Segment, Label } from 'semantic-ui-react'
import { NavLink, Redirect} from 'react-router-dom'

class SignupForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: null,
      email: null,
      password: null,
      signupError: false,
      nameError: false,
      passwordError: false,
      emailError: false
    };
  }

  handleNameChange = (_, input) => {
    if (input.value.trim() === '') {
      this.setState({ nameError: true });
    } else {
      this.setState({ name: input.value, nameError: false });
    }
  }

  handlePasswordChange = (_, input) => {
    if (input.value.trim().length < 10) {
      this.setState({ passwordError: true });
    } else {
      this.setState({ password: input.value, passwordError: false });
    }
  }

  handleEmailChange = (_, input) => {
    if (input.value.trim() === '') {
      this.setState({ emailError: true });
    } else {
      this.setState({ email: input.value, emailError: false });
    }
  }

  handleSubmit = (_, input) => {
    if (this.state.nameError || this.state.emailError ||
      this.state.passwordError) {
      return;
    }

    fetch("/api/auth/signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify({
        name: this.state.name,
        emailId: this.state.email,
        password: this.state.password
      })
    })
      .then(res => {
        if (!res.ok) {
          throw new Error();
        }
        return res.json()
      })
      .then(res => {
        localStorage.setItem("currentUser", JSON.stringify(res))
      })
      .catch(error => {
        this.setState({ signupError: true })
      });
  }

  render() {
    if(localStorage.getItem('currentUser')) {
      return <Redirect to="/dashboard"></Redirect>
    }
    return (
      <Grid textAlign='center' style={{ height: '100vh' }} verticalAlign='middle'>
        <Grid.Column style={{ maxWidth: 450 }}>
          <Header as='h2' color='teal' textAlign='center'>
            <Image src='/favicon.png' /> Create your account
          </Header>
          <Form
            size='large'
            onSubmit={this.handleSubmit}>
            <Segment stacked>
              <Form.Input
                fluid
                icon='user'
                iconPosition='left'
                error={this.state.nameError ? {
                  content: 'Name can not be blank',
                  pointing: 'below'
                } : this.state.signupError ? true
                    : null}
                onChange={this.handleNameChange}
                placeholder='Name' />
              <Form.Input
                fluid
                icon='mail'
                iconPosition='left'
                error={this.state.emailError ? {
                  content: 'Email Id can not be blank',
                  pointing: 'below'
                } : this.state.signupError ? true
                    : null}
                onChange={this.handleEmailChange}
                placeholder='E-mail address' />
              <Form.Input
                fluid
                icon='lock'
                iconPosition='left'
                error={this.state.passwordError ? {
                  content: 'Password should be atleast 10 characters',
                  pointing: 'below'
                } : this.state.signupError ? true
                    : null}
                onChange={this.handlePasswordChange}
                placeholder='Password'
                type='password'
              />

              <Button
                color='teal'
                fluid
                size='large'>
                Sign Up
              </Button>
            </Segment>
          </Form>
          <Message>
            Already have an account? <Label as={NavLink} to="/login">Log In</Label>
          </Message>
        </Grid.Column>
      </Grid>
    );
  }

}

export default SignupForm