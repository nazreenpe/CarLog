import React, {Component} from 'react'
import { NavLink, Redirect } from 'react-router-dom'

class Dashboard extends Component {
  constructor(props) {
    super(props)
  }

  render() {
    if (!localStorage.getItem("currentUser")) {
      return <Redirect to="/login" />
    }
    return (
      <div>This is where the dashboard goes</div>
    )
  }
}

export default Dashboard