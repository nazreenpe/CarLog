import React, { Component } from 'react'
import {
  Card,
  Image,
  Container,
} from 'semantic-ui-react'

class Profile extends Component {
  render() {
    let user = JSON.parse(localStorage.getItem("currentUser"))  
    return <Container>
    <Card>
      <Image src='' wrapped ui={false} />
      <Card.Content>
       <Card.Header>{user.name}</Card.Header>
       <Card.Meta>{user.emailId}</Card.Meta>
      </Card.Content>
    </Card>
    </Container>
  }
}

export default Profile