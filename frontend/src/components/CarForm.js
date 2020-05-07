import React from 'react';

class CarForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: props.startValue,
            hasSubmitted: false,
            make:'',
            model:'',
            year:null,
            trim:''
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        console.log("Setting value to", event.target.value);

        let name = event.target.name;
        let value = event.target.value;
        this.setState({ [name]: value });
    }

    async handleSubmit(event) {
        // alert('A name was submitted: ' + this.state.value);
        //   ajax call to backEnd
        this.setState({ hasSubmitted: true });

        const response = await fetch("http://localhost:8080/cars", {
            method: 'POST', // *GET, POST, PUT, DELETE, etc.
            headers: {
              'Content-Type': 'application/json',
              'Accept': 'application/json'
              // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: JSON.stringify({
                "make": this.state.make,
                "model": this.state.model,
                "year": this.state.year,
                "trim": this.state.trim

            }) // body data type must match "Content-Type" header
          });

        event.preventDefault();
    }

    render() {
        if (this.state.hasSubmitted) {
            return <p>Your Car is added successfully { this.state.model }</p>
        }

        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Make:
            <input
                        type="text"
                        name="make"
                        onChange={this.handleChange} />
                </label>
                <label>
                    Model:
            <input
                        type="text"
                        name="model"
                        onChange={this.handleChange} />
                </label>
                <label>
                    Year:
            <input
                        type="text"
                        name="year"
                        onChange={this.handleChange} />
                </label>
                <label>
                    Trim:
            <input
                        type="text"
                        name="trim"
                        onChange={this.handleChange} />
                </label>
                <input type="submit" value="Submit" />
            </form>
        );
    }
}

export default CarForm;