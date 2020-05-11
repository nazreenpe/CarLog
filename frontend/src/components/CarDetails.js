import React from 'react';
import {
  Button,
  Container,
  Grid,
  Header,
  Divider
} from 'semantic-ui-react'
import { NavLink, Link } from 'react-router-dom'



class CarDetails extends React.Component {
  constructor(props) {
    super(props);
    console.log(props)
    this.state = {
      id: props.id,
      car: {},
      hasLoaded: false
    };
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

        <Grid divided='vertically'>
          <Grid.Row columns={1}>
            <Grid.Column> Record 1 </Grid.Column>
          </Grid.Row>
          <Grid.Row columns={1}>
            <Grid.Column> Record 2 </Grid.Column>
          </Grid.Row>
          <Grid.Row columns={1}>
            <Grid.Column> Record 3 </Grid.Column>
          </Grid.Row>
        </Grid>
      </Container>
    )
  }
}

export default CarDetails;