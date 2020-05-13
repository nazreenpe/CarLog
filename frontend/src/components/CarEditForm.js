import React from 'react';
import { Redirect, NavLink } from 'react-router-dom'
import {
  Button, Form, Grid, Confirm, Image, Message, Segment, Label, Container, Divider
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
      failedToCreate: false,
      carDeleted: false,
      failedToDelete: false,
      showConfirm: false
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.delete = this.delete.bind(this);
    this.confirmDelete = this.confirmDelete.bind(this);
    this.cancelDelete = this.cancelDelete.bind(this);
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
      .catch(error => {
        this.setState({ failedToCreate: true })
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

  delete() {
    fetch("/api/cars/" + this.state.carToEdit.id, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      }
    })
      .then(res => {
        if (!res.ok) {
          throw new Error()
        }
        return res.json()
      })
      .then(res => {
        this.setState({ carDeleted: true })
        console.log(res)
      })
      .catch(error => {
        this.setState({ failedToDelete: true })
      })
  }

  confirmDelete() {
    this.setState({showConfirm: true})
  }

  cancelDelete() {
    this.setState({showConfirm: false})
  }

  render() {
    if (this.state.updatedCar) {
      return <Redirect to={"/dashboard/cars/" + this.state.updatedCar.id} />
    }

    if(this.state.carDeleted) {
      return <Redirect to={"/dashboard/"} />
    }

    return (
      <Container>
        <Button
          content={"Back"}
          icon="left arrow"
          labelPosition='left'
          as={NavLink}
          to={"/dashboard/cars/" + this.state.id}
          push={true}
        />

        <Button content={"Delete"}
          negative
          icon="trash"
          labelPosition='left'
          onClick={this.confirmDelete}
        />
        <Confirm
          open={this.state.showConfirm}
          onCancel={this.cancelDelete}
          onConfirm={this.delete}
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