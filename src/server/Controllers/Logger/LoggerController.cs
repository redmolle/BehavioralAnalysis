using server.Db;
using server.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using server.Services.User;

namespace server.Controllers.Logger
{
    [Route("api/[controller]")]
    [ApiController]
    public class LoggerController : ControllerBase
    {
        public LoggerController(Context logContext)
        {
            this.logContext = logContext;
        }

        Context logContext = null;

        [HttpPost]
        public IActionResult Log([FromBody]LogRequest request)
        {
            var log = new Log
            {
                Created = DateTime.TryParse(request.Date, out var date) ? date : DateTime.Now,
                DeviceId = request.DeviceId,
                Type = LogType.contact,
                Value = JsonConvert.SerializeObject(request.Value)
            };

            logContext.Log.Add(log);

            logContext.SaveChanges();

            return CreatedAtAction("Get", log.Id);
        }

        [HttpGet]
        [Route("all")]
        [Authorize(Roles = UserRole.Admin)]
        public IActionResult Get()
        {
            var logs = logContext.Log.ToList();
            return Ok(logs.Select(x => new LogResult
            {
                Id = x.Id.ToString(),
                Created = x.Created,
                Type = x.Type.ToString(),
                Value = x.Value
            }).ToList());
        }
    }
}
