import React from 'react';
import 'semantic-ui-css/semantic.min.css'

import { HashRouter, Route, Redirect, Switch } from "react-router-dom"
import AppLayout from './components/AppLayout.js'
import Dashboard from './components/Dashboard.js'

function App() {
  return (
    <HashRouter>
      <Switch>
        <Route exact path="/(|signup|login|home)" component={AppLayout} />
        <Route path="/dashboard" render={props => <Dashboard />} />
        <Route render={props => <Redirect to="/dashboard" push={true} />} />
      </Switch>
    </HashRouter>
  );
}

export default App;
