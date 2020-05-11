import React from 'react';
import 'semantic-ui-css/semantic.min.css'

import {HashRouter, Route} from "react-router-dom"
import AppLayout from './components/AppLayout.js'
import Dashboard from './components/Dashboard.js'
import Logout from './components/Logout.js'

function App() {
  return (
      <HashRouter>
        <Route exact path="/(|signup|login|home)" component={AppLayout} />
        <Route exact path="/dashboard" render={props => <Dashboard /> } />
      </HashRouter>
  );
}

export default App;
