import React from 'react';
import './App.css';
import CarForm from './components/CarForm';
import CarDetails from './components/CarDetails';
import Header from './components/Header';

function App() {
  return (
    <div>
      <div>
        <Header></Header>
      </div>

      <div className="page-body">
        <p>
          Welcome to CarLog
        </p>
        {/* <CarForm startValue="No cars Yet!"></CarForm> */}
        {/* <CarDetails id="7bbd93a2-40b4-4ca6-bb47-7d6955e814b8"></CarDetails> */}
      </div>
    </div>
  );
}

export default App;
