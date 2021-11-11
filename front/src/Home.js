import React, {Component} from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import {Alert} from "./alerts/Alert";

class Home extends Component {
    render() {
        return (
            <div>
                <AppNavbar/>
                <Alert/>
            </div>
        );
    }
}
export default Home;
