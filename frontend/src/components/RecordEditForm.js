import React from 'react';
import { Redirect, NavLink } from 'react-router-dom'
import {
  Button, Form, Grid, Loader, Image, Message, Segment, Label, Container, Divider
} from 'semantic-ui-react'
import SemanticDatepicker from 'react-semantic-ui-datepickers';
import 'react-semantic-ui-datepickers/dist/react-semantic-ui-datepickers.css';

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

  handleChange(event, data) {
    console.log("Setting value to", data.value);
    let name = data.name;
    let value = data.value;
    this.setState({ [name]: value });
  }

  componentDidMount() {
    let { carId, id } = this.state
    fetch("/api/cars/" + carId + "/mrs/" + id, {
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
    let { carId, id } = this.state
    fetch("/api/cars/" + carId + "/mrs/" + id, {
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
    let { recordToEdit, updatedRecord, id, carId } = this.state
    if (updatedRecord) {
      return <Redirect
        to={"/dashboard/cars/" + carId + "/mrs/" + id}
      />
    }

    if (!recordToEdit) {
      return <Loader inline active={!recordToEdit} content="Loading ..." />
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
                <SemanticDatepicker
                  name="date"
                  placeholder={recordToEdit.date}
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

export default RecordEditForm;