import React, { Component } from 'react';

const CAPTCHA_URI = '/api/auth/captcha';

class SingIn extends Component {
    constructor(props) {
        super(props);
        this.state = {
            formValue: {
                account: '',
                password: '',
                captcha: ''
            }
        };
    }

    onSignUp(e) {
        e.preventDefault();
        let params = this.state.formValue;
        this.props.action.signIn(params);
    }

    onChange(e) {
        let value = this.state.formValue;
        value[e.target.name] = e.target.value;
        this.setState({formValue: value});
    }

    onEnterToSignUp(e) {
        if (e.keyCode === 13) {
            this.onSignUp(e);
        }
    }

    onRefreshCaptcha(e) {
        e.preventDefault();
        e.target.src = CAPTCHA_URI + '?' + (new Date().getTime());
    }

    render() {
        return (
            <div>
                <form role="form">
                    <div className="form-group">
                        <input type="text" className="form-control" name="account" placeholder="邮箱"/>
                    </div>
                    <div className="form-group">
                        <input type="password" className="form-control" name="password" placeholder="密码"/>
                    </div>
                    <div className="form-group">
                        <input type="password" className="form-control" name="password2" placeholder="确认密码"/>
                    </div>
                    <div className="form-group">
                        <div className="row">
                            <a href="javascript:;" onClick={this.onRefreshCaptcha.bind(this)}>
                                <img src={CAPTCHA_URI} className="col-xs-6"/>
                            </a>

                            <div className="col-xs-6">
                                <label className="sr-only">验证码</label>
                                <input tag="3" className="form-control" name="captcha" placeholder="点击图片刷新验证码"
                                       onKeyUp={this.onEnterToSignUp.bind(this)} onChange={this.onChange.bind(this)}/>
                            </div>
                        </div>
                    </div>
                    <div className="form-group">
                        <button type="button" className="btn btn-primary btn-block" onClick={this.onSignUp.bind(this)}>
                            注册
                        </button>
                    </div>
                </form>
            </div>
        );
    }
}

export default SingIn;
