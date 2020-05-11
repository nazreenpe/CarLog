import React from 'react';
import { Redirect, NavLink } from 'react-router-dom'
import {
  Button, Form, Grid, Header, Image, Message, Segment, Label, Container, Divider
} from 'semantic-ui-react'

class RecordEditForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      carId: props.carId,
      id: props.id,
      hasSubmitted: false,
      date: null,
      recordToEdit: {},
      failedToUpdate: false
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

  componentDidMount() {
    let {carId, id} = this.state
    fetch("/api/cars/" + carId + "/mrs/" + id , {
      method: 'GET',
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
      .then(record => {
        this.setState({ recordToEdit: record })
        console.log(record)
      })
      .catch(error => {
        this.setState({ failedToLoad: true })
      })
  }

  handleSubmit(event) {
    this.setState({ hasSubmitted: true });
    let {carId, id} = this.state
    fetch("/api/cars/" + carId + "/mrs/" + id , {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify({
        "date": this.state.date,
      })
    })
      .then(res => {
        if (!res.ok) {
          throw new Error()
        }
        return res.json()
      })
      .then(record => {
        this.setState({ updatedRecord: record })
        console.log(record)
      })
      .catch(error => {
        this.setState({ failedToUpdate: true })
      })

    event.preventDefault();
  }

  render() {
    let {recordToEdit, updatedRecord, id, carId} = this.state
    if (updatedRecord) {
      return <Redirect
        to={"/dashboard/cars/" + carId + "/mrs/" + id}
      />
    }

    return (
      <Container>
        <Button content="Back to record"
          icon="left arrow"
          labelPosition='left'
          as={NavLink}
          to={"/dashboard/cars/" + carId + "/mrs/" + id}
          push={true}
        />
        <Divider />
        <Grid textAlign='left' style={{ height: '100vh' }}>
          <Grid.Column style={{ maxWidth: 450 }}>
            <Form size='large' onSubmit={this.handleSubmit}>
              <Segment stacked>
                <Form.Input fluid icon='calendar'
                  iconPosition='left'
                  placeholder='Date'
                  name="date"
                  defaultValue={recordToEdit.date}
                  onChange={this.handleChange}
                  error={this.state.failedToUpdate}
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

export default RecordEditForm;