import * as React from 'react';
import { Redirect, Route, Switch } from 'react-router';
import Layout from './components/Layout';
import Home from './components/Home';
import Dash from './components/Dash';

import './custom.css'

export default () => (
    <Layout>
        <Switch>
            <Route exact path='/' component={Home} />
            <Route path='/dash/:page?' component={Dash} />
        </Switch>
    </Layout>
);
