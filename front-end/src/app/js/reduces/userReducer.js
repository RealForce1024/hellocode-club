import Immutable from 'immutable';

import {SIGN_IN, SIGN_UP, GET_USER} from '../actions/userActions';

export default function (state = Immutable.fromJS({}), action = {}) {
    switch (action.type) {
        case SIGN_IN:
            return Immutable.fromJS(action.data);
        case SIGN_UP:
            return Immutable.fromJS(action.data);
        case GET_USER :
            return Immutable.fromJS(action.data);
        default:
            return state;
    }
}
