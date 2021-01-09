import * as React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router';
import { Link } from 'react-router-dom';
import { ApplicationState } from '../store';
import * as LogsStore from '../store/Logs';

type DashProps =
    LogsStore.LogState
    & typeof LogsStore.actionCreators
    & RouteComponentProps<{ page: string }>;

class Dash extends React.PureComponent<DashProps> {
    public componentDidMount() {
        this.ensureDataFetched();
    }

    public componentDidUpdate() {
        this.ensureDataFetched();
    }

    public render() {
        return (
            <React.Fragment>
                <h1 id="tabelLabel">Dash</h1>
                {this.renderLogsTable()}
                {this.renderPagination()}
            </React.Fragment>
        );
    }

    private ensureDataFetched() {
        const page = parseInt(this.props.match.params.page, 10) || 1;
        this.props.requestLogs(page);
    }

    private renderLogsTable() {
        return (
            <table className='table table-striped' aria-labelledby="tabelLabel">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Device</th>
                        <th>Type</th>
                        <th>Log</th>
                    </tr>
                </thead>

                <tbody>
                    {this.props.logs.map((log: LogsStore.Log) =>
                        <tr key={log.id}>
                            <td>{log.date}</td>
                            <td>{log.device}</td>
                            <td>{log.type}</td>
                            <td>{log.value}</td>
                        </tr>
                    )}
                </tbody>
            </table>
        );
    }

    private renderPagination() {
        const prevPage = (this.props.page || 1) - 1;
        const nextPage = (this.props.page || 1) + 1;

        return (
            <div className="d-flex justify-content-between">
                {prevPage > 0 && <Link className='btn btn-outline-secondary btn-sm' to={`/dash/${prevPage}`}>Previous</Link>}
                {this.props.isLoading ? <span>Loading...</span> : <span>Page {this.props.page} of {this.props.maxPage}</span>}
                {nextPage <= this.props.maxPage && <Link className='btn btn-outline-secondary btn-sm' to={`/dash/${nextPage}`}>Next</Link>}
            </div>
        );
    }
}

export default connect(
    (state: ApplicationState) => state.logs,
    LogsStore.actionCreators
)(Dash as any);
