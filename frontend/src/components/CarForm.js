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

    handleSubmit(event) {
        // alert('A name was submitted: ' + this.state.value);
        //   ajax call to backEnd
        this.setState({ hasSubmitted: true });
        event.preventDefault();
    }

    render() {

        if (this.state.hasSubmitted) {
            return <p>Your Car is added successfully</p>
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