using bas.Models;
using System;
using System.Collections.Generic;

namespace bas.Services.Log
{
    public interface ILogService
    {
        Guid CreateLog(LogRequest logRequest);

        IEnumerable<LogResult> GetAllLogs();
    }
}
