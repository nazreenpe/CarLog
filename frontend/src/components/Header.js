import React from 'react';
import { Button } from 'reactstrap';
import './style.css';

class Header extends React.Component {
    render() {
        return (
            <div className="header">
                <h1>CarLog</h1>
                <Button id="login-button" color="primary" size="lg">
                    Login
                </Button>
            </div>
        );
    }
}

export default Header;