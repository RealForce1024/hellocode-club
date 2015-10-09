import React, { Component } from 'react';
import {RouteHandler} from 'react-router';
import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';

import Navbar from './Navbar';

class App extends Component {
    componentWillMount() {
        this.props.action.user.getUser();
    }

    render() {
        return (
            <div>
                <Navbar {...this.props}/>
                <RouteHandler className="container" {...this.props}/>
            </div>
        );
    }
}


function mapStateToProps(state) {
    return {
        data: {
            user: state.user
        }
    };
}

import * as userActions from '../../actions/userActions';

function mapDispatchToProps(dispatch) {
    return {
        action: {
            user: bindActionCreators(userActions, dispatch)
        }
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(App);
