import React, { Component } from 'react'
import { NavLink, Redirect, HashRouter, Route, Switch } from 'react-router-dom'
import {
  Container,
  Dropdown,
  Image,
  Menu,
} from 'semantic-ui-react'
import Logout from './Logout.js'
import CarList from './CarList.js'
import CarDetails from './CarDetails.js'
import CarForm from './CarForm.js'
import CarEditForm from './CarEditForm.js'
import RecordForm from './RecordForm.js'
import RecordEditForm from './RecordEditForm.js'
import RecordDetails from './RecordDetails.js'
import ActivityForm from './ActivityForm.js'
import DocumentUpload from './DocumentUpload.js';
import Profile from './Profile.js';
import PdfExport from './PdfExport.js';


class Dashboard extends Component {
 
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
                <Dropdown.Item as={NavLink} to="/dashboard/profile">Profile</Dropdown.Item>
                <Dropdown.Divider />
                <Logout />
              </Dropdown.Menu>
            </Dropdown>
          </Container>
        </Menu>

        <Container style={{ marginTop: '7em' }}>
          <HashRouter>
            <Switch>

              <Route exact path="/dashboard" component={CarList} />
              <Route exact path="/dashboard/cars/:id" render={props =>
                props.match.params.id === 'new'
                  ? <CarForm />
                  : <CarDetails id={props.match.params.id} />
              } />
              <Route exact path="/dashboard/cars/:id/edit" render={props =>
                <CarEditForm id={props.match.params.id} />
              } />
              <Route exact path="/dashboard/cars/:carId/mrs/:id" render={props =>
                props.match.params.id === 'new'
                  ? <RecordForm carId={props.match.params.carId} />
                  : <RecordDetails carId={props.match.params.carId} id={props.match.params.id} />
              } />
              <Route exact path="/dashboard/cars/:carId/mrs/:id/edit" render={props =>
                <RecordEditForm carId={props.match.params.carId} id={props.match.params.id} />
              } />
              <Route exact path="/dashboard/cars/:carId/mrs/:recordId/as/new" render={props =>
                <ActivityForm carId={props.match.params.carId} recordId={props.match.params.recordId} />
              } />
              <Route exact path="/dashboard/cars/:carId/mrs/:recordId/d/new" render={props =>
                <DocumentUpload carId={props.match.params.carId} recordId={props.match.params.recordId} />
              } />
              <Route exact path="/dashboard/cars/:carId/export" render={props =>
                <PdfExport carId={props.match.params.carId} />
              } />
              <Route exact path="/dashboard/profile" render={props =>
                <Profile />
              } />
              <Route render={props => <Redirect to="/dashboard" push={true} />} />
            </Switch>
          </HashRouter>
        </Container>
      </div>
    )
  }
}

export default Dashboard