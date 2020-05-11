import React, {Component} from 'react'
import {Card, Grid} from 'semantic-ui-react'
import { NavLink } from 'react-router-dom'

class CarList extends Component {
  constructor(props) {
    super(props)
    this.state = {
      cars: []
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
    .then(res => {
        if (!res.ok) {
          throw new Error()
        }
        return res.json()
    })
    .then(cars => {
      this.setState({cars: cars})
      console.log(cars)
    })
  }

  render() {
    let { cars } = this.state;
    let rows = []
    for(let i =0; i <= Math.ceil(cars.length / 3); i++) {
      let columns = cars.slice(i*3, (i + 1) *3).map(car => {
        return <Grid.Column>
          <Card as={NavLink} to={"/dashboard/cars/" + car.id}
            image="/favicon.png"
            header={car.year + " " + car.make + " " + car.model + " " + car.trim}
            meta={"Vin number=Todo"}
          />
        </Grid.Column>
      })
      rows.push(<Grid.Row columns={3}>
          {columns}
        </Grid.Row>)
    }
    return <div>
      <Grid divided="vertically">
        {rows}
      </Grid>
    </div>
  }
}

export default CarList