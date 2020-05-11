import React from 'react';
import { Redirect, NavLink } from 'react-router-dom'
import {
  Button, Form, Grid, Header, Image, Message, Segment, Label, Container, Divider
} from 'semantic-ui-react'

class CarEditForm extends React.Component {
  constructor(props) {
    super(props);
    console.log("Props")
    console.log(this.props)
    this.state = {
      id: props.id,
      hasSubmitted: false,
      make: '',
      model: '',
      year: null,
      trim: '',
      updatedCar: null,
      carToEdit: {},
      failedToCreate: false
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  componentDidMount() {
    fetch("/api/cars/" + this.state.id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      }
    })
      .then(res => {
        if (!res.ok) {
          throw new Error()
        }
        return res.json()
      })
      .then(car => {
        this.setState({
          carToEdit: car,
          hasLoaded: true,
          make: car.make,
          model: car.model,
          year: car.year,
          trim: car.trim
        })
        console.log(car)
      })
  }

  handleChange(event, input) {
    console.log("Setting value to", event.target.value);
    let name = event.target.name;
    let value = event.target.value;
    this.setState({ [name]: value });
  }

  handleSubmit(event) {
    this.setState({ hasSubmitted: true });

    fetch("/api/cars/" + this.state.carToEdit.id, {
      method: 'PUT',
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
        this.setState({ updatedCar: car })
        console.log(car)
      })
      .catch(error => {
        this.setState({ failedToCreate: true })
      })

    event.preventDefault();
  }

  render() {

    if (this.state.updatedCar) {
      return <Redirect to={"/dashboard/cars/" + this.state.updatedCar.id} />
    }

    return (
      <Container>
        <Button content={"Back"}
          icon="left arrow"
          labelPosition='left'
          as={NavLink}
          to={"/dashboard/cars/" + this.state.id}
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
                  defaultValue={this.state.carToEdit.make}
                  onChange={this.handleChange}
                  error={this.state.failedToCreate}
                />
                <Form.Input fluid icon='car'
                  iconPosition='left'
                  placeholder='Model'
                  name="model"
                  defaultValue={this.state.carToEdit.model}
                  onChange={this.handleChange}
                  error={this.state.failedToCreate}
                />
                <Form.Input fluid icon='car'
                  iconPosition='left'
                  placeholder='Year'
                  name="year"
                  defaultValue={this.state.carToEdit.year}
                  onChange={this.handleChange}
                  error={this.state.failedToCreate}
                />
                <Form.Input fluid icon='car'
                  iconPosition='left'
                  placeholder='Trim'
                  name="trim"
                  defaultValue={this.state.carToEdit.trim}
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

export default CarEditForm;