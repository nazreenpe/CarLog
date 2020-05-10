import React from 'react'
import { Button, Form, Grid, Header, Image, Message, Segment, Label } from 'semantic-ui-react'
import { NavLink } from 'react-router-dom'

class LoginForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      hasSubmited: false,
      email: null,
      password: null
    }
  }

  doLogin = (e, value) => {
    console.log(this.state);
    fetch("/api/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      }
    })
    .then(res => res.json())
    .then(res => console.log(res));
  }

  handleEmail = (e, input) => {
    this.setState({email: input.value})
  }

  handlePassword = (e, input) => {
    this.setState({password: input.value})
  }

  render() {
    return (
      <Grid textAlign='center' style={{ height: '100vh' }} verticalAlign='middle'>
        <Grid.Column style={{ maxWidth: 450 }}>
          <Header as='h2' color='teal' textAlign='center'>
            <Image src='/favicon.png' /> Log-in to your account
          </Header>
          <Form size='large' onSubmit={this.doLogin}>
            <Segment stacked>
              <Form.Input fluid icon='mail'
                iconPosition='left'
                placeholder='E-mail address' 
                onChange={this.handleEmail}
                />
              <Form.Input
                fluid
                icon='lock'
                iconPosition='left'
                placeholder='Password'
                type='password'
                onChange={this.handlePassword}
              />

              <Button color='teal' fluid size='large'>
                Login
          </Button>
            </Segment>
          </Form>
          <Message>
            New to us? <Label as={NavLink} to="/signup">Sign Up</Label>
          </Message>
        </Grid.Column>
      </Grid>
    )
  }
}

export default LoginForm