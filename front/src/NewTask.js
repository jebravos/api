import {Component} from "react";
import {Label} from "reactstrap";
import {alertService} from "./alerts/AlertService";


export class NewTask extends Component {
    constructor(props) {
        super(props);
        this.state = {
            algoId: this.props.algoId,
            payload: this.props.payload
        };

    }

    render() {

        return (
            <div>
                <Label onClick={this.handleNewTask.bind(this)}>{this.props.algoId}</Label>
            </div>
        );
    }

    async handleNewTask() {

        const {algoId, payload} = this.state;

        const body = JSON.stringify(payload);
        console.log(body)

        const response = await fetch(`/${algoId}/new_task`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: payload
        })

        if (!response.ok) {
            const error = await response.text();
            alertService.error(error, {autoClose: true})
        } else {
            const id = await response.json();
            alertService.info(`Task identified by ${id} was enqueued`, {autoClose: true})
        }

    }
}
