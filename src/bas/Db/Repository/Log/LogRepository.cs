using bas.Db.Factory;
using System;
using System.Collections.Generic;
using System.Linq;

namespace bas.Db.Repository.Log
{
    public class LogRepository : Repository, ILogRepository
    {
        public LogRepository(IContextFactory contextFactory)
            : base(contextFactory)
        {
        }

        public Guid CreateLog(Models.Log log)
        {
            using (var context = _contextFactory.CreateDbContext())
            {
                context.Log.Add(log);
                context.SaveChanges();

                return log.Id;
            }
        }

        public IEnumerable<Models.Log> GetAllLogs()
        {
            using (var context = _contextFactory.CreateDbContext())
            {
                return context.Log
                    .OrderByDescending(x => x.Created)
                    .ThenBy(x => x.DeviceId)
                    .ThenBy(x => x.Type)
                    .ToList();
            }
        }
    }
}
