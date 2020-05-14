import React, { Component } from 'react'
import {
  Button,
  Card,
  Divider,
  Dimmer,
  Loader,
  Grid,
  Header
} from 'semantic-ui-react'
import { NavLink } from 'react-router-dom'
import handleExpiredSession from './ExpiredSessionHandler';

class CarList extends Component {
  constructor(props) {
    super(props)
    this.state = {
      cars: [],
      failedToLoad: false,
      hasLoaded: false
    }
  }

  componentDidMount() {
    fetch("/api/cars", {
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
      .then(cars => {
        this.setState({ cars: cars, hasLoaded: true })
        console.log(cars)
      })
      .catch(error => {
        this.setState({ hasLoaded: false })
        console.log("Error fetching cars")
      })
  }

  render() {
    let { cars } = this.state;
    let rows = []
    for (let i = 0; i <= Math.ceil(cars.length / 3); i++) {
      let columns = cars.slice(i * 3, (i + 1) * 3).map(car => {
        return <Grid.Column>
          <Card as={NavLink} to={"/dashboard/cars/" + car.id}
            image="/favicon.png"
            header={car.year + " " + car.make + " " + car.model + " " + car.trim}
          />
        </Grid.Column>
      })
      rows.push(<Grid.Row columns={3}>
        {columns}
      </Grid.Row>)
    }
    return <div>
      <Header as="h2">My Cars</Header>
      <Button
        primary
        content="Add a new Car"
        as={NavLink}
        to="/dashboard/cars/new"
        push={true} />
      <Divider />
      <Loader inline active={!this.state.hasLoaded} content="Loading ..." />
      <Grid divided="vertically">
        {rows}
      </Grid>
    </div>
  }
}

export default CarList