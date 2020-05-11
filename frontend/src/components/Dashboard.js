import React, {Component} from 'react'
import { NavLink, Redirect, HashRouter, Route } from 'react-router-dom'
import {
Button,
Container,
Dropdown,
Form,
Grid,
Header,
Image,
List,
Divider,
Menu,
Message,
Segment,
Label } from 'semantic-ui-react'

import CarList from './CarList.js'

class Dashboard extends Component {
  constructor(props) {
    super(props)
  }

  render() {
    let userJson = localStorage.getItem("currentUser")
    if (!userJson) {
      return <Redirect to="/login" />
    }
    let currentUser = JSON.parse(userJson)
    return (
      <div>
        <Menu fixed='top' inverted>
          <Container>
            <Menu.Item as='a' header>
              <Image size='mini' src='/favicon.png' style={{ marginRight: '1.5em' }} />
              CarLog
            </Menu.Item>
            <Menu.Item as={NavLink} to="/dashboard">Dashboard</Menu.Item>

            <Dropdown item simple text={currentUser.name}>
              <Dropdown.Menu>
                <Dropdown.Item as={NavLink} to="/profile">Profile</Dropdown.Item>
                <Dropdown.Divider />
                <Dropdown.Item as={NavLink} to="/logout">Log out</Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
          </Container>
        </Menu>
        <Container style={{ marginTop: '7em' }}>
          <HashRouter>
            <Route exact path="/dashboard" component={CarList} />
          </HashRouter>
        </Container>
      </div>
    )
  }
}

export default Dashboard