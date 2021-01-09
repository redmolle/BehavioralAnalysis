import * as React from 'react';
import { connect } from 'react-redux';

const Home = () => (
  <div>
        <p><strong>B</strong>ehavioral <strong>A</strong>nalysis <strong>S</strong>ervice of mobile devices</p>
        <p><a href='https://github.com/redmolle/Diploma'>It's a diploma project</a></p>
  </div>
);

export default connect()(Home);