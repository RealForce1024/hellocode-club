import Http from 'superagent';
import {interceptor} from './interceptor';

export const SIGN_IN = 'SIGN_IN';
export const SIGN_UP = 'SIGN_UP';
export const GET_USER = 'GET_USER';

function loadSignIn(data) {
    console.log(data);
    return {
        type: SIGN_IN,
        data: data
    };
}

function loadSignUp(data) {
    return {
        type: SIGN_UP,
        data: data
    };
}

function loadUser(data) {
    return {
        type: GET_USER,
        data: data
    };
}

export function signIn(payload) {
    return dispatch => {
        return Http.post('/api/auth/signin')
            .send(payload)
            .end(function (err, resp) {
                dispatch(loadSignIn(resp.body));
            });
    };
}

export function signUp(payload) {
    return dispatch => {
        return Http.post('/api/auth/signup')
            .send(payload)
            .end((err, resp) => {
                dispatch(loadSignUp(resp.body))
            });
    }
}

export function signOut() {
    return dispatch => {
        return Http.del('/api/auth/signout')
            .end((err, resp) => {
                window.location.href = '/app';
            });
    };
}

export function getUser() {
    return dispatch => {
        return Http.get('/api/user')
            .end((err, resp) => {
                interceptor(resp, () => dispatch(loadUser(resp.body)));
            });
    };
}
