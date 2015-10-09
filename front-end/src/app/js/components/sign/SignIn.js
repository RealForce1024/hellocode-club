import React, { Component } from 'react';
import {Link} from 'react-router';

export default class SignIn extends Component {
    constructor(props) {
        super(props);
        this.state = {
            formValue: {
                account: '',
                password: ''
            },
            submitError: ''
        };
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.data.user.has('id')) {
            // 登录成功
            window.location.href = '/app';
        }
        if (nextProps.data.user.has('code')) {
            this.setState({submitError: nextProps.data.user.get('message')});
        }
    }

    onSignIn(e) {
        e.preventDefault();
        let params = this.state.formValue;
        this.props.action.signIn(params);
    }

    handleChange(e) {
        let value = this.state.formValue;
        value[e.target.name] = e.target.value;
        this.setState({formValue: value});
    }

    handleEnterToSignIn(e) {
        if (e.keyCode === 13) {
            this.onSignIn(e);
        }
    }

    handleFocus(e) {
        e.preventDefault();
        let value = this.state.formValue;
        value[e.target.name] = '';
        this.setState({formValue: value});
    }

    render() {
        return (
            <form role="form" style={this.props.style}>
                <div className="form-group">
                    <label className="sr-only">账号(邮箱)</label>
                    <input tabIndex="1" type="text" className="form-control" name="account" placeholder="邮箱"
                           onFocus={this.handleFocus.bind(this)} onChange={this.handleChange.bind(this)}/>
                </div>
                <div className="form-group">
                    <label className="sr-only">密码</label>
                    <input tabIndex="2" type="password" className="form-control" name="password" placeholder="密码"
                           onKey={this.handleEnterToSignIn.bind(this)} onFocus={this.handleFocus.bind(this)}
                           onChange={this.handleChange.bind(this)}/>
                </div>
                <div className="form-group">
                    <button tabIndex="3" type="button" className="btn btn-primary btn-block"
                            onClick={this.onSignIn.bind(this)}>
                        登录
                    </button>
                    <span className="pull-right text-danger">{this.state.submitError}</span>
                </div>
                <div className="form-group">
                    <Link to="authSignUp">没有账号？注册</Link>
                </div>
            </form>
        );
    }
}

SignIn.defaultProps = {
    style: {
        marginTop: '100px',
        maxWidth: '500px',
        minWidth: '300px',
        marginLeft: 'auto',
        marginRight: 'auto'
    }
};
