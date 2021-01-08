using bas.Db;
using bas.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace bas.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class LogController : ControllerBase
    {
        public LogController(Context context)
        {
            _context = context;
        }

        private readonly Context _context = null;

        [HttpPost]
        public IActionResult Log([FromBody] LogRequest request)
        {
            var log = new Log
            {
                Created = DateTime.TryParse(request.Date, out var date) ? date : DateTime.Now,
                DeviceId = request.DeviceId,
                Type = LogType.contact,
                Value = JsonConvert.SerializeObject(request.Value)
            };

            _context.Log.Add(log);

            _context.SaveChanges();

            return CreatedAtAction("Get", log.Id);
        }

        [HttpGet]
        [Route("all")]
        [Authorize(Roles = UserRole.Admin)]
        public IActionResult Get([FromBody] object request)
        {
            var logs = _context.Log.Select(x => new LogResult
            {
                Id = x.Id.ToString(),
                Created = x.Created,
                Type = x.Type.ToString(),
                Value = x.Value
            }).ToList();

            return Ok(logs);
        }
    }
}
