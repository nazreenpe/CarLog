import React from 'react';
import { Redirect, NavLink } from 'react-router-dom'
import {
  Button, Form, Grid, Dropdown, TextArea, Message, Segment, Label, Container, Divider
} from 'semantic-ui-react'

import FileUploadButton from './FileUploadButton';
import handleExpiredSession from './ExpiredSessionHandler';

class DocumentUpload extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      carId: props.carId,
      recordId: props.recordId,
      description: null,
      path: null,
      filename: null,
      failedToCreate: false
    }
    this.handleUpload = this.handleUpload.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
    this.handleChange = this.handleChange.bind(this)
  }

  handleUpload(path, filename) {
    this.setState({ path: path, filename: filename })
  }

  handleChange(event, input) {
    console.log("Setting value to", event.target.value);
    let name = event.target.name;
    let value = event.target.value;
    this.setState({ [name]: value });
  }

  handleSubmit() {
    let { carId, recordId, path, description, filename } = this.state
    fetch("/api/cars/" + carId + "/mrs/" + recordId + "/d", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify({
        path: path, 
        description: description,
        filename: filename 
      })
    })
      .then(handleExpiredSession)
      .then(res => {
        if (!res.ok) {
          throw new Error()
        }
        return res.json()
      })
      .then(res => {
        this.setState({ uploadedDoc: res })
      })
      .catch(error => {
        this.setState({ failedToCreate: true })
        console.log("Error uploading document")
      })
  }

  render() {
    let { uploadedDoc, failedToCreate, carId, recordId } = this.state
    if (uploadedDoc) {
      return <Redirect to={"/dashboard/cars/" + carId + "/mrs/" + recordId} />
    }
    return <Container>
      <Grid textAlign='left' style={{ height: '100vh' }}>
        <Grid.Column style={{ maxWidth: 450 }}>
          <FileUploadButton onUpload={this.handleUpload} />

          <Form size='large' onSubmit={this.handleSubmit}>
            <Segment stacked>
              <Form.Input
                placeholder='Description for the document'
                fluid
                search
                onChange={this.handleChange}
                error={failedToCreate}
                name="description"
              />
              <Button color='teal' fluid size='large'>
                Go
              </Button>
            </Segment>
          </Form>
        </Grid.Column>
      </Grid>
    </Container>
  }
}

export default DocumentUpload