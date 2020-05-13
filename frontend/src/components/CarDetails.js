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
import RecordForm from './RecordForm';



class CarDetails extends React.Component {
  constructor(props) {
    super(props);
    console.log(props)
    this.state = {
      id: props.id,
      car: {},
      hasLoaded: false,
      records: [],
      showRecordForm: false
    };
    this.displayRecordForm = this.displayRecordForm.bind(this)
    this.hideRecordForm = this.hideRecordForm.bind(this)
  }

  displayRecordForm() {
    this.setState({ showRecordForm: true })
  }

  hideRecordForm() {
    this.setState({ showRecordForm: false })
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
        this.setState({ car: car, hasLoaded: true })
        console.log(car)
        fetch("/api/cars/" + car.id + "/mrs", {
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
          .then(records => {
            this.setState({ records: records })
          })
          .catch(error => {
            this.setState({ failedToCreate: true })
          })
      })
  }

  render() {
    let { car } = this.state;
    return (
      <Container>
        <Header as="h1">{car.make + " " + car.model}</Header>
        <Header as="h2">{car.year + " " + car.trim}</Header>
        <Button
          content={"Back"}
          icon="left arrow"
          labelPosition='left'
          as={NavLink}
          to={"/dashboard"}
          push={true}
        />
        <Button
          positive
          content="Edit"
          icon="pencil"
          labelPosition='right'
          size='small'
          as={Link}
          to={"/dashboard/cars/" + car.id + "/edit"}
          push={true}
        />
        <Divider />
        <Header as="h2">{this.state.records.length} Records</Header>
        <Button
          positive
          onClick={this.displayRecordForm}
          content="Add a Record"
        />
        {
          this.state.showRecordForm ?
            (<div><Button content="Cancel"
              icon="trash"
              negative
              labelPosition='left'
              onClick={this.hideRecordForm}
            />
              <RecordForm carId={this.state.id} /> </div>) : <div />
        }
        <Divider />
        <Grid divided='vertically'>
          {this.state.records.map(record => {
            return <Grid.Row columns={1}>
              <Grid.Column>
                <Card as={NavLink} to={"/dashboard/cars/" + car.id + "/mrs/" + record.id}>
                  <Card.Content>
                    <Card.Header>{record.date}</Card.Header>
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

export default CarDetails;