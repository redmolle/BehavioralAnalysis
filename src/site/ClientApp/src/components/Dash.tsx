import * as React from 'react';
import { connect } from 'react-redux';
import { ApplicationState } from '../store';
import * as LogsStore from '../store/Logs';

type DashProps =
    LogsStore.LogState
    & typeof LogsStore.actionCreators;


class Dash extends React.PureComponent<DashProps> {
  // This method is called when the component is first added to the document
  public componentDidMount() {
    this.ensureDataFetched();
  }

  // This method is called when the route parameters change
  public componentDidUpdate() {
    this.ensureDataFetched();
  }

  public render() {
    return (
      <React.Fragment>
        <h1 id="tabelLabel">Логи</h1>
        {this.renderLogsTable()}
        {this.renderPagination()}
      </React.Fragment>
    );
  }

  private ensureDataFetched() {
      this.props.requestLogs();
  }

    private renderLogsTable() {
    return (
      <table className='table table-striped' aria-labelledby="tabelLabel">
        <thead>
          <tr>
            <th>Дата</th>
            <th>Устройство</th>
            <th>Тип</th>
            <th>Лог</th>
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

    return (
      <div className="d-flex justify-content-between">
        {this.props.isLoading && <span>Loading...</span>}
      </div>
    );
  }
}

export default connect(
    (state: ApplicationState) => state.logs, // Selects which state properties are merged into the component's props
    LogsStore.actionCreators // Selects which action creators are merged into the component's props
)(Dash as any);
