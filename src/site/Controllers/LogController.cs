using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using site.Db;
using site.Db.Models;
using site.Models;
using System;
using System.Collections.Generic;
using System.Linq;

namespace site.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class LogController : ControllerBase
    {
        private readonly ILogger<LogController> _logger;

        private readonly Context _context;

        public LogController(ILogger<LogController> logger, Context context)
        {
            _logger = logger;
            _context = context;
        }

        [HttpGet]
        public IEnumerable<LogResult> Get()
        {
            var result = new LogResult[] { };

            _logger.LogInformation("LogController.Get()");

            try
            {
                result = _context.Log
                    .OrderByDescending(x => x.Created)
                    .ThenBy(x => x.DeviceId)
                    .ThenBy(x => x.Type)
                    .Select(x => new LogResult
                    {
                        Id = x.Id.ToString(),
                        Date = x.Created.ToString("dd-MM-yyyy HH:mm:ss"),
                        Device = x.DeviceId,
                        Type = x.Type.ToString(),
                        Value = x.Value,
                    })
                    .ToArray();
            } 
            catch (Exception ex)
            {
                _logger.LogError(ex, ex.Message);
            }

            return result;
        }

        [HttpPost]
        public string Post([FromBody]LogRequest request)
        {
            var result = string.Empty;

            _logger.LogInformation($"LogController.Post({JsonConvert.SerializeObject(request)})");

            try
            {
                var log = new Log
                {
                    Created = DateTime.TryParse(request.Date, out var date) ? date : DateTime.Now,
                    DeviceId = request.DeviceId,
                    Type = LogType.app,
                    Value = request.Value.ToString()
                };

                _context.Log.Add(log);
                _context.SaveChanges();

                result = log.Id.ToString();
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, ex.Message);
            }

            return result;
        }
    }
}
