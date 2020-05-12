import React from 'react';
import { Redirect, NavLink } from 'react-router-dom'
import {
  Button, Form, Grid, Dropdown, Image, Message, Segment, Label, Container, Divider
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
      failedToCreate: false,
      validActivities: [
        { key: "OIL_CHANGE", value: "OIL_CHANGE", text: "Oil Change" },
        { key: "TIRE_ROTATION", value: "TIRE_ROTATION", text: "Tire Rotation" },
        { key: "REPLACE_WIPER", value: "REPLACE_WIPER", text: "Replace Wiper" },
      ]
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event, input) {
    console.log("Setting value to", input.value);
    let name = input.name;
    let value = input.value;
    this.setState({ [name]: value });
  }

  handleSubmit(event) {
    this.setState({ hasSubmitted: true });
    let { carId, recordId } = this.state

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
    let { carId, recordId, createdActivity } = this.state
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
                <Dropdown
                  placeholder='Select an activity'
                  fluid
                  search
                  selection
                  onChange={this.handleChange}
                  error={this.state.failedToCreate}
                  name="type"
                  options={this.state.validActivities}
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