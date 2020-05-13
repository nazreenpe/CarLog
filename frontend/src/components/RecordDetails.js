import React from 'react';
import {
  Button,
  Image,
  Icon,
  Container,
  Portal,
  Segment,
  Header,
  Item,
  Divider,
  Card
} from 'semantic-ui-react'
import { NavLink, Link } from 'react-router-dom'
import RecordEditForm from './RecordEditForm';
import handleExpiredSession from './ExpiredSessionHandler';
import DocumentUpload from './DocumentUpload';
import { Document, Page, pdfjs } from "react-pdf";
pdfjs.GlobalWorkerOptions.workerSrc = `//cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.js`;

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
      imageToPreview: null,
      numPages: null,
      pageNumber: 1,
    };

    this.showEditForm = this.showEditForm.bind(this)
    this.hideRecordForm = this.hideRecordForm.bind(this)
    this.wireUpPreview = this.wireUpPreview.bind(this)
    this.handleClosePortal = this.handleClosePortal.bind(this)
    this.onDocumentLoadSuccess = this.onDocumentLoadSuccess.bind(this)
    this.wireUpDelete = this.wireUpDelete.bind(this)
  }

  onDocumentLoadSuccess = ({ numPages }) => {
    this.setState({ numPages });
  }

  handleClosePortal() {
    this.setState({ showPopup: false })
  }

  showEditForm() {
    this.setState({ displayEditForm: true })
  }

  hideRecordForm() {
    this.setState({ displayEditForm: false })
  }

  wireUpDelete(document) {
    return (event) => {
      fetch("/api/s3upload/delete/" + document.path, {
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
        .then(signedUrlRes => {
          fetch(signedUrlRes.url, {
            method: "DELETE"
          })
            .then(res => {
              fetch(
                `/api/cars/${this.state.carId}/mrs/${this.state.id}/d/${document.id}`,
                {
                  method: "DELETE",
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
                  let oldDocs = this.state.documents
                  let indexToDelete = oldDocs.indexOf(document);
                  if (indexToDelete > -1) {
                    oldDocs.splice(indexToDelete, 1);
                    this.setState({documents: oldDocs})
                  }
                  return res.json()
                })
                .catch(error => console.log(error))
            }).catch(error => console.log(error))
        })
        .catch(error => console.log(error))
    }
  }


  wireUpPreview(path, filename) {
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
          this.setState({
            imageToPreview: res.url,
            showPopup: true,
            fileType: filename ? filename.split(".").pop() : "jpeg"
          })
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
    const { pageNumber, numPages } = this.state;
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

        <Portal open={this.state.showPopup}>
          <Segment
            style={{
              overflowX: 'hidden',
              left: '30%',
              position: 'fixed',
              width: "50%",
              height: "70%",
              top: '10%',
              zIndex: 1000,
            }}
          >
            <Button
              icon='close'
              content='Close'
              negative
              onClick={this.handleClosePortal}
            />
            {
              this.state.fileType === "pdf" ?
                <Document file={this.state.imageToPreview}
                  onLoadError={console.error}
                  onLoadSuccess={this.onDocumentLoadSuccess}>
                  {
                    Array.from(
                      new Array(numPages),
                      (el, index) => (
                        <Page
                          key={`page_${index + 1}`}
                          pageNumber={index + 1}
                        />
                      ),
                    )
                  }
                </Document>
                :
                <Image
                  src={this.state.imageToPreview} width="100%" />
            }
          </Segment>
        </Portal>

        <Item.Group >
          {documents.map(document => {
            return <Item key={document.path}>
              <Item.Content>
                <Icon name='file' size='large' color='black' />
                <Item.Header
                  onClick={this.wireUpPreview(document.path, document.filename)}
                  as="a">
                  {document.filename || document.path}
                </Item.Header>
                <Item.Description>{document.description}</Item.Description>
                <Button
                  negative
                  size='tiny'
                  icon="trash"
                  onClick={this.wireUpDelete(document)}
                />
              </Item.Content>
            </Item>
          })}
        </Item.Group>
      </Container>
    )
  }
}

export default RecordDetails;