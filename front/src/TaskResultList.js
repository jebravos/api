import React, {Component} from "react";
import {Container, Table} from 'reactstrap';
import AppNavbar from "./AppNavbar";
import {Alert} from "./alerts/Alert";


class TaskResultList extends Component{

    constructor(props) {
        super(props);
        console.log(props)
        this.state = {tasks: []}
        // this.algoId = props.algoId;
    }

    componentDidMount() {
        console.log(this.props.algoId)
        fetch(`/${this.props.algoId}/results/list`)
            .then(response => response.json())
            .then(data => this.setState({tasks: data}));
    }

    render() {
        const {tasks} = this.state;

        const taskList = tasks.map(task => {
            return <tr key={task.id} bgcolor={task.status === "ERROR" ? 'lightcoral' : task.status === "DONE" ? 'lightgreen' : 'lightgrey' }>
                <td style={{whiteSpace: 'nowrap'}}>{task.id}</td>
                <td style={{whiteSpace: 'nowrap'}}>{task.algoId}</td>
                <td>{task.timestamp}</td>
                <td>{task.status}</td>
            </tr>
        })


        return (
            <div>
                <AppNavbar/>
                <Alert/>
                <Container fluid>
                    <div className="float-right">
                    </div>
                    <h3>Task Results</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="30%">ID</th>
                            <th width="30%">TIMESTAMP</th>
                            <th width="40%">STATUS</th>
                        </tr>
                        </thead>
                        <tbody>
                        {taskList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default TaskResultList;
