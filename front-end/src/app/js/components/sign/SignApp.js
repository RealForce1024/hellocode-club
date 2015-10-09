import React, {Component} from 'react';
import {RouteHandler} from 'react-router';
import {connect} from 'react-redux';

class SignApp extends Component {
    render() {
        return (
            <div className="container">
                <RouteHandler {...this.props}/>
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

import {signIn, signUp} from '../../actions/userActions';

function mapDispatchToProps(dispatch) {
    return {
        action: {
            signIn: payload => dispatch(signIn(payload)),
            signUp: payload => dispatch(signUp(payload))
        }
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(SignApp);
