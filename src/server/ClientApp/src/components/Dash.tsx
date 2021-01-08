import * as React from 'react';
import * as DashStore from '../store/Dash';

type DashProps =
    DashStore.DashState
    & typeof DashStore.actionCreators;

class LogDash extends React.PureComponent<DashProps> {
    public componentDidMount() {
        this.ensureDataFetched();
    }

    public componentDidUpdate() {
        this.ensureDataFetched();
    }

    public render() {
        return (
            <React.Fragment>
            <h1 id="tabelLabel">Логи</h1>

            </React.Fragment>
        )
    }

    private ensureDataFetched() {
        const startDateIndex
    }
}
