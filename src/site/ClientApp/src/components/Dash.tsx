import { push } from 'connected-react-router';
import { ENOPROTOOPT } from 'constants';
import * as React from 'react';
import { connect } from 'react-redux';
import { Redirect, RouteComponentProps, useLocation } from 'react-router';
import { Link, useHistory } from 'react-router-dom';
import { ApplicationState } from '../store';
import * as LogsStore from '../store/Logs';

type DashProps =
    LogsStore.LogState
    & typeof LogsStore.actionCreators
    & RouteComponentProps<{ filter: string, page: string }>;

const filterOptions = [
    { value: "def" },
    { value: "app" },
    { value: "call" },
    { value: "contact" },
    { value: "file" },
    { value: "location" },
    { value: "notification" },
    { value: "granted_permission" },
    { value: "sms" },
    { value: "wifi" }
];

const Dash = (props: DashProps) => {
    const history = useHistory();
    const [page, setPage] = React.useState(parseInt(props.match.params.page, 10) || 1);
    const [filter, setFilter] = React.useState(props.match.params.filter && props.match.params.filter !== "" ? props.match.params.filter : "none");

    const move = (newPage: number, newFilter: string) => {
        history.push(`/dash/${newPage}/${newFilter}`);
        setPage(newPage);
        setFilter(newFilter);
    }

    const renderFilterList = () => {

        const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
            move(page, e.target.value);
        }

        return (
            <div>
                Filter: <select value={filter} onChange={handleChange}>
                    {filterOptions.map((option) => (
                        <option value={option.value}>{option.value === "def" ? "default" : option.value}</option>
                    ))}
                </select>
            </div>
        );
    }

    const renderLogsTable = () => {
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
                    {props.logs.map((log: LogsStore.Log) =>
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

    const renderPagination = () => {
        const prevPage = (page || 1) - 1;
        const nextPage = (page || 1) + 1;

        return (
            <div className="d-flex justify-content-between">
                {prevPage > 0 && <button className='btn btn-outline-secondary btn-sm' onClick={() => move(prevPage, filter)}>Previous</button>}
                {<span>Page {page} of {props.maxPage}</span>}
                {nextPage <= props.maxPage && <button className='btn btn-outline-secondary btn-sm' onClick={() => move(nextPage, filter)}>Next</button>}
            </div>
        );
    }

    React.useEffect(() => {
        props.requestLogs(page, filter);
    }, [page, filter]);

    return (
        <React.Fragment>
            <h1 id="tabelLabel">Dash</h1> {renderFilterList()}
            {props.isLoading ? <span>Loading...</span> : renderLogsTable()}
            {renderPagination()}
        </React.Fragment>
    );
}


export default connect(
    (state: ApplicationState) => state.logs,
    LogsStore.actionCreators
)(Dash as any);
