import React from 'react';
import {
  Button,
  Image,
  Icon,
  Container,
  Popup,
  Header,
  Item,
  Divider
} from 'semantic-ui-react'
import { NavLink, Link } from 'react-router-dom'
import RecordEditForm from './RecordEditForm';
import handleExpiredSession from './ExpiredSessionHandler';
import DocumentUpload from './DocumentUpload';

class RecordDetails extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      id: props.id,
      carId: props.carId,
      record: {},
      hasLoaded: false,
      activities: [],
      documents: [],
      displayEditForm: false,
      showPopup: false,
      imageToPreview: null
    };

    this.showEditForm = this.showEditForm.bind(this)
    this.hideRecordForm = this.hideRecordForm.bind(this)
    this.wireUpPreview = this.wireUpPreview.bind(this)
  }

  showEditForm() {
    this.setState({ displayEditForm: true })
  }

  hideRecordForm() {
    this.setState({ displayEditForm: false })
  }

  wireUpPreview(path) {
    return (event) => {
      fetch("/api/s3upload/" + path, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Accept": "application/json"
        }
      })
        .then(handleExpiredSession)
        .then(res => {
          if (!res.ok) {
            throw new Error()
          }
          return res.json()
        })
        .then(res => {
          this.setState({ imageToPreview: res.url, showPopup: true })
          console.log(res.url)
        })
        .catch(error => {
          console.log("Error fetching signed URL")
        })
    }
  }

  componentDidMount() {
    let { carId, id } = this.state
    fetch("/api/cars/" + carId + "/mrs/" + id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      }
    })
      .then(handleExpiredSession)
      .then(res => {
        if (!res.ok) {
          throw new Error()
        }
        return res.json()
      })
      .then(record => {
        this.setState({ record: record, hasLoaded: true })
        console.log(record)
        fetch("/api/cars/" + carId + "/mrs/" + id + "/as", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
          }
        })
          .then(handleExpiredSession)
          .then(res => {
            if (!res.ok) {
              throw new Error()
            }
            return res.json()
          })
          .then(activities => {
            this.setState({ activities: activities })
          })
          .catch(error => {
            console.log(error)
          })

        fetch("/api/cars/" + carId + "/mrs/" + id + "/d", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
          }
        })
          .then(handleExpiredSession)
          .then(res => {
            if (!res.ok) {
              throw new Error()
            }
            return res.json()
          })
          .then(documents => {
            this.setState({ documents: documents })
          })
          .catch(error => {
            console.log(error)
          })
      })
      .catch(error => {
        console.log(error)
      })
  }

  render() {
    let { carId, id, record, activities, documents } = this.state;
    return (
      <Container>
        <Header as="h1">Maintenance record for {record.date}</Header>
        <Button
          content={"Back"}
          icon="left arrow"
          labelPosition='left'
          as={NavLink}
          to={"/dashboard/cars/" + carId}
          size='medium'
          push={true}
        />
        <Button
          color='blue'
          content="Edit"
          icon="pencil"
          labelPosition='right'
          size='medium'
          onClick={this.showEditForm}
        />

        {
          this.state.displayEditForm ?
            <div>

              <RecordEditForm carId={carId} id={record.id} />
              <Button
                color='grey'
                icon="cancel"
                onClick={this.hideRecordForm}
                content="Cancel"
              />
            </div> :
            <div></div>
        }
        <Divider />
        <Header as="h2">Activities: {this.state.activities.length} </Header>
        <Button
          color='blue'
          as={NavLink}
          to={"/dashboard/cars/" + carId + "/mrs/" + record.id + "/as/new"}
          content="Record an activity"
        />
        <Divider />
        <Item.Group>
          {activities.map(activity => {
            return <Item key={activity.id}>
              <Icon name='check circle' size='large' color='black' />
              <Item.Content>
                <Item.Header>{activity.typeName}</Item.Header>
                <Item.Description>{activity.notes}</Item.Description>
              </Item.Content>
            </Item>
          })}
        </Item.Group>
        <Header as="h2">Documents: {this.state.documents.length}</Header>
        <Button
          color='blue'
          as={NavLink}
          to={"/dashboard/cars/" + carId + "/mrs/" + record.id + "/d/new"}
          content="Upload a document"
        />

        <Image size="medium"
          src={this.state.imageToPreview}
          onClick={() => this.setState({ showPopup: false })}
          hidden={!this.state.showPopup} />

        <Item.Group link>
          {documents.map(document => {
            return <Item key={document.path} onClick={this.wireUpPreview(document.path)}>
              <Item.Content>
                <Icon name='file' size='large' color='black' />
                <Item.Header>{document.filename || document.path}</Item.Header>
                <Item.Description>{document.description}</Item.Description>
              </Item.Content>
            </Item>
          })}
        </Item.Group>
      </Container>
    )
  }
}

export default RecordDetails;