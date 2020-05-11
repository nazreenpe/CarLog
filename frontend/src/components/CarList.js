import React, {Component} from 'react'
import {Card} from 'semantic-ui-react'
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
    return <div>
      {this.state.cars.map(car => (<Card as={NavLink} to={"/dashboard/cars/" + car.id}
        image="/favicon.png"
        header={car.year + " " + car.make + " " + car.model + " " + car.trim}
        meta={"Vin number=Todo"}

        />))}
    </div>
  }
}

export default CarList