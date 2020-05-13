import React from 'react';
import { Redirect, NavLink } from 'react-router-dom'
import {
  Button, Form, Grid, Header, Image, Message, Segment, Label, Container, Divider
} from 'semantic-ui-react'
import SemanticDatepicker from 'react-semantic-ui-datepickers';
import 'react-semantic-ui-datepickers/dist/react-semantic-ui-datepickers.css';
import handleExpiredSession from './ExpiredSessionHandler';

class RecordForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      carId: props.carId,
      hasSubmitted: false,
      date: null,
      createdRecord: null,
      failedToCreate: false
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event, data) {
    console.log("Setting value to", data.value);
    let name = data.name;
    let value = data.value;
    this.setState({ [name]: value });
  }

  handleSubmit(event) {
    this.setState({ hasSubmitted: true });

    fetch("/api/cars/" + this.state.carId + "/mrs", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify({
        "date": this.state.date,
      })
    })
    .then(handleExpiredSession)
      .then(res => {
        if (!res.ok) {
          throw new Error()
        }
        return res.json()
      })
      .then(record => {
        this.setState({ createdRecord: record })
        console.log(record)
      })
      .catch(error => {
        this.setState({ failedToCreate: true })
      })

    event.preventDefault();
  }

  render() {
    if (this.state.createdRecord) {
      return <Redirect
        to={"/dashboard/cars/" + this.state.carId + "/mrs/" + this.state.createdRecord.id}
      />
    }

    return (
      <Grid textAlign='left'>
        <Grid.Column style={{ maxWidth: 450 }}>
          <Segment>
            <Form size='large' onSubmit={this.handleSubmit}>
              <SemanticDatepicker
                name="date"
                onChange={this.handleChange}
                error={this.state.failedToCreate}
              />
              <Button content="Add" color='teal' size='large' />
            </Form>
          </Segment>
        </Grid.Column>
      </Grid>
    );
  }
}

export default RecordForm;