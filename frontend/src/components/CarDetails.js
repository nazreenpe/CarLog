import React from 'react';
import { Container, Grid, Header } from 'semantic-ui-react'


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
         this.setState({car: car, hasLoaded: true})
         console.log(car)
       })
     }

    render() {
        return (
          <Container>
          <Header as="h1">{this.state.car.make + " " + this.state.car.model}</Header>
          <Header as="h2">{this.state.car.year + " " + this.state.car.trim}</Header>
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