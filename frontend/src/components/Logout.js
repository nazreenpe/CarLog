import React, {Component} from 'react'
import { NavLink, Redirect } from 'react-router-dom'
import { Dropdown } from 'semantic-ui-react'

class Logout extends Component {
  constructor(props){
    super(props)
    this.state = {
      loggedOut: false
    }

    this.logout = this.logout.bind(this)
  }

  logout() {
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
      this.setState({loggedOut: true})
    })
  }

  render() {
    if (this.state.loggedOut) {
      return <Redirect to="/login" push={true}/>
    }
    return <Dropdown.Item onClick={this.logout} > Log out </Dropdown.Item>
  }
}

export default Logout