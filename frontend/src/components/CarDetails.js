import React from 'react';
import axios from 'axios';

class CarDetails extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            // props.id came from the value we gave in App.js
            id: props.id,
            make:'',
            model:'',
            year:null,
            trim:'',
            hasLoaded: false
        };

        axios.get(`/cars/${props.id}`, null, {
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(res => {
                const carDetails = res.data;
                this.setState({ ...carDetails });
            })

    }

    render() {
        return <dl>
            <dt>make</dt>
            <dd>{this.state.make}</dd>
            <dt>model</dt>
            <dd>{this.state.model}</dd>
            <dt>year</dt>
            <dd>{this.state.year}</dd>
            <dt>trim</dt>
            <dd>{this.state.trim}</dd>
        </dl>
    }
}

export default CarDetails;