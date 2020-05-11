import React, {Component} from 'react'
import { NavLink, Redirect } from 'react-router-dom'

class Logout extends Component {
  componentDidUpdate() {
    fetch("/api/auth/logout", {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
    })
    .then(res => {
      localStorage.removeItem("currentUser")
      console.log("logged out")
    })
  }
  render() {
    return <Redirect to="/login" />
  }
}

export default Logout