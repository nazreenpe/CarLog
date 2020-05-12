import React from 'react';
import {
  Button,
  Card,
  Container,
  Grid,
  Header,
  Divider
} from 'semantic-ui-react'
import { NavLink, Link } from 'react-router-dom'
import RecordEditForm from './RecordEditForm';



class RecordDetails extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      id: props.id,
      carId: props.carId,
      record: {},
      hasLoaded: false,
      activities: [],
      displayEditForm: false
    };

    this.showEditForm = this.showEditForm.bind(this)
    this.hideRecordForm = this.hideRecordForm.bind(this)
  }

  showEditForm() {
    this.setState({ displayEditForm: true })
  }

  hideRecordForm() {
    this.setState({ displayEditForm: false })
  }

  componentDidMount() {
    this.setState({displayEditForm: false})
    let { carId, id } = this.state
    fetch("/api/cars/" + carId + "/mrs/" + id, {
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
          .then(res => {
            if (!res.ok) {
              throw new Error()
            }
            return res.json()
          })
          .then(activities => {
            this.setState({ activities: activities })
          })
      })
  }

  render() {
    let { carId, record, activities } = this.state;
    return (
      <Container>
        <Header as="h1">Maintenance record for {record.date}</Header>
        <Button
          content={"Back"}
          icon="left arrow"
          labelPosition='left'
          as={NavLink}
          to={"/dashboard/cars/" + carId}
          push={true}
        />
        <Button
          positive
          content="Edit"
          icon="pencil"
          labelPosition='right'
          size='small'
          onClick={this.showEditForm}
        />

        {
          this.state.displayEditForm ?
            <div>
              <Button
                negative
                icon="trash"
                onClick={this.hideRecordForm}
                content="Cancel"
              />
              <RecordEditForm carId={carId} id={record.id} />
            </div> :
            <div></div>
        }
        <Divider />
        <Header as="h2">{this.state.activities.length} activities</Header>
        <Button
          positive
          as={NavLink}
          to={"/dashboard/cars/" + carId + "/mrs/" + record.id + "/as/new"}
          content="Record an activity"
        />
        <Divider />
        <Grid divided='vertically'>
          {activities.map(activity => {
            return <Grid.Row columns={1}>
              <Grid.Column>
                <Card>
                  <Card.Content>
                    <Card.Header>{activity.typeName}</Card.Header>
                  </Card.Content>
                </Card>
              </Grid.Column>
            </Grid.Row>
          })}
        </Grid>

      </Container >
    )
  }
}

export default RecordDetails;