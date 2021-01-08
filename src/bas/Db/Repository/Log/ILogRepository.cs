using System;
using System.Collections.Generic;

namespace bas.Db.Repository.Log
{
    public interface ILogRepository
    {
        Guid CreateLog(Models.Log log);

        IEnumerable<Models.Log> GetAllLogs();
    }
}
