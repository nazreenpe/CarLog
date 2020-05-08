import React from 'react'
import { Button, Form, Grid, Header, Image, Message, Segment, Label } from 'semantic-ui-react'
import { NavLink } from 'react-router-dom'

const SignupForm = () => (
  <Grid textAlign='center' style={{ height: '100vh' }} verticalAlign='middle'>
    <Grid.Column style={{ maxWidth: 450 }}>
      <Header as='h2' color='teal' textAlign='center'>
        <Image src='/favicon.png' /> Create your account
      </Header>
      <Form size='large'>
        <Segment stacked>
          <Form.Input fluid icon='user' iconPosition='left' placeholder='Name' />
          <Form.Input fluid icon='mail' iconPosition='left' placeholder='E-mail address' />
          <Form.Input
            fluid
            icon='lock'
            iconPosition='left'
            placeholder='Password'
            type='password'
          />

          <Button color='teal' fluid size='large'>
            Sign Up
          </Button>
        </Segment>
      </Form>
      <Message>
        Already have an account? <Label as={NavLink} to="/login">Log In</Label>
      </Message>
    </Grid.Column>
  </Grid>
)

export default SignupForm