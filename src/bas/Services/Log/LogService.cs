using bas.Db.Repository.Log;
using bas.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace bas.Services.Log
{
    public class LogService : ILogService
    {
        public LogService(ILogRepository logRepository)
        {
            _logRepository = logRepository;
        }

        private ILogRepository _logRepository;

        public Guid CreateLog(LogRequest logRequest)
        {
            return _logRepository.CreateLog(new Models.Log
            {
                Created = DateTime.TryParse(logRequest.Date, out var date) ? date : DateTime.Now,
                DeviceId = logRequest.DeviceId,
                Type = logRequest.Type,
                Value = logRequest.Value.ToString()
            });
        }

        public IEnumerable<LogResult> GetAllLogs()
        {
            return _logRepository.GetAllLogs()
                .Select(x => new LogResult
                {
                    Id = x.Id.ToString(),
                    DeviceId = x.DeviceId,
                    Created = x.Created,
                    Type = x.Type.ToString(),
                    Value = x.Value
                })
                .ToList();
        }
    }
}
