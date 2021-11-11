import React, {Component} from 'react';
import {
    Collapse,
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    Nav,
    Navbar,
    NavbarBrand,
    NavbarToggler,
    UncontrolledDropdown
} from 'reactstrap';
import {Link} from 'react-router-dom';
import {NewTask} from "./NewTask";

export default class AppNavbar extends Component {
    constructor(props) {
        super(props);
        this.state = {isOpen: false};
        this.toggle = this.toggle.bind(this);
    }

    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }

    render() {
        return (
            <Navbar color="dark" dark expand="md">
                <NavbarBrand href="/">IQIVIA</NavbarBrand>
                <NavbarToggler onClick={this.toggle}/>
                <Collapse isOpen={this.state.isOpen} navbar>
                    <Nav className="ml-auto" navbar>
                        <UncontrolledDropdown nav inNavbar>
                            <DropdownToggle nav caret>
                                Algoritmhs
                            </DropdownToggle>
                            <DropdownMenu right>
                                <DropdownItem>
                                    <Link to="/tasksResults/workerA">Task Results Algorithm A</Link>
                                </DropdownItem>
                                <DropdownItem>
                                    <Link to="/tasksResults/workerB">Task Results Algorithm B</Link>
                                </DropdownItem>
                                <DropdownItem divider/>
                            </DropdownMenu>
                        </UncontrolledDropdown>
                        <UncontrolledDropdown nav inNavbar>
                            <DropdownToggle nav caret>
                                New Task
                            </DropdownToggle>
                            <DropdownMenu right>
                                <DropdownItem>
                                    <NewTask algoId="workerA" payload='{
                	"laboratory_name": "CUSTOMER_NAME",
                	"sfdc": "123456789",
                	"tranches" : [60,30,10]
                }'>Worker A</NewTask>
                                </DropdownItem>
                                <DropdownItem>
                                    <NewTask algoId="workerB" payload='{
                	"laboratory_name": "CUSTOMER_NAME",
                	"input_file": "input_questions.xlsx",
                	"identification_column" : "MY_ID"
                }'>Worker B</NewTask>
                                </DropdownItem>
                            </DropdownMenu>
                        </UncontrolledDropdown>
                    </Nav>
                </Collapse>
            </Navbar>
        )
    }
}
