import React from 'react';
import logo from './CarMaintenance_LLsquare.jpg';
import './App.css';
import CarForm from './components/CarForm';
import CarDetails from './components/CarDetails';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Launching CarLog App!
        </p>
        <CarForm startValue="No cars Yet!"></CarForm>
        <CarDetails id="7bbd93a2-40b4-4ca6-bb47-7d6955e814b8"></CarDetails>
      </header>
    </div>
  );
}

export default App;
