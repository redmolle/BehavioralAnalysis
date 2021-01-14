using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
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

        [HttpGet("{page?}")]
        public LogPageResult Get(int? page, [FromQuery]string filter)
        {
            int perPage = 50;
            var result = new LogPageResult();

            _logger.LogInformation("LogController.Get()");

            int p = page ?? 0;
            p = p <= 0 ? 0 : p - 1;

            try
            {
                var logs = _context.Log.AsQueryable();

                if (filter != LogType.def)
                {
                    logs = logs.Where(x => x.Type == filter);
                }

                result.MaxPage = (int)Math.Ceiling((double)logs.Count() / (double)(perPage == 0 ? 1 : perPage));
                result.Logs = logs
                    .OrderByDescending(x => x.Created)
                    .ThenBy(x => x.DeviceId)
                    .ThenBy(x => x.Type)
                    .Skip(perPage * p)
                    .Take(perPage)
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
        public IEnumerable<string> Post([FromBody]LogRequest request)
        {
            var result = new List<string>();

            _logger.LogInformation($"LogController.Post({JsonConvert.SerializeObject(request)})");

            try
            {
                var list = new List<string>();

                var value = request.Value.ToString();

                var token = JToken.Parse(value);

                if (token is JArray)
                {
                    var valueList = JsonConvert.DeserializeObject<List<object>>(value);
                    foreach (var itemValue in valueList)
                    {
                        list.Add(itemValue.ToString());
                    }
                }
                else if (token is JObject)
                {
                    list.Add(value);
                }

                foreach (var itemValue in list.Where(x => new[] { "{}", "[]" }.Contains(x)))
                {
                    var log = new Log
                    {
                        Created = DateTime.TryParse(request.Date, out var date) ? date : DateTime.Now,
                        DeviceId = request.DeviceId,
                        Type = request.Type,
                        Value = itemValue
                    };

                    _context.Log.Add(log);
                    _context.SaveChanges();

                    result.Add(log.Id.ToString());
                }
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, ex.Message);
            }

            return result;
        }

        [HttpGet("type")]
        public IEnumerable<string> Type()
        {
            return _context.Log.Select(x => x.Type).Distinct();
        }
    }
}
