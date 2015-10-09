import React, {Component} from 'react';

export default class Navbar extends Component {
    render() {
        const user = this.props.data.user.toJS();
        return (
            <nav className="navbar navbar-dark navbar-fixed-top bg-primary">
                <div className="container">
                    <a className="navbar-brand" href="/app">Hellocode.club</a>
                    <ul className="nav navbar-nav">
                        <li className="nav-item active">
                            <a className="nav-link" href="#">Home <span className="sr-only">(current)</span></a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="#">Features</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="#">Pricing</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="#">About</a>
                        </li>
                    </ul>
                    <ul className="nav navbar-nav pull-right">
                        <li className="nav-item">
                            <a className="nav-link" href="#">{user.nick || user.id}</a>
                        </li>
                    </ul>
                </div>
            </nav>
        );
    }
}