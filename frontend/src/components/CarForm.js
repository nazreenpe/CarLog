import React from 'react';
import CarDetails from './CarDetails';

class CarForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: props.startValue,
            hasSubmitted: false,
            carId: undefined
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        console.log("Setting value to", event.target.value);

        this.setState({ value: event.target.value });
    }

    handleSubmit(event) {
        // alert('A name was submitted: ' + this.state.value);
        //   ajax call to backEnd
        this.setState({ hasSubmitted: true });
        event.preventDefault();
    }

    render() {
        console.log("CarForm render has been called");
        // depending on ajax call, can have if-else conditions giving different
        // results, including CarDetails
        if (this.state.carId) {
            return <CarDetails id={this.state.carId}/>
        }
        if (this.state.hasSubmitted) {
            return <p>Form Submitted successfully (without Ajax ;)</p>
        }
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Name:
            <input type="text" value={this.state.value} onChange={this.handleChange} />
                </label>
                <input type="submit" value="Submit" />
            </form>
        );
    }
}

export default CarForm;