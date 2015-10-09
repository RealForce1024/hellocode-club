import React from 'react';
import Router, {Route, RouteHandler, HistoryLocation} from 'react-router';
import {Provider} from 'react-redux';

import configureStore from './store/configureStore';
import App from './components/app/App';

const store = configureStore();

let routes = (
    <Route name="app" path="/app" handler={App}>
    </Route>
);

Router.run(routes, HistoryLocation, (Handler, routerState) => {
    React.render(
        <Provider store={store}>
            {() => <Handler routerState={routerState}/>}
        </Provider>,
        document.body);
});
