import './App.css';
import {Component} from "react";
import TaskResultList from "./TaskResultList";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import Home from "./Home";


class App extends Component {

    state = {
        results: []
    };

    async componentDidMount() {
        const response = await fetch('/workerA/results/list');
        const body = await response.json();
        this.setState({results: body});
    }

    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>
                    <Route path='/tasksResults/workerA' exact={true} component={() => <TaskResultList algoId={'workerA'}/>}/>
                    <Route path='/tasksResults/workerB' exact={true} component={() => <TaskResultList algoId={'workerB'}/>}/>
                </Switch>
            </Router>
        )
    }

}

export default App;
