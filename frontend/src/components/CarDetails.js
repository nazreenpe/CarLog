import React from 'react';
import {
  Button,
  Card,
  Container,
  Item,
  Header,
  Icon,
  Divider
} from 'semantic-ui-react'
import { NavLink, Link } from 'react-router-dom'
import RecordEditForm from './RecordEditForm';
import RecordForm from './RecordForm';
import handleExpiredSession from './ExpiredSessionHandler';

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
      .then(handleExpiredSession)
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
          .then(handleExpiredSession)
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
          color='blue'
          content="Edit"
          icon="pencil"
          labelPosition='right'
          size='small'
          as={Link}
          to={"/dashboard/cars/" + car.id + "/edit"}
          push={true}
        />
        <Button
          color='blue'
          content="Export"
          icon="send"
          labelPosition='right'
          size='small'
          as={Link}
          to={"/dashboard/cars/" + car.id + "/export"}
          push={true}
        />
        <Divider />
        <Header as="h2">{this.state.records.length} Records</Header>
        <Button
          color='blue'
          onClick={this.displayRecordForm}
          content="Add a Record"
        />
        {
          this.state.showRecordForm ?
            <RecordForm carId={this.state.id} /> : <div />
        }
        <Divider />
        <Item.Group link>
          {this.state.records.map(record => {
            return <Item key={record.id}
              as={NavLink} to={"/dashboard/cars/" + car.id + "/mrs/" + record.id}>
              <Item.Content>
                <Icon name='folder' size='large' color='black' />
                <Item.Header>Record: {record.date}</Item.Header>
              </Item.Content>
            </Item>
          })}
        </Item.Group>
      </Container >
    )
  }
}

export default CarDetails;