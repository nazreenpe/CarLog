import React from 'react';
import { Redirect, NavLink } from 'react-router-dom'
import {
  Button, Form, Grid, Header, Image, Message, Segment, Label, Container, Divider
} from 'semantic-ui-react'

class CarForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      value: props.startValue,
      hasSubmitted: false,
      make: '',
      model: '',
      year: null,
      trim: '',
      createdCar: null,
      failedToCreate: false
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event, input) {
    console.log("Setting value to", event.target.value);
    let name = event.target.name;
    let value = event.target.value;
    this.setState({ [name]: value });
  }

  handleSubmit(event) {
    this.setState({ hasSubmitted: true });

    fetch("/api/cars", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify({
        "make": this.state.make,
        "model": this.state.model,
        "year": this.state.year,
        "trim": this.state.trim
      })
    })
      .then(res => {
        if (!res.ok) {
          throw new Error()
        }
        return res.json()
      })
      .then(car => {
        this.setState({ createdCar: car })
        console.log(car)
      })
      .catch(error => {
        this.setState({ failedToCreate: true })
      })

    event.preventDefault();
  }

  render() {
    if (this.state.createdCar) {
      return <Redirect to={"/dashboard/cars/" + this.state.createdCar.id} />
    }

    return (
      <Container>
        <Button content="Back to cars"
          icon="left arrow"
          labelPosition='left'
          as={NavLink}
          to="/dashboard"
          push={true}
        />
        <Divider />
        <Grid textAlign='left' style={{ height: '100vh' }}>
          <Grid.Column style={{ maxWidth: 450 }}>
            <Form size='large' onSubmit={this.handleSubmit}>
              <Segment stacked>
                <Form.Input fluid icon='factory'
                  iconPosition='left'
                  placeholder='Make'
                  name="make"
                  onChange={this.handleChange}
                  error={this.state.failedToCreate}
                />
                <Form.Input fluid icon='car'
                  iconPosition='left'
                  placeholder='Model'
                  name="model"
                  onChange={this.handleChange}
                  error={this.state.failedToCreate}
                />
                <Form.Input fluid icon='car'
                  iconPosition='left'
                  placeholder='Year'
                  name="year"
                  onChange={this.handleChange}
                  error={this.state.failedToCreate}
                />
                <Form.Input fluid icon='car'
                  iconPosition='left'
                  placeholder='Trim'
                  name="trim"
                  onChange={this.handleChange}
                  error={this.state.failedToCreate}
                />
                <Button color='teal' fluid size='large'>
                  Go
              </Button>
              </Segment>
            </Form>
          </Grid.Column>
        </Grid>

      </Container>

    );
  }
}

export default CarForm;