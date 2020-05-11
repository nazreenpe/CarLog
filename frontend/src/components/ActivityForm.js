import React from 'react';
import { Redirect, NavLink } from 'react-router-dom'
import {
  Button, Form, Grid, Header, Image, Message, Segment, Label, Container, Divider
} from 'semantic-ui-react'

class ActivityForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      carId: props.carId,
      recordId: props.recordId,  
      hasSubmitted: false,
      type: null, 
      createdActivity: null,
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
    let {carId, recordId} = this.state

    fetch("/api/cars/" + carId + "/mrs/" + recordId + "/as", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify({
        "type": this.state.type
      })
    })
      .then(res => {
        if (!res.ok) {
          throw new Error()
        }
        return res.json()
      })
      .then(actvity => {
        this.setState({ createdActivity: actvity })
        console.log(actvity)
      })
      .catch(error => {
        this.setState({ failedToCreate: true })
      })

    event.preventDefault();
  }

  render() {
    let {carId, recordId, createdActivity} = this.state
    if (createdActivity) {
      return <Redirect to={"/dashboard/cars/" + carId + "/mrs/" + recordId} />
    }

    return (
      <Container>
        <Button content="Back to record"
          icon="left arrow"
          labelPosition='left'
          as={NavLink}
          to={"/dashboard/cars/" + carId + "/mrs/" + recordId}
          push={true}
        />
        <Divider />
        <Grid textAlign='left' style={{ height: '100vh' }}>
          <Grid.Column style={{ maxWidth: 450 }}>
            <Form size='large' onSubmit={this.handleSubmit}>
              <Segment stacked>
                <Form.Input fluid icon='factory'
                  iconPosition='left'
                  placeholder='Type'
                  name="type"
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

export default ActivityForm;