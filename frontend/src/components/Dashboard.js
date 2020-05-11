import React, { Component } from 'react'
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
  Label
} from 'semantic-ui-react'
import Logout from './Logout.js'
import CarList from './CarList.js'
import CarDetails from './CarDetails.js'
import CarForm from './CarForm.js'
import CarEditForm from './CarEditForm.js'

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
                <Logout />
              </Dropdown.Menu>
            </Dropdown>
          </Container>
        </Menu>

        <Container style={{ marginTop: '7em' }}>
          <HashRouter>
            <Route exact path="/dashboard" component={CarList} />
            <Route exact path="/dashboard/cars/:id" render={props =>
              props.match.params.id === 'new'
                ? <CarForm />
                : <CarDetails id={props.match.params.id} />
            } />
            <Route exact path="/dashboard/cars/:id/edit" render={ props => 
              <CarEditForm id={props.match.params.id} /> 
            } />
          </HashRouter>
        </Container>
      </div>
    )
  }
}

export default Dashboard